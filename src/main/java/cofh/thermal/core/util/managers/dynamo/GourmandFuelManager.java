package cofh.thermal.core.util.managers.dynamo;

import cofh.core.util.helpers.FluidHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.GourmandFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.GOURMAND_FUEL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class GourmandFuelManager extends SingleItemFuelManager {

    private static final GourmandFuelManager INSTANCE = new GourmandFuelManager();
    protected static final int DEFAULT_ENERGY = 1600;

    public static GourmandFuelManager instance() {

        return INSTANCE;
    }

    private GourmandFuelManager() {

        super(DEFAULT_ENERGY);
    }

    @Override
    public boolean validFuel(ItemStack input) {

        if (FluidHelper.hasFluidHandlerCap(input)) {
            return false;
        }
        return getEnergy(input) > 0;
    }

    @Override
    protected void clear() {

        fuelMap.clear();
        convertedFuels.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFromFood(stack);
    }

    public int getEnergyFromFood(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem().hasCraftingRemainingItem(stack)) {
            return 0;
        }
        FoodProperties food = stack.getItem().getFoodProperties(stack, null);
        if (food == null) {
            return 0;
        }
        int energy = food.nutrition() * DEFAULT_ENERGY;

        if (!food.effects().isEmpty()) {
            for (FoodProperties.PossibleEffect effect : food.effects()) {
                if (effect.effect().getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    return 0;
                }
            }
            energy *= 2;
        }
        if (food.nutrition() > 0 && food.saturation() > food.nutrition()) {
            energy *= 4;
        }
        if (food.eatSeconds() < 1.6F) {
            energy *= 2;
        }
        return energy >= MIN_ENERGY ? energy : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(GOURMAND_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<GourmandFuel>> convertedFuels = new ArrayList<>();

    public List<RecipeHolder<GourmandFuel>> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        ItemStack query;
        for (Item item : BuiltInRegistries.ITEM) {
            query = new ItemStack(item);
            try {
                if (getFuel(query) == null && validFuel(query)) {
                    convertedFuels.add(convert(query, getEnergy(query)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(getRegistryName(query.getItem()) + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected RecipeHolder<GourmandFuel> convert(ItemStack item, int energy) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "gourmand_" + getName(item)), new GourmandFuel(energy, singletonList(Ingredient.of(item)), emptyList()));
    }
    // endregion
}
