package cofh.thermal.lib.util.references;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class ThermalTags {

    private ThermalTags() {

    }

    public static class Blocks {

        public static final TagKey<Block> LOGS_RUBBERWOOD = commonTag("rubberwood_logs");

        public static final TagKey<Block> TREE_EXTRACTOR_GROUND = thermalTag("devices/tree_extractor_ground");

        public static final TagKey<Block> DUCTS = thermalTag("ducts");
        public static final TagKey<Block> DYNAMOS = thermalTag("dynamos");
        public static final TagKey<Block> MACHINES = thermalTag("machines");
        public static final TagKey<Block> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Block> ROCKWOOL = thermalTag("rockwool");

        // region HELPERS
        private static TagKey<Block> thermalTag(String name) {

            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, name));
        }

        private static TagKey<Block> commonTag(String name) {

            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
        // endregion
    }

    public static class Items {

        public static final TagKey<Item> LOGS_RUBBERWOOD = commonTag("rubberwood_logs");

        public static final TagKey<Item> BITUMEN = commonTag("bitumen");
        public static final TagKey<Item> COAL_COKE = commonTag("coal_coke");
        public static final TagKey<Item> ROSIN = commonTag("rosin");
        public static final TagKey<Item> SAWDUST = commonTag("sawdust");
        public static final TagKey<Item> SLAG = commonTag("slag");
        public static final TagKey<Item> TAR = commonTag("tar");

        public static final TagKey<Item> MACHINE_DIES = thermalTag("crafting/dies");
        public static final TagKey<Item> MACHINE_CASTS = thermalTag("crafting/casts");

        public static final TagKey<Item> DUCTS = thermalTag("ducts");
        public static final TagKey<Item> DYNAMOS = thermalTag("dynamos");
        public static final TagKey<Item> MACHINES = thermalTag("machines");
        public static final TagKey<Item> HARDENED_GLASS = thermalTag("glass/hardened");
        public static final TagKey<Item> ROCKWOOL = thermalTag("rockwool");

        // region HELPERS
        private static TagKey<Item> thermalTag(String name) {

            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, name));
        }

        private static TagKey<Item> commonTag(String name) {

            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
        // endregion
    }

}
