package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fluids.FluidStack;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.HIVE_EXTRACTOR_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.HIVE_EXTRACTOR_MAPPING;

public class HiveExtractorMapping extends SerializableRecipe {

    protected final Block hive;
    protected final ItemStack item;
    protected final FluidStack fluid;

    public HiveExtractorMapping(Block hive, ItemStack item, FluidStack fluid) {

        this.hive = hive;
        this.item = item;
        this.fluid = fluid;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return HIVE_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return HIVE_EXTRACTOR_MAPPING.get();
    }

    // region GETTERS
    public Block getHive() {

        return hive;
    }

    public ItemStack getItem() {

        return item;
    }

    public FluidStack getFluid() {

        return fluid;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<HiveExtractorMapping> {

        public static final MapCodec<HiveExtractorMapping> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        BuiltInRegistries.BLOCK.byNameCodec().fieldOf(HIVE).forGetter(HiveExtractorMapping::getHive),
                        ItemStack.CODEC.fieldOf(ITEM).forGetter(HiveExtractorMapping::getItem),
                        FluidStack.CODEC.fieldOf(FLUID).forGetter(HiveExtractorMapping::getFluid)
                ).apply(builder, HiveExtractorMapping::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, HiveExtractorMapping> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<HiveExtractorMapping> codec() {

            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HiveExtractorMapping> streamCodec() {

            return STREAM_CODEC;
        }

        private static HiveExtractorMapping fromNetwork(RegistryFriendlyByteBuf buffer) {

            Block hive = BuiltInRegistries.BLOCK.get(buffer.readResourceLocation());
            ItemStack item = ItemStack.STREAM_CODEC.decode(buffer);
            FluidStack fluid = FluidStack.STREAM_CODEC.decode(buffer);

            return new HiveExtractorMapping(hive, item, fluid);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, HiveExtractorMapping recipe) {

            buffer.writeResourceLocation(getRegistryName(recipe.hive));
            ItemStack.STREAM_CODEC.encode(buffer, recipe.item);
            FluidStack.STREAM_CODEC.encode(buffer, recipe.fluid);
        }

    }
    // endregion
}
