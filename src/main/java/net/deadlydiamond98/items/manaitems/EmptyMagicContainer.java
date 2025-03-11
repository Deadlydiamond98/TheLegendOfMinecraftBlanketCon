package net.deadlydiamond98.items.manaitems;

import net.deadlydiamond98.magiclib.items.consumables.MagicDowngrade;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;

public class EmptyMagicContainer extends MagicDowngrade {

    public EmptyMagicContainer(Settings settings, int useUntilManaIs, int removedMana, boolean consumed, int cooldown) {
        super(settings, useUntilManaIs, removedMana, consumed, cooldown);
    }

    @Override
    public void removeMana(PlayerEntity user, Hand hand) {
        super.removeMana(user, hand);
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds. NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 0.5f);
    }
}
