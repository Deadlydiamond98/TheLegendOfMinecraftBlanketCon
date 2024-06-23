package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.CoralParentBlock.WATERLOGGED;

public class MagicPowder extends Item {
    public MagicPowder(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (ManaHandler.CanRemoveManaFromPlayer(context.getPlayer(), 2)) {
            if (state.isOf(Blocks.GRASS)) {
                world.setBlockState(pos, ZeldaBlocks.Loot_Grass.getDefaultState());
            }
            else if (state.isOf(Blocks.NETHERRACK)) {
                world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            }
            else if (state.isOf(Blocks.SOUL_SAND)) {
                world.setBlockState(pos, Blocks.SAND.getDefaultState());
            }
            else if (state.isOf(Blocks.SOUL_SOIL)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }

            world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);
            ManaHandler.removeManaFromPlayer(context.getPlayer(), 2);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }
}
