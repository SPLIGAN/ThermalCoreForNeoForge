package cofh.thermal.core.compat.patchouli;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class CraftingProcessor implements IComponentProcessor {

    private CraftingRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        HolderLookup.Provider registries = level.registryAccess();
        ResourceLocation recipeId = ResourceLocation.parse(variables.get("recipe", registries).asString());
        Optional<? extends RecipeHolder<?>> recipe = level.getRecipeManager().byKey(recipeId);
        if (recipe.isPresent() && recipe.get().value() instanceof CraftingRecipe) {
            this.recipe = (CraftingRecipe) recipe.get().value();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the crafting recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {

        if (recipe == null) {
            return null;
        }
        HolderLookup.Provider registries = level.registryAccess();
        if (key.equals("out")) {
            return IVariable.from(recipe.getResultItem(registries), registries);
        } else if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1));
            if (recipe instanceof ShapedRecipe) {
                int width = ((ShapedRecipe) recipe).getWidth();
                if (width < 3) {
                    if (index % 3 >= width) {
                        return null;
                    }
                    index = index * width / 3 + index % 3;
                }
            }
            if (recipe.getIngredients().size() <= index) {
                return null;
            }
            var items = Arrays.stream(recipe.getIngredients().get(index).getItems()).map(stack -> IVariable.from(stack, registries)).collect(Collectors.toList());
            return IVariable.wrapList(items, registries);
        } else if (key.equals("title")) {
            return IVariable.from(recipe.getResultItem(registries).getHoverName(), registries);
        } else if (key.equals("show")) {
            return IVariable.wrap(true);
        }
        return null;
    }

}
