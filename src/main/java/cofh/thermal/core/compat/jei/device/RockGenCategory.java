package cofh.thermal.core.compat.jei.device;

import cofh.core.util.helpers.RenderHelper;
import cofh.thermal.core.client.gui.device.DeviceRockGenScreen;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
import cofh.thermal.lib.compat.jei.Drawables;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.lib.compat.jei.Drawables.SLOT;
import static cofh.thermal.lib.util.ThermalIDs.ID_DEVICE_ROCK_GEN;

public class RockGenCategory implements IRecipeCategory<RecipeHolder<RockGenMapping>> {

    protected static final FluidStack LAVA_FLUID = new FluidStack(Fluids.LAVA, BUCKET_VOLUME);

    protected final RecipeType<RecipeHolder<RockGenMapping>> type;
    protected IDrawable background;
    protected IDrawable icon;
    protected Component name;

    protected IDrawableStatic slot;
    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public RockGenCategory(IGuiHelper guiHelper, ItemStack icon, RecipeType<RecipeHolder<RockGenMapping>> type) {

        this.type = type;
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, icon);

        background = guiHelper.drawableBuilder(DeviceRockGenScreen.TEXTURE, 26, 11, 140, 62)
                .addPadding(0, 0, 16, 8)
                .build();
        name = getTextComponent(BLOCKS.get(ID_DEVICE_ROCK_GEN).getDescriptionId());

        slot = Drawables.getDrawables(guiHelper).getSlot(SLOT);
        progressFluidBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_ARROW_FLUID);
        progressFluid = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_ARROW_FLUID), 40, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public RecipeType<RecipeHolder<RockGenMapping>> getRecipeType() {

        return type;
    }

    @Override
    public Component getTitle() {

        return name;
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public IDrawable getIcon() {

        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RockGenMapping> recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 23, 13)
                .addIngredients(NeoForgeTypes.FLUID_STACK, List.of(LAVA_FLUID))
                .setFluidRenderer(BUCKET_VOLUME, false, 16, 16);

        Block adjacent = recipe.value().getAdjacent();
        Block below = recipe.value().getBelow();

        builder.addSlot(RecipeIngredientRole.OUTPUT, 115, 24)
                .addItemStack(recipe.value().getResult());

        if (adjacent instanceof LiquidBlock liquidBlock) {
            builder.addSlot(RecipeIngredientRole.INPUT, 45, 13)
                    .addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(liquidBlock.fluid, BUCKET_VOLUME)))
                    .setFluidRenderer(BUCKET_VOLUME, false, 16, 16);
        } else if (adjacent != Blocks.AIR) {
            builder.addSlot(RecipeIngredientRole.INPUT, 45, 13)
                    .addItemStack(new ItemStack(adjacent));
        }
        if (below instanceof LiquidBlock liquidBlock) {
            builder.addSlot(RecipeIngredientRole.INPUT, 34, 34)
                    .addIngredients(NeoForgeTypes.FLUID_STACK, List.of(new FluidStack(liquidBlock.fluid, BUCKET_VOLUME)))
                    .setFluidRenderer(BUCKET_VOLUME, false, 16, 16);
        } else if (below != Blocks.AIR) {
            builder.addSlot(RecipeIngredientRole.INPUT, 34, 34)
                    .addItemStack(new ItemStack(below));
        }
    }

    @Override
    public void draw(RecipeHolder<RockGenMapping> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        if (recipe.value().getBelow() != Blocks.AIR) {
            slot.draw(guiGraphics, 33, 33);
        }
        RenderHelper.drawFluid(guiGraphics, 74, 24, LAVA_FLUID, 24, 16);
        progressFluidBackground.draw(guiGraphics, 74, 24);
        progressFluid.draw(guiGraphics, 74, 24);
    }

}

