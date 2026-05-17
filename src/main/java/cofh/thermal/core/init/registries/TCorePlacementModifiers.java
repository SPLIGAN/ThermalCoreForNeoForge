package cofh.thermal.core.init.registries;

import cofh.thermal.core.common.world.ConfigPlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;

import static cofh.thermal.core.ThermalCore.PLACEMENT_MODIFIERS;

public class TCorePlacementModifiers {

    private TCorePlacementModifiers() {

    }

    public static void register() {

    }

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigPlacementFilter>> CONFIG_FILTER =
            PLACEMENT_MODIFIERS.register("config", () -> () -> ConfigPlacementFilter.CODEC);

}
