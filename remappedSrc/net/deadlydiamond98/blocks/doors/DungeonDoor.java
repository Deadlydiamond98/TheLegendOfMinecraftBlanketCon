package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.entities.doors.DungeonDoorEntity;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.block.ILockable;
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
import net.minecraft.state.property.EnumProperty;
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

public class DungeonDoor extends BlockWithEntity implements ILockable {

    // Collision Shapes
    protected static final VoxelShape NORTH_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape EAST_WEST_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    // Properties
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final DirectionProperty FACING = Properties.FACING;

    public static final EnumProperty<LockType> LOCKED = EnumProperty.of("locked", LockType.class);
    public static final EnumProperty<DungeonDoorParts> DOOR_PARTS = EnumProperty.of("dungeon_door_parts", DungeonDoorParts.class);

    private final DoorColor color;

    public DungeonDoor(Settings settings, DoorColor color) {
        super(settings);
        this.color = color;
        setDefaultState(this.stateManager.getDefaultState().with(OPEN, false).with(FACING, Direction.NORTH)
                .with(DOOR_PARTS, DungeonDoorParts.BL).with(LOCKED, LockType.UNLOCKED));
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


        placePart(world, bottomRight, direction, DungeonDoorParts.BR); // Bottom Right
        placePart(world, middleLeft, direction, DungeonDoorParts.CL); // Middle Left
        placePart(world, middleRight, direction, DungeonDoorParts.CR); // Middle Right
        placePart(world, topLeft, direction, DungeonDoorParts.TL); // Top Left
        placePart(world, topRight, direction, DungeonDoorParts.TR); // Top Right

        return this.getDefaultState().with(FACING, direction);
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

    protected void placePart(World world, BlockPos pos, Direction direction, DungeonDoorParts part) {
        world.setBlockState(pos, this.getDefaultState().with(FACING, direction).with(DOOR_PARTS, part));
    }

    private boolean canLock(BlockState state) {
        return state.get(LOCKED).isUnlocked() && !state.get(OPEN);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean currentOpenState = state.get(OPEN);
        boolean newOpenState = !currentOpenState;

        Direction direction = state.get(FACING);
        DungeonDoorParts part = state.get(DOOR_PARTS);
        LockType lockState = state.get(LOCKED);

        ItemStack itemStack = player.getStackInHand(hand);

        if (isLockItem(itemStack) && canLock(state)) {
            updateDoor(part, world, pos, direction, currentOpenState, fromLock(itemStack));
            world.playSound(null, pos, SoundEvents.BLOCK_HANGING_SIGN_PLACE, SoundCategory.BLOCKS);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        if (tryUnlocking(itemStack, state, part, pos, direction, currentOpenState, world, player)) {
            return ActionResult.SUCCESS;
        }

        if (lockState.isUnlocked()) {
            openCloseDoor(part, world, pos, direction, newOpenState, currentOpenState);
            return ActionResult.SUCCESS;
        }
        else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    protected void openCloseDoor(DungeonDoorParts part, World world, BlockPos pos, Direction direction, boolean newOpenState, boolean currentOpenState) {
        if (!currentOpenState) {
            world.playSound(null, pos, ZeldaSounds.DungeonDoorOpen, SoundCategory.BLOCKS);
        }
        else {
            world.playSound(null, pos, ZeldaSounds.DungeonDoorClose, SoundCategory.BLOCKS);
        }
        updateDoor(part, world, pos, direction, newOpenState, LockType.UNLOCKED);
    }


    private boolean tryUnlocking(ItemStack itemStack, BlockState state, DungeonDoorParts part, BlockPos pos,
                                 Direction direction, boolean currentOpenState, World world, PlayerEntity player) {
        if (isKeyItem(itemStack, state.get(LOCKED))) {
            updateDoor(part, world, pos, direction, currentOpenState, LockType.UNLOCKED, true, state.get(LOCKED).getBlock());
            world.playSound(null, pos, SoundEvents.BLOCK_HANGING_SIGN_BREAK, SoundCategory.BLOCKS);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return true;
        }

        return false;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient) {
            DungeonDoorParts part = state.get(DOOR_PARTS);
            updateTest(true, part, world, pos, state.get(FACING), (w, p, a) -> destroyPart(w, (PlayerEntity) a[0], p), player);
        }
    }

    private void destroyPart(World world, PlayerEntity player, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(this) && !(state.get(DOOR_PARTS).isBasePos())) {
            world.breakBlock(pos, false);
        } else {
            world.breakBlock(pos, !player.isCreative());
        }
    }

    private void updateDoor(DungeonDoorParts part, World world, BlockPos pos, Direction direction, boolean open, LockType type) {
        updateDoor(part, world, pos, direction, open, type, false, ZeldaBlocks.Dungeon_Door);
    }

    private void updateDoor(DungeonDoorParts part, World world, BlockPos pos, Direction direction, boolean open, LockType type, boolean spawnParticles, Block block) {
        updateTest(true, part, world, pos, direction,
                (w, p, a) -> updatePart(w, p, (Direction) a[0], (boolean) a[1], (LockType) a[2], (boolean) a[3], (Block) a[4]),
                direction, open, type, spawnParticles, block
        );
    }

    private void updatePart(World world, BlockPos pos, Direction direction, boolean open, LockType type, boolean spawnParticles, Block block) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(this)) {
            world.setBlockState(pos, state.with(OPEN, open).with(FACING, direction).with(LOCKED, type), 3);
            if (spawnParticles) {
                world.addBlockBreakParticles(pos, block.getDefaultState());
            }
        }
    }

    public void updateTest(boolean placeFirst, DungeonDoorParts part, World world, BlockPos pos, Direction direction, UpdateDoor action,
                           Object... args) {
        Direction offset = part.isLeft() ? direction.rotateYCounterclockwise() : direction.rotateYClockwise();
        BlockPos basePos = pos.down(part.getY());

        if (placeFirst) {
            action.update(world, basePos, args); // Bottom Left
        }
        action.update(world, basePos.offset(offset), args); // Bottom Right
        action.update(world, basePos.up(), args); // Middle Left
        action.update(world, basePos.offset(offset).up(), args); // Middle Right
        action.update(world, basePos.up(2), args); // Top Left
        action.update(world, basePos.offset(offset).up(2), args); // Top Right
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) ? NORTH_SOUTH_SHAPE : EAST_WEST_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? VoxelShapes.empty() : getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(OPEN);
    }

    public DoorColor getColor() {
        return this.color;
    }

    @Nullable
    @Override
    public final BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(DOOR_PARTS).isBasePos()) {
            return getBlockEntity(pos, state);
        }
        return null;
    }

    protected BlockEntity getBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonDoorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ZeldaBlockEntities.DUNGEON_DOOR, DungeonDoorEntity::tickDungeonDoor);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, DOOR_PARTS, OPEN, LOCKED);
    }

    @FunctionalInterface
    interface UpdateDoor {
        void update(World world, BlockPos pos, Object... args);
    }
}
