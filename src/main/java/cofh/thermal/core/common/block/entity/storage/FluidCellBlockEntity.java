package cofh.thermal.core.common.block.entity.storage;

import cofh.core.common.network.packet.client.TileStatePacket;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.core.util.helpers.FluidHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.common.fluid.FluidHandlerRestrictionWrapper;
import cofh.lib.common.fluid.FluidStorageCoFH;
import cofh.lib.common.fluid.FluidStorageRestrictable;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.BlockHelper;
import cofh.thermal.core.common.inventory.storage.FluidCellMenu;
import cofh.thermal.lib.common.block.entity.StorageCellBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.core.client.renderer.model.ModelUtils.*;
import static cofh.core.util.helpers.FluidHelper.getFluidHandlerCap;
import static cofh.lib.api.StorageGroup.ACCESSIBLE;
import static cofh.lib.util.Constants.BUCKET_VOLUME;
import static cofh.lib.util.Constants.TANK_MEDIUM;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.common.config.ThermalCoreConfig.storageAugments;
import static cofh.thermal.core.init.registries.TCoreBlockEntities.FLUID_CELL_TILE;
import static cofh.thermal.lib.util.ThermalAugmentRules.createAllowValidator;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class FluidCellBlockEntity extends StorageCellBlockEntity implements ITickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_FILTER);

    public static final int BASE_CAPACITY = TANK_MEDIUM * 4;

    protected FluidStorageCoFH fluidStorage = new FluidStorageRestrictable(BASE_CAPACITY, fluid -> filter.valid(fluid))
            .setTransferLimits(() -> amountInput, () -> amountOutput);

    public FluidCellBlockEntity(BlockPos pos, BlockState state) {

        super(FLUID_CELL_TILE.get(), pos, state);

        amountInput = BUCKET_VOLUME;
        amountOutput = BUCKET_VOLUME;

        tankInv.addTank(fluidStorage, ACCESSIBLE);

        transferControl.initControl(false, true);

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    //    @Override
    //    public void neighborChanged(Block blockIn, BlockPos fromPos) {
    //
    //        super.neighborChanged(blockIn, fromPos);
    //
    //        // TODO: Handle caching of neighbor caps.
    //    }

    @Override
    public void tickServer() {

        if (redstoneControl.getState()) {
            transferOut();
            transferIn();
        }
        if (Utils.timeCheck() || fluidStorage.getFluidStack().getFluid() != renderFluid.getFluid()) {
            updateTrackers(true);
        }
    }

    @Override
    public int getLightValue() {

        return FluidHelper.luminosity(renderFluid);
    }

    protected void transferIn() {

        if (!transferControl.getTransferIn()) {
            return;
        }
        if (amountInput <= 0 || fluidStorage.isFull()) {
            return;
        }
        for (int i = inputTracker; i < 6 && fluidStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.from3DDataValue(i));
            }
        }
        for (int i = 0; i < inputTracker && fluidStorage.getSpace() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isInput()) {
                attemptTransferIn(Direction.from3DDataValue(i));
            }
        }
        ++inputTracker;
        inputTracker %= 6;
    }

    protected void transferOut() {

        if (!transferControl.getTransferOut()) {
            return;
        }
        if (amountOutput <= 0 || fluidStorage.isEmpty()) {
            return;
        }
        for (int i = outputTracker; i < 6 && fluidStorage.getAmount() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.from3DDataValue(i));
            }
        }
        for (int i = 0; i < outputTracker && fluidStorage.getAmount() > 0; ++i) {
            if (reconfigControl.getSideConfig(i).isOutput()) {
                attemptTransferOut(Direction.from3DDataValue(i));
            }
        }
        ++outputTracker;
        outputTracker %= 6;
    }

    protected void attemptTransferIn(Direction side) {

        FluidHelper.extractFromAdjacent(this, fluidStorage, Math.min(amountInput, fluidStorage.getSpace()), side);
    }

    // This is used rather than the generic in FluidHelper as it can be optimized somewhat.
    protected void attemptTransferOut(Direction side) {

        BlockEntity adjTile = BlockHelper.getAdjacentTileEntity(this, side);
        if (adjTile != null) {
            var handler = getFluidHandlerCap(adjTile, side.getOpposite());
            if (handler != null) {
                fluidStorage.modify(-handler.fill(fluidStorage.getFluidStack().copyWithAmount(Math.min(amountOutput, fluidStorage.getAmount())), EXECUTE));
            }
        }
    }

    @Override
    protected boolean keepFluids() {

        return true;
    }

    @Override
    public int getMaxInput() {

        return fluidStorage.getCapacity() / 4;
    }

    @Override
    public int getMaxOutput() {

        return fluidStorage.getCapacity() / 4;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new FluidCellMenu(i, level, worldPosition, inventory, player);
    }

    @Nonnull
    @Override
    public ModelData getModelData() {

        return ModelData.builder()
                .with(SIDES, reconfigControl().getRawSideConfig())
                .with(FACING, reconfigControl.getFacing())
                .with(FLUID, renderFluid)
                .with(LEVEL, levelTracker)
                .build();
    }

    @Override
    protected void updateTrackers(boolean send) {

        renderFluid = fluidStorage.getFluidStack().copyWithAmount(BUCKET_VOLUME);

        int curScale = fluidStorage.getAmount() > 0 ? 1 + (int) (fluidStorage.getRatio() * 14) : 0;
        if (curScale != compareTracker) {
            compareTracker = curScale;
            if (send) {
                setChanged();
            }
        }
        if (fluidStorage.isCreative()) {
            curScale = fluidStorage.isEmpty() ? 10 : 9;
        } else {
            curScale = fluidStorage.getAmount() > 0 ? 1 + Math.min((int) (fluidStorage.getRatio() * 8), 7) : 0;
        }
        if (levelTracker != curScale) {
            levelTracker = curScale;
            if (send) {
                TileStatePacket.sendToClient(this);
            }
        }
    }

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion

    // region CAPABILITIES
    protected IFluidHandler inputFluidCap = null;
    protected IFluidHandler outputFluidCap = null;

    @Override
    protected void updateHandlers() {

        fluidCap = fluidStorage;
        inputFluidCap = new FluidHandlerRestrictionWrapper(fluidStorage, true, false);
        outputFluidCap = new FluidHandlerRestrictionWrapper(fluidStorage, false, true);
    }

    @Override
    public IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {

        if (side == null) {
            return fluidCap;
        }
        return switch (reconfigControl.getSideConfig(side)) {
            case SIDE_NONE -> null;
            case SIDE_INPUT -> inputFluidCap;
            case SIDE_OUTPUT -> outputFluidCap;
            default -> super.getFluidHandlerCapability(side);
        };
    }
    // endregion
}
