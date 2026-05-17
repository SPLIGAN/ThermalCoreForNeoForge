package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.util.managers.device.RockGenManager;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static cofh.lib.util.Utils.getRegistryName;
import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.ROCK_GEN_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.ROCK_GEN_MAPPING;

public class RockGenMapping extends SerializableRecipe {

    protected final int time;
    protected final Block below;
    protected final Block adjacent;
    protected final ItemStack result;

    public RockGenMapping(int time, Block below, Block adjacent, ItemStack result) {

        this.time = time;
        this.below = below;
        this.adjacent = adjacent;
        this.result = result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return ROCK_GEN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return ROCK_GEN_MAPPING.get();
    }

    // region GETTERS
    public int getTime() {

        return time;
    }

    public Block getBelow() {

        return below;
    }

    public Block getAdjacent() {

        return adjacent;
    }

    public ItemStack getResult() {

        return result;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<RockGenMapping> {

        public static final MapCodec<RockGenMapping> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        com.mojang.serialization.Codec.INT.optionalFieldOf(TIME, RockGenManager.instance().getDefaultEnergy()).forGetter(RockGenMapping::getTime),
                        BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf(BELOW, Blocks.AIR).forGetter(RockGenMapping::getBelow),
                        BuiltInRegistries.BLOCK.byNameCodec().fieldOf(ADJACENT).forGetter(RockGenMapping::getAdjacent),
                        ItemStack.CODEC.fieldOf(RESULT).forGetter(RockGenMapping::getResult)
                ).apply(builder, RockGenMapping::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, RockGenMapping> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<RockGenMapping> codec() {

            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RockGenMapping> streamCodec() {

            return STREAM_CODEC;
        }

        private static RockGenMapping fromNetwork(RegistryFriendlyByteBuf buffer) {

            int time = buffer.readInt();
            Block below = BuiltInRegistries.BLOCK.get(buffer.readResourceLocation());
            Block adjacent = BuiltInRegistries.BLOCK.get(buffer.readResourceLocation());
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);

            return new RockGenMapping(time, below, adjacent, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, RockGenMapping recipe) {

            buffer.writeInt(recipe.time);
            buffer.writeResourceLocation(getRegistryName(recipe.below));
            buffer.writeResourceLocation(getRegistryName(recipe.adjacent));
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }

    }
    // endregion
}
