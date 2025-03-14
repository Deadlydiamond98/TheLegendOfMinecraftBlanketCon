package net.deadlydiamond98.blocks.loot;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class LootGrass extends PlantBlock {
    public static final IntProperty AGE = Properties.AGE_1;
    public LootGrass(Settings settings) {
        super(settings);
        this.setDefaultState(((BlockState) this.stateManager.getDefaultState()).with(AGE, 1));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());

        if (blockState.isOf(Blocks.GRASS_BLOCK)) {
            return true;
        }
        return false;
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(AGE, 0);
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMature(state) && random.nextInt(32) == 0) {
            world.setBlockState(pos, state.with(AGE, 1), 2);
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    private IntProperty getAgeProperty() {
        return AGE;
    }

    public int getAge(BlockState state) {
        return (Integer) state.get(this.getAgeProperty());
    }

    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) == 1;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !isMature(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }
}
