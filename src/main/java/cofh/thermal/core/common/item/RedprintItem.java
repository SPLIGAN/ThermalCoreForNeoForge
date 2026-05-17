package cofh.thermal.core.common.item;

import cofh.core.common.item.ItemCoFH;
import cofh.core.util.ProxyUtils;
import cofh.lib.api.IConveyableData;
import cofh.lib.api.control.ISecurable;
import cofh.lib.api.item.IPlacementItem;
import cofh.lib.init.CoFHDataComponents;
import cofh.lib.util.CoFHItemData;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.lib.util.helpers.StringHelper.canLocalize;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static net.minecraft.ChatFormatting.DARK_GRAY;
import static net.minecraft.ChatFormatting.GRAY;

public class RedprintItem extends ItemCoFH implements IPlacementItem {

    public RedprintItem(Properties builder) {

        super(builder);

        ProxyUtils.registerItemModelProperty(this, ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "has_data"), ((stack, world, entity, seed) -> hasStoredData(stack) ? 1F : 0F));
    }

    private static boolean hasStoredData(ItemStack stack) {

        return !CoFHItemData.getTag(stack).isEmpty();
    }

    private static void clearStoredData(ItemStack stack) {

        stack.remove(CoFHDataComponents.COFH_ITEM_DATA.get());
        stack.remove(DataComponents.CUSTOM_DATA);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        CompoundTag conveyableData = CoFHItemData.getTag(stack);

        if (conveyableData.isEmpty()) {
            tooltip.add(getTextComponent("info.thermal.redprint.use").withStyle(GRAY));
        } else {
            tooltip.add(getTextComponent("info.thermal.redprint.use.contents").withStyle(GRAY));
            tooltip.add(getTextComponent("info.thermal.redprint.use.sneak").withStyle(DARK_GRAY));

            tooltip.add(getTextComponent("info.thermal.redprint.contents"));
            for (String type : conveyableData.getAllKeys()) {
                if (!canLocalize("info.thermal.redprint.data." + type)) {
                    tooltip.add(getTextComponent("info.thermal.redprint.unknown")
                            .withStyle(DARK_GRAY));
                }
                tooltip.add(Component.literal(" - ")
                        .append(getTextComponent("info.thermal.redprint.data." + type)
                                .withStyle(GRAY))
                );
            }
        }
    }

    public Rarity getRarity(ItemStack stack) {

        return hasStoredData(stack) ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    protected boolean useDelegate(ItemStack stack, UseOnContext context) {

        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (player == null || Utils.isClientWorld(world)) {
            return false;
        }
        if (player.isSecondaryUseActive() && context.getHand() == InteractionHand.MAIN_HAND) {
            if (hasStoredData(stack)) {
                player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.3F);
                clearStoredData(stack);
            }
            return true;
        }
        BlockPos pos = context.getClickedPos();
        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof ISecurable && !((ISecurable) tile).canAccess(player)) {
            return false;
        }
        if (tile instanceof IConveyableData conveyableTile) {
            if (!hasStoredData(stack) && context.getHand() == InteractionHand.MAIN_HAND) {
                conveyableTile.writeConveyableData(player, CoFHItemData.getTag(stack));
                tile.setChanged();
                if (!hasStoredData(stack)) {
                    return false;
                } else {
                    player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.7F);
                }
            } else if (hasStoredData(stack)) {
                conveyableTile.readConveyableData(player, CoFHItemData.getTag(stack));
                player.level.playSound(null, player.blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.5F, 0.8F);
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), context.getItemInHand()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        return player.mayUseItemAt(context.getClickedPos(), context.getClickedFace(), stack) && useDelegate(stack, context) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            if (hasStoredData(stack)) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
            }
            clearStoredData(stack);
        }
        player.swing(hand);
        return InteractionResultHolder.success(stack);
    }

    // region IPlacementItem
    @Override
    public boolean onBlockPlacement(ItemStack stack, UseOnContext context) {

        return useDelegate(stack, context);
    }
    // endregion
}
