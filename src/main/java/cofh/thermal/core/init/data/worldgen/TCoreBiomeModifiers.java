package cofh.thermal.core.init.data.worldgen;

import cofh.thermal.core.init.registries.TCoreEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.DatapackHelper.holderSetUnion;

public class TCoreBiomeModifiers {

    public static final ResourceKey<BiomeModifier> BASALZ_SPAWN_BASALT_DELTAS = createKey("basalz_spawn_basalt_deltas");
    public static final ResourceKey<BiomeModifier> BASALZ_SPAWN_CAVES = createKey("basalz_spawn_caves");
    public static final ResourceKey<BiomeModifier> BASALZ_SPAWN_PEAKS = createKey("basalz_spawn_peaks");

    public static final ResourceKey<BiomeModifier> BLITZ_SPAWN_BADLANDS = createKey("blitz_spawn_badlands");
    public static final ResourceKey<BiomeModifier> BLITZ_SPAWN_SANDY = createKey("blitz_spawn_sandy");
    public static final ResourceKey<BiomeModifier> BLITZ_SPAWN_SAVANNA = createKey("blitz_spawn_savanna");

    public static final ResourceKey<BiomeModifier> BLIZZ_SPAWN_SNOWY = createKey("blizz_spawn_snowy");

    public static void init(BootstrapContext<BiomeModifier> context) {

        var isBadlandsTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_BADLANDS);
        var isSandyTag = context.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_SANDY);
        var isSavannaTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_SAVANNA);
        var isSnowyTag = context.lookup(Registries.BIOME).getOrThrow(Tags.Biomes.IS_SNOWY);

        var isBasaltDelta = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.BASALT_DELTAS));
        var isDripstoneCaves = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.DRIPSTONE_CAVES));
        var isLushCaves = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.LUSH_CAVES));
        var isStonyPeaks = HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(Biomes.STONY_PEAKS));

        var caveBiomes = holderSetUnion(isDripstoneCaves, isLushCaves);

        registerMob(context, BASALZ_SPAWN_BASALT_DELTAS, isBasaltDelta, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BASALZ.get(), 50, 2, 4)));
        registerMob(context, BASALZ_SPAWN_CAVES, caveBiomes, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BASALZ.get(), 25, 1, 3)));
        registerMob(context, BASALZ_SPAWN_PEAKS, isStonyPeaks, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BASALZ.get(), 35, 1, 3)));

        registerMob(context, BLITZ_SPAWN_BADLANDS, isBadlandsTag, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BLITZ.get(), 35, 1, 3)));
        registerMob(context, BLITZ_SPAWN_SANDY, isSandyTag, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BLITZ.get(), 25, 1, 3)));
        registerMob(context, BLITZ_SPAWN_SAVANNA, isSavannaTag, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BLITZ.get(), 50, 2, 4)));

        registerMob(context, BLIZZ_SPAWN_SNOWY, isSnowyTag, List.of(new MobSpawnSettings.SpawnerData(TCoreEntities.BLIZZ.get(), 50, 1, 4)));
    }

    // region HELPERS
    private static ResourceKey<BiomeModifier> createKey(String name) {

        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(ID_THERMAL, name));
    }

    private static void registerMob(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> biomeMod, HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners) {

        context.register(biomeMod, new BiomeModifiers.AddSpawnsBiomeModifier(biomes, spawners));
    }
    // endregion

}
