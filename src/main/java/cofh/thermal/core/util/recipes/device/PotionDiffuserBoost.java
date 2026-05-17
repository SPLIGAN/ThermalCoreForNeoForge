package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import cofh.thermal.core.util.managers.device.PotionDiffuserManager;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.POTION_DIFFUSER_BOOST_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.POTION_DIFFUSER_BOOST;

public class PotionDiffuserBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected int amplifier;
    protected float durationMod;
    protected int cycles;

    public PotionDiffuserBoost(Ingredient inputItem, int amplifier, float durationMod, int cycles) {

        this.ingredient = inputItem;
        this.amplifier = amplifier;
        this.durationMod = durationMod;
        this.cycles = cycles;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return POTION_DIFFUSER_BOOST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return POTION_DIFFUSER_BOOST.get();
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public int getAmplifier() {

        return amplifier;
    }

    public float getDurationMod() {

        return durationMod;
    }

    public int getCycles() {

        return cycles;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<PotionDiffuserBoost> {

        public static final MapCodec<PotionDiffuserBoost> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf(INGREDIENT).forGetter(PotionDiffuserBoost::getIngredient),
                        com.mojang.serialization.Codec.INT.optionalFieldOf(AMPLIFIER, 0).forGetter(PotionDiffuserBoost::getAmplifier),
                        com.mojang.serialization.Codec.FLOAT.optionalFieldOf(DURATION_MOD, 0.0F).forGetter(PotionDiffuserBoost::getDurationMod),
                        com.mojang.serialization.Codec.INT.optionalFieldOf(CYCLES, PotionDiffuserManager.instance().getDefaultEnergy()).forGetter(PotionDiffuserBoost::getCycles)
                ).apply(builder, PotionDiffuserBoost::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, PotionDiffuserBoost> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<PotionDiffuserBoost> codec() {

            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PotionDiffuserBoost> streamCodec() {

            return STREAM_CODEC;
        }

        private static PotionDiffuserBoost fromNetwork(RegistryFriendlyByteBuf buffer) {

            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int amplifier = buffer.readInt();
            float durationMod = buffer.readFloat();
            int cycles = buffer.readInt();

            return new PotionDiffuserBoost(ingredient, amplifier, durationMod, cycles);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, PotionDiffuserBoost recipe) {

            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            buffer.writeInt(recipe.amplifier);
            buffer.writeFloat(recipe.durationMod);
            buffer.writeInt(recipe.cycles);
        }

    }
    // endregion
}
