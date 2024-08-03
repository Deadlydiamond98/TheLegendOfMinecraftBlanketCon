package net.deadlydiamond98.items.custom.manaItems.restoring;

import net.deadlydiamond98.magiclib.items.consumables.MagicFood;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MagicJar extends MagicFood {

    public MagicJar(Settings settings, int amountToGive) {
        super(settings, amountToGive);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.addBlockBreakParticles(user.getBlockPos(), Blocks.LIME_STAINED_GLASS.getDefaultState());
        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
