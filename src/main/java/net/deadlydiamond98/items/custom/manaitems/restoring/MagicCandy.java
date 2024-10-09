package net.deadlydiamond98.items.custom.manaitems.restoring;

import net.deadlydiamond98.magiclib.items.consumables.MagicFood;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class MagicCandy extends MagicFood {
    public MagicCandy(Settings settings, int amountToGive) {
        super(settings, amountToGive);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addMana(user.getMaxMana());
        world.playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed,
                SoundCategory.PLAYERS, 1.0f, 0.5f);
        return super.finishUsing(stack, world, user);
    }
}
