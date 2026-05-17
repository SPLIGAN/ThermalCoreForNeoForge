package cofh.thermal.core.common.item;

import cofh.core.common.item.FluidContainerItem;
import cofh.core.util.ProxyUtils;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.common.entity.projectile.ThrownFlorb;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static cofh.core.util.helpers.FluidHelper.addPotionTooltip;
import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.lib.util.helpers.StringHelper.*;

public class FlorbItem extends FluidContainerItem {

    protected static int cooldown = 0;

    public FlorbItem(Properties builder, int fluidCapacity, Predicate<FluidStack> validator) {

        super(builder, fluidCapacity, validator);
        setEnchantability(0);

        ProxyUtils.registerColorable(this);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        FluidStack fluid = getFluid(stack);
        if (!fluid.isEmpty()) {
            tooltip.add(StringHelper.getFluidName(fluid));
        }
        if (FluidHelper.hasPotionTag(fluid)) {
            tooltip.add(getEmptyLine());
            tooltip.add(getTextComponent(localize("info.cofh.effects") + ":"));
            addPotionTooltip(fluid, tooltip);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {

        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        if (getFluid(stack).isEmpty()) {
            return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
        }
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (MathHelper.RANDOM.nextFloat() * 0.4F + 0.8F));
        if (cooldown > 0) {
            playerIn.getCooldowns().addCooldown(this, cooldown);
        }
        if (!worldIn.isClientSide) {
            createFlorb(stack, worldIn, playerIn);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }

    protected void createFlorb(ItemStack stack, Level world, Player player) {

        ThrownFlorb florb = new ThrownFlorb(world, player);
        ItemStack throwStack = cloneStack(stack, 1);
        throwStack.setDamageValue(1);
        florb.setItem(throwStack);
        florb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.5F);
        world.addFreshEntity(florb);
    }

    // region IFluidContainerItem
    @Override
    public int fill(ItemStack container, FluidStack resource, FluidAction action) {

        if (!getFluid(container).isEmpty() || resource.getAmount() < getCapacity(container)) {
            return 0;
        }
        return super.fill(container, resource, action);
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, FluidAction action) {

        if (container.getDamageValue() <= 0) {
            return FluidStack.EMPTY;
        }
        return super.drain(container, maxDrain, action);
    }
    // endregion

    // region DISPENSER BEHAVIOR
    private static final DefaultDispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {

            Level level = source.level();
            Position position = DispenserBlock.getDispensePosition(source);
            ThrownFlorb florb = new ThrownFlorb(level, position.x(), position.y(), position.z());
            ItemStack throwStack = cloneStack(stack, 1);
            throwStack.setDamageValue(1);
            florb.setItem(throwStack);
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            florb.shoot(direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(), 1.0F, 3.0F);
            level.addFreshEntity(florb);
            stack.shrink(1);
            return stack;
        }
    };
    // endregion
}
