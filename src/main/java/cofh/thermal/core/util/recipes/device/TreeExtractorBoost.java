package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.TREE_EXTRACTOR_BOOST_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.TREE_EXTRACTOR_BOOST;

public class TreeExtractorBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected float outputMod;
    protected int cycles;

    public TreeExtractorBoost(Ingredient inputItem, float outputMod, int cycles) {

        this.ingredient = inputItem;
        this.outputMod = outputMod;
        this.cycles = cycles;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return TREE_EXTRACTOR_BOOST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return TREE_EXTRACTOR_BOOST.get();
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public float getOutputMod() {

        return outputMod;
    }

    public int getCycles() {

        return cycles;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<TreeExtractorBoost> {

        public static final MapCodec<TreeExtractorBoost> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf(INGREDIENT).forGetter(TreeExtractorBoost::getIngredient),
                        com.mojang.serialization.Codec.FLOAT.optionalFieldOf(OUTPUT_MOD, 1.0F).forGetter(TreeExtractorBoost::getOutputMod),
                        com.mojang.serialization.Codec.INT.optionalFieldOf(CYCLES, TreeExtractorManager.instance().getDefaultEnergy()).forGetter(TreeExtractorBoost::getCycles)
                ).apply(builder, TreeExtractorBoost::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, TreeExtractorBoost> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<TreeExtractorBoost> codec() {

            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TreeExtractorBoost> streamCodec() {

            return STREAM_CODEC;
        }

        private static TreeExtractorBoost fromNetwork(RegistryFriendlyByteBuf buffer) {

            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            float outputMod = buffer.readFloat();
            int cycles = buffer.readInt();

            return new TreeExtractorBoost(ingredient, outputMod, cycles);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, TreeExtractorBoost recipe) {

            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            buffer.writeFloat(recipe.outputMod);
            buffer.writeInt(recipe.cycles);
        }

    }
    // endregion
}
