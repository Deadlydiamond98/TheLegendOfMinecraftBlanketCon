package net.deadlydiamond98.blocks.loot;

import com.mojang.serialization.MapCodec;
import net.deadlydiamond98.blocks.entities.loot.LootPotBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LootPot extends BlockWithEntity {
    public static final MapCodec<LootPot> CODEC = createCodec(LootPot::new);

    public LootPot(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            LootPotBlockEntity blockEntity = (LootPotBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemStack stack = player.getMainHandStack();
                if (blockEntity.hasLootTable()) {
                    ItemStack loot = blockEntity.getStack(0);
                    if (player.isSneaking() && stack.isEmpty()) {
                        player.giveItemStack(loot);
                        blockEntity.removeStack(0);
                    }
                }
                else {
                    ItemStack existingStack = blockEntity.getStack(0);
                    if (!stack.isEmpty()) {
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
                    else if (player.isSneaking() && !existingStack.isEmpty() && stack.isEmpty()) {
                        player.giveItemStack(existingStack);
                        blockEntity.removeStack(0);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        LootPotBlockEntity blockEntity = (LootPotBlockEntity) world.getBlockEntity(pos);
        if (blockEntity instanceof LootPotBlockEntity) {
            DefaultedList<ItemStack> items = blockEntity.getItems();
            ItemScatterer.spawn(world, pos, items);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LootPotBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 8.0, 11.0);
    }
}
