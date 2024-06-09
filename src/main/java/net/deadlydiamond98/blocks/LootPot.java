package net.deadlydiamond98.blocks;

import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LootPot extends BlockWithEntity {
    public LootPot(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            LootPotBlockEntity blockEntity = (LootPotBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemStack stack = player.getStackInHand(hand);
                if (!stack.isEmpty()) {
                    ItemStack existingStack = blockEntity.getStack(0);
                    if (existingStack.isEmpty()) {
                        blockEntity.setStack(0, stack.split(1));
                    } else if (ItemStack.areItemsEqual(existingStack, stack)) {
                        existingStack.increment(1);
                        stack.decrement(1);
                    }
                    blockEntity.markDirty();
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        LootPotBlockEntity blockEntity = (LootPotBlockEntity) world.getBlockEntity(pos);
        if (blockEntity instanceof LootPotBlockEntity) {
            blockEntity.checkLootInteraction(player);
            DefaultedList<ItemStack> items = blockEntity.getItems();
            ItemScatterer.spawn(world, pos, items);
        }
        super.onBreak(world, pos, state, player);
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
