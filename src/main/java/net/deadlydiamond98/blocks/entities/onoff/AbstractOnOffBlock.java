package net.deadlydiamond98.blocks.entities.onoff;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractOnOffBlock extends BlockWithEntity {

    public static final BooleanProperty TRIGGERED;

    public AbstractOnOffBlock(Settings settings, boolean startState) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(TRIGGERED, startState));
    }

    public AbstractOnOffBlock(Settings settings) {
        this(settings, true);
    }

    public void setOnOffState(World world, BlockPos pos, BlockState state, boolean bl) {
        world.setBlockState(pos, state.with(TRIGGERED, bl));
    }

    public boolean isOn(BlockState state) {
        return state.get(TRIGGERED);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    static {
        TRIGGERED = Properties.TRIGGERED;
    }
}
