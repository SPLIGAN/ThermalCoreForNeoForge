package cofh.thermal.core.util.recipes.device;

import cofh.lib.util.recipes.SerializableRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;
import static cofh.thermal.core.init.registries.TCoreRecipeSerializers.FISHER_BOOST_SERIALIZER;
import static cofh.thermal.core.init.registries.TCoreRecipeTypes.FISHER_BOOST;

public class FisherBoost extends SerializableRecipe {

    protected final Ingredient ingredient;

    protected final ResourceKey<LootTable> lootTable;
    protected final float outputMod;
    protected final float useChance;

    public FisherBoost(Ingredient inputItem, ResourceKey<LootTable> lootTable, float outputMod, float useChance) {

        this.ingredient = inputItem;
        this.lootTable = lootTable;
        this.outputMod = outputMod;
        this.useChance = useChance;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return FISHER_BOOST_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {

        return FISHER_BOOST.get();
    }

    // region GETTERS
    public Ingredient getIngredient() {

        return ingredient;
    }

    public ResourceKey<LootTable> getLootTable() {

        return lootTable;
    }

    public float getOutputMod() {

        return outputMod;
    }

    public float getUseChance() {

        return useChance;
    }
    // endregion

    // region SERIALIZER
    public static class Serializer implements RecipeSerializer<FisherBoost> {

        public static final MapCodec<FisherBoost> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf(INGREDIENT).forGetter(FisherBoost::getIngredient),
                        ResourceKey.codec(Registries.LOOT_TABLE).optionalFieldOf(LOOT_TABLE, BuiltInLootTables.FISHING_FISH).forGetter(FisherBoost::getLootTable),
                        com.mojang.serialization.Codec.FLOAT.optionalFieldOf(OUTPUT_MOD, 1.0F).forGetter(FisherBoost::getOutputMod),
                        com.mojang.serialization.Codec.FLOAT.optionalFieldOf(USE_CHANCE, 1.0F).forGetter(FisherBoost::getUseChance)
                ).apply(builder, FisherBoost::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, FisherBoost> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<FisherBoost> codec() {

            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FisherBoost> streamCodec() {

            return STREAM_CODEC;
        }

        private static FisherBoost fromNetwork(RegistryFriendlyByteBuf buffer) {

            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ResourceKey<LootTable> lootTable = buffer.readResourceKey(Registries.LOOT_TABLE);
            float outputMod = buffer.readFloat();
            float useChance = buffer.readFloat();

            return new FisherBoost(ingredient, lootTable, outputMod, useChance);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, FisherBoost recipe) {

            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            buffer.writeResourceKey(recipe.lootTable);
            buffer.writeFloat(recipe.outputMod);
            buffer.writeFloat(recipe.useChance);
        }

    }
    // endregion
}
