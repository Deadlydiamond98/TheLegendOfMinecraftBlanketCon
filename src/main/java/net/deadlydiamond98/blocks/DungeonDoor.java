package net.deadlydiamond98.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.entities.DungeonDoorEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DungeonDoor extends BlockWithEntity {
    public static final BooleanProperty OPEN;
    public static final DirectionProperty FACING;
    public static final IntProperty PART_X;
    public static final IntProperty PART_Y;
    protected static final VoxelShape NORTH_SOUTH_SHAPE;
    protected static final VoxelShape EAST_WEST_SHAPE;

    static {
        OPEN = Properties.OPEN;
        FACING = Properties.FACING;
        PART_X = IntProperty.of("part_x", 0, 1);
        PART_Y = IntProperty.of("part_y", 0, 2);
        NORTH_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
        EAST_WEST_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    }


    protected DungeonDoor(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(OPEN, false).with(FACING, Direction.NORTH).with(PART_X, 0).with(PART_Y, 0));;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();

        if (!canPlaceAt(ctx, pos, direction)) {
            return null;
        }

        BlockPos bottomRight = pos.offset(direction.rotateYCounterclockwise());
        BlockPos middleLeft = pos.up();
        BlockPos middleRight = bottomRight.up();
        BlockPos topLeft = pos.up(2);
        BlockPos topRight = bottomRight.up(2);


        placePart(world, bottomRight, direction, 1, 0); // Bottom Right
        placePart(world, middleLeft, direction, 0, 1); // Middle Left
        placePart(world, middleRight, direction, 1, 1); // Middle Right
        placePart(world, topLeft, direction, 0, 2); // Top Left
        placePart(world, topRight, direction, 1, 2); // Top Right

        return this.getDefaultState().with(FACING, direction).with(OPEN, false).with(PART_X, 0).with(PART_Y, 0);
    }

    private boolean canPlaceAt(ItemPlacementContext context, BlockPos pos, Direction direction) {
        World world = context.getWorld();
        return world.getBlockState(pos).canReplace(context)
                && world.getBlockState(pos.offset(direction.rotateYCounterclockwise())).canReplace(context)
                && world.getBlockState(pos.up()).canReplace(context)
                && world.getBlockState(pos.offset(direction.rotateYCounterclockwise()).up()).canReplace(context)
                && world.getBlockState(pos.up(2)).canReplace(context)
                && world.getBlockState(pos.offset(direction.rotateYCounterclockwise()).up(2)).canReplace(context);
    }

    private void placePart(World world, BlockPos pos, Direction direction, int partX, int partY) {
        world.setBlockState(pos, this.getDefaultState().with(FACING, direction).with(PART_X, partX).with(PART_Y, partY));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient) {
            Direction direction = state.get(FACING);
            int partX = state.get(PART_X);
            int partY = state.get(PART_Y);


            if (partX == 0) {
                BlockPos basePos = pos.offset(direction.getOpposite(), partX).down(partY);
                destroyPart(world, basePos); // Bottom Left
                destroyPart(world, basePos.offset(direction.rotateYCounterclockwise())); // Bottom Right
                destroyPart(world, basePos.up()); // Middle Left
                destroyPart(world, basePos.offset(direction.rotateYCounterclockwise()).up()); // Middle Right
                destroyPart(world, basePos.up(2)); // Top Left
                destroyPart(world, basePos.offset(direction.rotateYCounterclockwise()).up(2)); // Top Right
            }
            else {
                BlockPos basePos = pos.down(partY);
                destroyPart(world, basePos); // Bottom Left
                destroyPart(world, basePos.offset(direction.rotateYClockwise())); // Bottom Right
                destroyPart(world, basePos.up()); // Middle Left
                destroyPart(world, basePos.offset(direction.rotateYClockwise()).up()); // Middle Right
                destroyPart(world, basePos.up(2)); // Top Left
                destroyPart(world, basePos.offset(direction.rotateYClockwise()).up(2)); // Top Right
            }
        }
    }

    private void destroyPart(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(this) && !(state.get(PART_X) == 0 && state.get(PART_Y) == 0)) {
            world.breakBlock(pos, false);
        }
        else {
            world.breakBlock(pos, true);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART_X, PART_Y, OPEN);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean isOpen = state.get(OPEN);
        boolean newOpenState = !isOpen;

        Direction direction = state.get(FACING);
        int partX = state.get(PART_X);
        int partY = state.get(PART_Y);

        if (partX == 0) {
            BlockPos basePos = pos.offset(direction.getOpposite(), partX).down(partY);
            updatePart(world, basePos, direction, newOpenState); // Bottom Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()), direction, newOpenState); // Bottom Right
            updatePart(world, basePos.up(), direction, newOpenState); // Middle Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(), direction, newOpenState); // Middle Right
            updatePart(world, basePos.up(2), direction, newOpenState); // Top Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(2), direction, newOpenState); // Top Right
        }
        else {
            BlockPos basePos = pos.down(partY);
            updatePart(world, basePos, direction, newOpenState); // Bottom Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()), direction, newOpenState); // Bottom Right
            updatePart(world, basePos.up(), direction, newOpenState); // Middle Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()).up(), direction, newOpenState); // Middle Right
            updatePart(world, basePos.up(2), direction, newOpenState); // Top Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()).up(2), direction, newOpenState); // Top Right
        }

        return ActionResult.SUCCESS;
    }

    private void updatePart(World world, BlockPos pos, Direction direction, boolean open) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(this)) {
            world.setBlockState(pos, state.with(OPEN, open).with(FACING, direction), 3);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH)
                ? NORTH_SOUTH_SHAPE : EAST_WEST_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? VoxelShapes.empty() : getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(OPEN);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        int partX = state.get(PART_X);
        int partY = state.get(PART_Y);

        if (partX == 0 && partY == 0) {
            return new DungeonDoorEntity(pos, state);
        }

        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.DUNGEON_DOOR, DungeonDoorEntity::tick);
    }
}
