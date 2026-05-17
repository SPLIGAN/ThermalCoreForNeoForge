package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleFluidFuelManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.COMPRESSION_FUEL;

public class CompressionFuelManager extends SingleFluidFuelManager {

    private static final CompressionFuelManager INSTANCE = new CompressionFuelManager();
    protected static final int DEFAULT_ENERGY = 100000;

    public static CompressionFuelManager instance() {

        return INSTANCE;
    }

    private CompressionFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(COMPRESSION_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
    }
    // endregion
}
