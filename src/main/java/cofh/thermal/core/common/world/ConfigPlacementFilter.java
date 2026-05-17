package cofh.thermal.core.common.world;

import cofh.thermal.core.common.config.ThermalWorldConfig;
import cofh.thermal.core.init.registries.TCorePlacementModifiers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ConfigPlacementFilter extends PlacementFilter {

    public static final MapCodec<ConfigPlacementFilter> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(com.mojang.serialization.Codec.STRING.fieldOf("config").forGetter(f -> f.name)).apply(builder, ConfigPlacementFilter::new));

    protected String name;

    public ConfigPlacementFilter(String name) {

        this.name = name;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {

        return ThermalWorldConfig.getFeatureConfig(name).shouldGenerate();
    }

    @Override
    public PlacementModifierType<?> type() {

        return TCorePlacementModifiers.CONFIG_FILTER.get();
    }

}
