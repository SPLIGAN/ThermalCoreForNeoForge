package cofh.thermal.core.util.managers.dynamo;

import cofh.core.util.helpers.FluidHelper;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.StirlingFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

import static cofh.lib.util.Constants.RF_PER_FURNACE_UNIT;
import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.STIRLING_FUEL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class StirlingFuelManager extends SingleItemFuelManager {

    private static final StirlingFuelManager INSTANCE = new StirlingFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static StirlingFuelManager instance() {

        return INSTANCE;
    }

    private StirlingFuelManager() {

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
        return fuel != null ? fuel.getEnergy() : getEnergyFurnaceFuel(stack);
    }

    public int getEnergyFurnaceFuel(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem().hasCraftingRemainingItem(stack)) {
            return 0;
        }
        int energy = stack.getBurnTime(null) * RF_PER_FURNACE_UNIT;
        return energy >= MIN_ENERGY ? energy : 0;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(STIRLING_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<StirlingFuel>> convertedFuels = new ArrayList<>();

    public List<RecipeHolder<StirlingFuel>> getConvertedFuels() {

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

    protected RecipeHolder<StirlingFuel> convert(ItemStack item, int energy) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "stirling_" + getName(item)), new StirlingFuel(energy, singletonList(Ingredient.of(item)), emptyList()));
    }
    // endregion
}
