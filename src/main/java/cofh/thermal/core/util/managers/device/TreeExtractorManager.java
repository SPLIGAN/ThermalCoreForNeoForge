package cofh.thermal.core.util.managers.device;

import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.lib.util.managers.AbstractManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.TREE_EXTRACTOR_BOOST;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.TREE_EXTRACTOR_MAPPING;

public class TreeExtractorManager extends AbstractManager {

    private static final TreeExtractorManager INSTANCE = new TreeExtractorManager();

    protected Map<ComparableItemStack, Pair<Integer, Float>> boostMap = new Object2ObjectOpenHashMap<>();

    protected List<TreeExtractorMapping> recipes = new ArrayList<>();

    protected TreeExtractorManager() {

        super(8);
    }

    public static TreeExtractorManager instance() {

        return INSTANCE;
    }

    protected void clear() {

        boostMap.clear();
        recipes.clear();
    }

    public Stream<TreeExtractorMapping> getRecipes() {

        return recipes.stream();
    }

    // region BOOSTS
    public boolean validBoost(ItemStack item) {

        return boostMap.containsKey(makeNBTComparable(item));
    }

    public void addBoost(TreeExtractorBoost boost) {

        for (ItemStack ingredient : boost.getIngredient().getItems()) {
            boostMap.put(makeNBTComparable(ingredient), Pair.of(boost.getCycles(), boost.getOutputMod()));
        }
    }

    public int getBoostCycles(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeNBTComparable(item)).getLeft() : 0;
    }

    public float getBoostOutputMod(ItemStack item) {

        return validBoost(item) ? boostMap.get(makeNBTComparable(item)).getRight() : 1.0F;
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        for (var mapping : recipeManager.getAllRecipesFor(TREE_EXTRACTOR_MAPPING.get())) {
            recipes.add(mapping.value());
        }
        for (var boost : recipeManager.getAllRecipesFor(TREE_EXTRACTOR_BOOST.get())) {
            addBoost(boost.value());
        }
    }
    // endregion
}
