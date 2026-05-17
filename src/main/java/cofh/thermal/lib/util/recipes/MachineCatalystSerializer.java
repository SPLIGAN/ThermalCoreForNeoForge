package cofh.thermal.lib.util.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static cofh.lib.util.recipes.RecipeJsonUtils.*;

public class MachineCatalystSerializer<T extends ThermalCatalyst> implements RecipeSerializer<T> {

    protected final IFactory<T> factory;
    protected final MapCodec<T> codec;

    public MachineCatalystSerializer(IFactory<T> factory) {

        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf(INGREDIENT).forGetter(recipe -> recipe.ingredient),
                        Codec.FLOAT.optionalFieldOf(PRIMARY_MOD, 1.0F).forGetter(recipe -> recipe.primaryMod),
                        Codec.FLOAT.optionalFieldOf(SECONDARY_MOD, 1.0F).forGetter(recipe -> recipe.secondaryMod),
                        Codec.FLOAT.optionalFieldOf(ENERGY_MOD, 1.0F).forGetter(recipe -> recipe.energyMod),
                        Codec.FLOAT.optionalFieldOf(MIN_CHANCE, 0.0F).forGetter(recipe -> recipe.minChance),
                        Codec.FLOAT.optionalFieldOf(USE_CHANCE, 1.0F).forGetter(recipe -> recipe.useChance)
                ).apply(builder, factory::create)
        );
    }

    @Override
    public MapCodec<T> codec() {

        return codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {

        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    //    @Override
    //    public T fromJson(ResourceLocation recipeId, JsonObject json) {
    //
    //        Ingredient ingredient;
    //
    //        float primaryMod = 1.0F;
    //        float secondaryMod = 1.0F;
    //        float energyMod = 1.0F;
    //        float minChance = 0.0F;
    //        float useChance = 1.0F;
    //
    //        /* INPUT */
    //        ingredient = parseIngredient(json.get(INGREDIENT));
    //
    //        if (json.has(PRIMARY_MOD)) {
    //            primaryMod = json.get(PRIMARY_MOD).getAsFloat();
    //        }
    //        if (json.has(SECONDARY_MOD)) {
    //            secondaryMod = json.get(SECONDARY_MOD).getAsFloat();
    //        }
    //        if (json.has(ENERGY_MOD)) {
    //            energyMod = json.get(ENERGY_MOD).getAsFloat();
    //        }
    //        if (json.has(MIN_CHANCE)) {
    //            minChance = json.get(MIN_CHANCE).getAsFloat();
    //        }
    //        if (json.has(USE_CHANCE)) {
    //            useChance = json.get(USE_CHANCE).getAsFloat();
    //        }
    //        return factory.create(ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    //    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer) {

        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

        float primaryMod = buffer.readFloat();
        float secondaryMod = buffer.readFloat();
        float energyMod = buffer.readFloat();
        float minChance = buffer.readFloat();
        float useChance = buffer.readFloat();

        return factory.create(ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {

        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);

        buffer.writeFloat(recipe.primaryMod);
        buffer.writeFloat(recipe.secondaryMod);
        buffer.writeFloat(recipe.energyMod);
        buffer.writeFloat(recipe.minChance);
        buffer.writeFloat(recipe.useChance);
    }

    public interface IFactory<T extends ThermalCatalyst> {

        T create(Ingredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance);

    }

}
