package cofh.thermal.core.util.managers.dynamo;

import cofh.thermal.lib.util.managers.SingleItemFuelManager;
import net.minecraft.world.item.crafting.RecipeManager;

import static cofh.thermal.core.init.registries.TCoreRecipeTypes.LAPIDARY_FUEL;

public class LapidaryFuelManager extends SingleItemFuelManager {

    private static final LapidaryFuelManager INSTANCE = new LapidaryFuelManager();
    protected static final int DEFAULT_ENERGY = 16000;

    public static LapidaryFuelManager instance() {

        return INSTANCE;
    }

    private LapidaryFuelManager() {

        super(DEFAULT_ENERGY);
    }

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        var recipes = recipeManager.getAllRecipesFor(LAPIDARY_FUEL.get());
        for (var holder : recipes) {
            addFuel(holder.value());
        }
    }
    // endregion
}
