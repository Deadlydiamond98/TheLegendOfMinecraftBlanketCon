package net.deadlydiamond98.blocks.redstoneish.onoff;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColorSwitchBlock extends Block {

    public static final BooleanProperty TRIGGERED;
    public static final BooleanProperty LINKED;

    static {
        TRIGGERED = Properties.TRIGGERED;
        LINKED = Properties.ATTACHED;
    }

    public ColorSwitchBlock(Settings settings, boolean startState) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(TRIGGERED, startState).with(LINKED, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return isOn(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isOn(state) ? getOutlineShape(state, world, pos, context) : VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED, LINKED);
    }

    public void toggleOnOff(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(TRIGGERED, !isOn(state)));
    }

    public void setOnOffState(World world, BlockPos pos, BlockState state, boolean bl) {
        world.setBlockState(pos, state.with(TRIGGERED, bl));
    }

    public boolean isOn(BlockState state) {
        return state.get(TRIGGERED);
    }

    public void setLinked(World world, BlockPos pos, BlockState state, boolean bl) {
        world.setBlockState(pos, state.with(LINKED, bl));
    }

    public boolean isLinked(BlockState state) {
        return state.get(LINKED);
    }

    public static class OnBlock extends ColorSwitchBlock {
        public OnBlock(Settings settings) {
            super(settings, true);
        }

        @Override
        public boolean isOn(BlockState state) {
            return super.isOn(state);
        }
    }

    public static class OffBlock extends ColorSwitchBlock {
        public OffBlock(Settings settings) {
            super(settings, false);
        }

        @Override
        public boolean isOn(BlockState state) {
            return super.isOn(state);
        }
    }
}
