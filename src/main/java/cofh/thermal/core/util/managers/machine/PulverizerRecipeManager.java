package cofh.thermal.core.util.managers.machine;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.ThermalCore;
import cofh.thermal.core.util.recipes.machine.PulverizerRecipe;
import cofh.thermal.lib.util.managers.SingleItemRecipeManager;
import cofh.thermal.lib.util.recipes.internal.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.*;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.*;

public class PulverizerRecipeManager extends SingleItemRecipeManager.Catalyzed {

    private static final PulverizerRecipeManager INSTANCE = new PulverizerRecipeManager();
    protected static final int DEFAULT_ENERGY = 4000;

    protected boolean defaultFurnaceRecipes = true;

    public static PulverizerRecipeManager instance() {

        return INSTANCE;
    }

    private PulverizerRecipeManager() {

        super(DEFAULT_ENERGY, 4, 0);
    }

    public void setDefaultFurnaceRecipes(boolean defaultFurnaceRecipes) {

        this.defaultFurnaceRecipes = defaultFurnaceRecipes;
    }

    @Override
    protected void clear() {

        recipeMap.clear();
        convertedRecipes.clear();
    }

    // region RECIPES
    @Override
    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids, BaseMachineRecipe.RecipeType type) {

        if (inputItems.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        ItemStack input = inputItems.get(0);
        if (input.isEmpty()) {
            return null;
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        for (FluidStack stack : outputFluids) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        IMachineRecipe recipe;
        if (type == BaseMachineRecipe.RecipeType.DISENCHANT) {
            recipe = new DisenchantMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
            recipeMap.put(makeComparable(input), recipe);
        } else {
            recipe = new InternalPulverizerRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
            recipeMap.put(makeNBTComparable(input), recipe);
        }
        return recipe;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(PULVERIZER_RECIPE.get());
        for (var holder : recipes) {
            addRecipe(holder.value());
        }
        var recycle = recipeManager.getAllRecipesFor(PULVERIZER_RECYCLE_RECIPE.get());
        for (var holder : recycle) {
            addRecipe(holder.value(), BaseMachineRecipe.RecipeType.DISENCHANT);
        }
        var catalysts = recipeManager.getAllRecipesFor(PULVERIZER_CATALYST.get());
        for (var holder : catalysts) {
            addCatalyst(holder.value());
        }

        if (defaultFurnaceRecipes) {
            ThermalCore.LOG.debug("Adding default Furnace-Based processing recipes to the Pulverizer...");
            createConvertedRecipes(recipeManager);
            for (var recipe : getConvertedRecipes()) {
                addRecipe(recipe.value(), BaseMachineRecipe.RecipeType.CATALYZED);
            }
        }
    }
    // endregion

    // region CATALYZED RECIPE
    protected static class InternalPulverizerRecipe extends CatalyzedMachineRecipe {

        public InternalPulverizerRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

            super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        }

        @Override
        public IRecipeCatalyst getCatalyst(ItemStack input) {

            return instance().getCatalyst(input);
        }

    }
    // endregion

    // region CONVERSION
    protected List<RecipeHolder<PulverizerRecipe>> convertedRecipes = new ArrayList<>();
    protected Map<ComparableItemStack, MutableTriple<Ingredient, Ingredient, Ingredient>> conversionIngredients = new HashMap<>(); // stored as Dust, Ore, Raw

    public List<RecipeHolder<PulverizerRecipe>> getConvertedRecipes() {

        return convertedRecipes;
    }

    protected void createConvertedRecipes(RecipeManager recipeManager) {

        for (var recipe : recipeManager.getAllRecipesFor(RecipeType.BLASTING)) {
            getConversionIngredients(recipe.value());
        }
        for (var ingredientSet : conversionIngredients.entrySet()) {
            convertRecipes(ingredientSet.getKey().toItemStack(), ingredientSet.getValue());
        }
        conversionIngredients = new HashMap<>();
    }

    protected void getConversionIngredients(AbstractCookingRecipe recipe) {

        if (recipe.isSpecial() || recipe.result.isEmpty()) {
            return;
        }
        ItemStack ingot = recipe.result;

        Ingredient input = recipe.getIngredients().get(0);
        if (!ingot.is(Tags.Items.INGOTS)) {
            return;
        }
        var ingredients = conversionIngredients.getOrDefault(makeComparable(ingot), new MutableTriple<>());

        for (ItemStack inputStack : input.getItems()) {
            if (validRecipe(inputStack)) {
                return;
            }
            if (inputStack.is(Tags.Items.DUSTS)) {
                ingredients.setLeft(input);
            }
            if (inputStack.is(Tags.Items.ORES)) {
                ingredients.setMiddle(input);
            }
            if (inputStack.is(Tags.Items.RAW_MATERIALS)) {
                ingredients.setRight(input);
            }
        }
        conversionIngredients.put(makeComparable(ingot), ingredients);
    }

    protected boolean convertRecipes(ItemStack ingot, Triple<Ingredient, Ingredient, Ingredient> ingredientSet) {

        Ingredient dust = ingredientSet.getLeft();
        Ingredient ore = ingredientSet.getMiddle();
        Ingredient raw = ingredientSet.getRight();

        if (dust == null) {
            return false;
        }

        if (!ingot.isEmpty() && !validRecipe(ingot)) {
            convertedRecipes.add(convertIngot(Ingredient.of(ingot), dust));
        }
        if (ore != null) {
            convertedRecipes.add(convertOre(ore, dust));
        }
        if (raw != null) {
            convertedRecipes.add(convertRaw(raw, dust));
        }
        return true;
    }

    protected RecipeHolder<PulverizerRecipe> convertIngot(Ingredient input, Ingredient dust) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "pulverizer_ingot_" + input.hashCode()),
                new PulverizerRecipe(getDefaultEnergy() / 2, 0.0F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Collections.singletonList(cloneStack(dust.getItems()[0], 1)),
                        List.of(-1.0F), // output chances
                        Collections.emptyList())); // no fluid output
    }

    protected RecipeHolder<PulverizerRecipe> convertOre(Ingredient input, Ingredient dust) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "pulverizer_ore_" + input.hashCode()),
                new PulverizerRecipe(getDefaultEnergy(), 0.2F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Arrays.asList(cloneStack(dust.getItems()[0], 1), new ItemStack(Blocks.GRAVEL)),
                        Arrays.asList(2.0F, 0.2F), // output chances
                        Collections.emptyList())); // no fluid output
    }

    protected RecipeHolder<PulverizerRecipe> convertRaw(Ingredient input, Ingredient dust) {

        return new RecipeHolder<>(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "pulverizer_raw_" + input.hashCode()),
                new PulverizerRecipe(getDefaultEnergy(), 0.1F,
                        Collections.singletonList(input),
                        Collections.emptyList(), // no fluid input
                        Collections.singletonList(cloneStack(dust.getItems()[0], 1)),
                        List.of(1.25F), // output chances
                        Collections.emptyList())); // no fluid output
    }
    // endregion
}
