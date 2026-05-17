package cofh.thermal.core.compat.patchouli;

import cofh.thermal.core.util.recipes.machine.SmelterRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class SmelterProcessor implements IComponentProcessor {

    private SmelterRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {

        if (!variables.has("recipe"))
            return;
        HolderLookup.Provider registries = level.registryAccess();
        ResourceLocation recipeId = ResourceLocation.parse(variables.get("recipe", registries).asString());
        Optional<? extends RecipeHolder<?>> recipe = level.getRecipeManager().byKey(recipeId);
        if (recipe.isPresent() && recipe.get().value() instanceof SmelterRecipe) {
            this.recipe = (SmelterRecipe) recipe.get().value();
        } else {
            LogManager.getLogger().warn("Thermalpedia missing the smelter recipe: " + recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {

        if (recipe == null)
            return null;
        HolderLookup.Provider registries = level.registryAccess();
        if (key.equals("out"))
            return IVariable.from(recipe.getOutputItems().get(0), registries);
        if (key.startsWith("in")) {
            int index = Integer.parseInt(key.substring(key.length() - 1)) - 1;
            if (recipe.getInputItems().size() <= index)
                return null;
            var items = Arrays.stream(recipe.getInputItems().get(index).getItems()).map(stack -> IVariable.from(stack, registries)).collect(Collectors.toList());
            return IVariable.wrapList(items, registries);
        }
        return null;
    }

}
