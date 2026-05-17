package cofh.thermal.lib.common.block.entity;

import cofh.core.common.block.entity.BlockEntityCoFH;
import cofh.core.util.control.IRedstoneControllableTile;
import cofh.core.util.control.ISecurableTile;
import cofh.core.util.control.RedstoneControlModule;
import cofh.core.util.control.SecurityControlModule;
import cofh.thermal.core.common.config.ThermalCoreConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public class LogisticsBlockEntity extends BlockEntityCoFH implements ISecurableTile, IRedstoneControllableTile {

    protected SecurityControlModule securityControl = new SecurityControlModule(this);
    protected RedstoneControlModule redstoneControl = new RedstoneControlModule(this);

    public LogisticsBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        stack = super.createItemStackTag(stack);
        CompoundTag nbt = stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).copyTag();
        if (hasSecurity()) {
            securityControl().write(nbt);
        }
        if (ThermalCoreConfig.keepRSControl.get()) {
            redstoneControl().writeSettings(nbt);
        }
        if (!nbt.isEmpty()) {
            stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(nbt));
        }
        return stack;
    }

    // region NBT
    @Override
    protected void loadAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {

        super.loadAdditional(nbt, registries);

        securityControl.read(nbt);
        redstoneControl.read(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {

        super.saveAdditional(nbt, registries);

        securityControl.write(nbt);
        redstoneControl.write(nbt);
    }
    // endregion

    // region NETWORK

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        securityControl.writeToBuffer(buffer);
        redstoneControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        securityControl.readFromBuffer(buffer);
        redstoneControl.readFromBuffer(buffer);
    }
    // endregion

    // region MODULES
    @Override
    public SecurityControlModule securityControl() {

        return securityControl;
    }

    @Override
    public RedstoneControlModule redstoneControl() {

        return redstoneControl;
    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(Player player, CompoundTag tag) {

        redstoneControl.readSettings(tag);

        onControlUpdate();
    }

    @Override
    public void writeConveyableData(Player player, CompoundTag tag) {

        redstoneControl.writeSettings(tag);
    }
    // endregion
}
