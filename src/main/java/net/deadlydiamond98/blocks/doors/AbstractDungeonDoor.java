package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDungeonDoor extends BlockWithEntity {
    public static final BooleanProperty OPEN;
    public static final BooleanProperty AUTO;
    public static final DirectionProperty FACING;
    public static final BooleanProperty LOCKED;
    public static final IntProperty PART_X;
    public static final IntProperty PART_Y;
    protected static final VoxelShape NORTH_SOUTH_SHAPE;
    protected static final VoxelShape EAST_WEST_SHAPE;

    static {
        OPEN = Properties.OPEN;
        AUTO = Properties.TRIGGERED;
        LOCKED = Properties.LOCKED;
        FACING = Properties.FACING;
        PART_X = IntProperty.of("part_x", 0, 1);
        PART_Y = IntProperty.of("part_y", 0, 2);
        NORTH_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
        EAST_WEST_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    }


    protected AbstractDungeonDoor(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(OPEN, false).with(FACING, Direction.NORTH)
                .with(PART_X, 0).with(PART_Y, 0).with(LOCKED, false).with(AUTO, isAuto()));
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

        return this.getDefaultState().with(FACING, direction).with(OPEN, false)
                .with(PART_X, 0).with(PART_Y, 0).with(LOCKED, false).with(AUTO, isAuto());
    }

    protected Boolean isAuto() {
        return false;
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

    protected void placePart(World world, BlockPos pos, Direction direction, int partX, int partY) {
        world.setBlockState(pos, this.getDefaultState().with(FACING, direction).with(PART_X, partX).with(PART_Y, partY).with(LOCKED, false).with(AUTO, isAuto()));
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
        builder.add(FACING, PART_X, PART_Y, OPEN, LOCKED, AUTO);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean currentOpenState = state.get(OPEN);
        boolean newOpenState = !currentOpenState;

        Direction direction = state.get(FACING);
        int partX = state.get(PART_X);
        int partY = state.get(PART_Y);

        ItemStack itemStack = player.getStackInHand(hand);
        if (isLockItem(itemStack) && canLock(state)) {
            updateDoor(partX, partY, world, pos, direction, currentOpenState, true, false);
            world.playSound(null, pos, SoundEvents.BLOCK_HANGING_SIGN_PLACE, SoundCategory.BLOCKS);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        if (tryUnlocking(itemStack, state, partX, partY, pos, direction, currentOpenState, world, player)) {
            return ActionResult.SUCCESS;
        }

        if (!state.get(LOCKED)) {
            if (!currentOpenState) {
                world.playSound(null, pos, ZeldaSounds.DungeonDoorOpen, SoundCategory.BLOCKS);
            }
            else {
                world.playSound(null, pos, ZeldaSounds.DungeonDoorClose, SoundCategory.BLOCKS);
            }
            updateDoor(partX, partY, world, pos, direction, newOpenState, false, false);
            return ActionResult.SUCCESS;
        }
        else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    private boolean tryUnlocking(ItemStack itemStack, BlockState state, int partX, int partY, BlockPos pos,
                              Direction direction, boolean currentOpenState, World world, PlayerEntity player) {
        if ((isKeyItem(itemStack)) && state.get(LOCKED)) {
            updateDoor(partX, partY, world, pos, direction, currentOpenState, false, true);
            world.playSound(null, pos, SoundEvents.BLOCK_HANGING_SIGN_BREAK, SoundCategory.BLOCKS);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return true;
        }

        if ((isMagicKeyItem(itemStack)) && state.get(LOCKED)) {
            if (player.canRemoveMana(25)) {
                updateDoor(partX, partY, world, pos, direction, currentOpenState, false, true);
                world.playSound(null, pos, SoundEvents.BLOCK_HANGING_SIGN_BREAK, SoundCategory.BLOCKS);
                player.removeMana(25);
                return true;
            }
            world.playSound(null, pos, ZeldaSounds.NotEnoughMana, SoundCategory.BLOCKS);
        }

        return false;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);
        if (f == -1.0F || state.get(LOCKED)) {
            return 0.0F;
        }
        int i = player.canHarvest(state) ? 30 : 100;
        return player.getBlockBreakingSpeed(state) / f / (float)i;
    }

    private void updateDoor(int partX, int partY, World world, BlockPos pos, Direction direction, boolean open, boolean locked, boolean spawnParticles) {

        if (partX == 0) {
            BlockPos basePos = pos.offset(direction.getOpposite(), partX).down(partY);
            updatePart(world, basePos, direction, open, locked, spawnParticles); // Bottom Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()), direction, open, locked, spawnParticles); // Bottom Right
            updatePart(world, basePos.up(), direction, open, locked,spawnParticles); // Middle Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(), direction, open, locked, spawnParticles); // Middle Right
            updatePart(world, basePos.up(2), direction, open, locked, spawnParticles); // Top Left
            updatePart(world, basePos.offset(direction.rotateYCounterclockwise()).up(2), direction, open, locked, spawnParticles); // Top Right
        }
        else {
            BlockPos basePos = pos.down(partY);
            updatePart(world, basePos, direction, open ,locked, spawnParticles); // Bottom Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()), direction, open, locked, spawnParticles); // Bottom Right
            updatePart(world, basePos.up(), direction, open, locked, spawnParticles); // Middle Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()).up(), direction, open, locked, spawnParticles); // Middle Right
            updatePart(world, basePos.up(2), direction, open, locked, spawnParticles); // Top Left
            updatePart(world, basePos.offset(direction.rotateYClockwise()).up(2), direction, open, locked, spawnParticles); // Top Right
        }
    }

    private boolean canLock(BlockState state) {
        return !state.get(OPEN) && !state.get(LOCKED);
    }

    private static boolean isKeyItem(ItemStack stack) {
        return stack.isOf(ZeldaItems.Dungeon_Key);
    }

    private static boolean isMagicKeyItem(ItemStack stack) {
        return stack.isOf(ZeldaItems.Master_Key);
    }

    private static boolean isLockItem(ItemStack stack) {
        return stack.isOf(ZeldaItems.Dungeon_Lock);
    }

    private void updatePart(World world, BlockPos pos, Direction direction, boolean open, boolean locked, boolean spawnParticles) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(this)) {
            world.setBlockState(pos, state.with(OPEN, open).with(FACING, direction).with(LOCKED, locked).with(AUTO, isAuto()), 3);
            if (spawnParticles) {
                world.addBlockBreakParticles(pos, Blocks.GOLD_BLOCK.getDefaultState());
            }
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
            return createDungeonDoor(pos, state);
        }

        return null;
    }

    protected abstract BlockEntity createDungeonDoor(BlockPos pos, BlockState state);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, getDungeonDoorEntity(), doorTickMethod());
    }

    protected abstract BlockEntityTicker<? super AbstractDungeonDoorEntity> doorTickMethod();

    protected abstract BlockEntityType<? extends AbstractDungeonDoorEntity> getDungeonDoorEntity();


}
