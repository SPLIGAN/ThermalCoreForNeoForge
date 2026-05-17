package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleFluidFuelManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.MAGMATIC_FUEL;

public class MagmaticFuelManager extends SingleFluidFuelManager {

    private static final MagmaticFuelManager INSTANCE = new MagmaticFuelManager();
    protected static final int DEFAULT_ENERGY = 100000;

    public static MagmaticFuelManager instance() {

        return INSTANCE;
    }

    private MagmaticFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(MAGMATIC_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
    }
    // endregion
}
