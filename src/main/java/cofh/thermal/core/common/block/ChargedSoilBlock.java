package cofh.thermal.core.common.block;

import cofh.core.common.block.SoilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.Direction;

public class ChargedSoilBlock extends SoilBlock {

    public static final IntegerProperty CHARGED = IntegerProperty.create("charged", 0, 4);

    public ChargedSoilBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHARGED, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(CHARGED);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {

        BlockPos abovePos = pos.above();
        BlockState aboveState = worldIn.getBlockState(abovePos);

        if (state.canSustainPlant(worldIn, pos, Direction.UP, aboveState).isTrue() && aboveState.isRandomlyTicking()) {
            int charge = state.getValue(CHARGED);
            int boost = 1 + charge;
            for (int i = 0; i < boost; ++i) {
                aboveState.randomTick(worldIn, abovePos, rand);
            }
            if (rand.nextInt(boost) > 0) {
                worldIn.setBlock(pos, state.setValue(CHARGED, Math.max(0, charge - 1)), 2);
            }
        }
    }

    public static void charge(BlockState state, Level worldIn, BlockPos pos) {

        int charge = state.getValue(CHARGED);
        if (charge < 4) {
            worldIn.setBlock(pos, state.setValue(CHARGED, charge + 1), 2);
        } else if (worldIn instanceof ServerLevel) {
            state.randomTick((ServerLevel) worldIn, pos, worldIn.random);
        }
    }

}
