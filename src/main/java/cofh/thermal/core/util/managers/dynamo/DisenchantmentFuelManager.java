package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.dynamo.DisenchantmentFuel;
import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import cofh.thermal.lib.util.recipes.internal.IDynamoFuel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cofh.lib.util.Utils.getName;
import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.DISENCHANTMENT_FUEL;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class DisenchantmentFuelManager extends SingleItemFuelManager {

    private static final DisenchantmentFuelManager INSTANCE = new DisenchantmentFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    private RegistryAccess registryAccess;

    public static DisenchantmentFuelManager instance() {

        return INSTANCE;
    }

    public void setRegistryAccess(RegistryAccess registryAccess) {

        this.registryAccess = registryAccess;
    }

    private DisenchantmentFuelManager() {

        super(DEFAULT_ENERGY);
    }

    @Override
    public boolean validFuel(ItemStack input) {

        if (input.getCapability(Capabilities.FluidHandler.ITEM) != null) {
            return false;
        }
        return getEnergy(input) > 0;
    }

    @Override
    protected void clear() {

        fuelMap.clear();
    }

    public int getEnergy(ItemStack stack) {

        IDynamoFuel fuel = getFuel(stack);
        return fuel != null ? fuel.getEnergy() : getEnergyFromEnchantments(stack);
    }

    public int getEnergyFromEnchantments(ItemStack stack) {

        if (stack.isEmpty()) {
            return 0;
        }
        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        int energy = 0;

        for (Holder<Enchantment> enchant : enchants.keySet()) {
            energy += enchant.value().getMinCost(enchants.getLevel(enchant));
        }
        energy += (enchants.size() * (enchants.size() + 1)) / 2;
        energy *= (DEFAULT_ENERGY / 2);

        return energy;
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(DISENCHANTMENT_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
        createConvertedRecipes(recipeManager);
    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<DisenchantmentFuel>> convertedFuels = new ArrayList<>();

    public List<RecipeHolder<DisenchantmentFuel>> getConvertedFuels() {

        return convertedFuels;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        if (registryAccess == null) {
            return;
        }
        var enchantLookup = registryAccess.lookup(Registries.ENCHANTMENT);
        if (enchantLookup.isEmpty()) {
            return;
        }
        List<ItemStack> books = new ArrayList<>();
        for (Holder.Reference<Enchantment> enchant : enchantLookup.get().listElements().toList()) {
            books.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchant, enchant.value().getMaxLevel())));
        }
        for (ItemStack book : books) {
            try {
                if (getFuel(book) == null && validFuel(book)) {
                    convertedFuels.add(convert(book, getEnergy(book)));
                }
            } catch (Exception e) { // pokemon!
                ThermalCore.LOG.error(getRegistryName(book.getItem()) + " threw an exception when querying the fuel value as the mod author is doing non-standard things in their item code (possibly tag related). It may not display in JEI but should function as fuel.");
            }
        }
    }

    protected RecipeHolder<DisenchantmentFuel> convert(ItemStack item, int energy) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "disenchantment_" + getName(item)), new DisenchantmentFuel(energy, singletonList(Ingredient.of(item)), emptyList()));
    }
    // endregion
}
