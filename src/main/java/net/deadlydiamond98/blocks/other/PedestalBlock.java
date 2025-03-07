package net.deadlydiamond98.blocks.other;

import net.deadlydiamond98.blocks.entities.PedestalBlockEntity;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BlockWithEntity {

    public static final DirectionProperty FACING;

    static {
        FACING = Properties.FACING;
    }

    public PedestalBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();

        return super.getPlacementState(ctx).with(FACING, direction);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);
            ItemStack stack = player.getStackInHand(hand);
            if (blockEntity != null) {
                if (blockEntity.hasLootTable()) {
                    blockEntity.checkLootInteraction(player);
                    ItemStack loot = blockEntity.getStack(0);
                    if (player.isSneaking() && stack.isEmpty()) {
                        player.giveItemStack(loot);
                        blockEntity.removeStack(0);
                    }
                }
                else {
                    ItemStack existingStack = blockEntity.getStack(0);
                    if (!stack.isEmpty() && stack.getItem() instanceof SwordItem) {
                        if (existingStack.isEmpty()) {
                            blockEntity.setStack(0, stack.split(stack.getCount()));
                        } else if (ItemStack.areItemsEqual(existingStack, stack) && existingStack.getCount() < existingStack.getMaxCount()) {
                            int spaceAvailable = existingStack.getMaxCount() - existingStack.getCount();
                            int amount = Math.min(stack.getCount(), spaceAvailable);

                            existingStack.increment(amount);
                            stack.decrement(amount);
                        }
                        blockEntity.markDirty();
                    }
                    else if (!existingStack.isEmpty()) {
                        player.giveItemStack(existingStack);
                        blockEntity.removeStack(0);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);
        if (blockEntity instanceof PedestalBlockEntity) {
            blockEntity.checkLootInteraction(player);
            DefaultedList<ItemStack> items = blockEntity.getItems();
            ItemScatterer.spawn(world, pos, items);
        }
        super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.PEDESTAL, PedestalBlockEntity::tick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }
}
