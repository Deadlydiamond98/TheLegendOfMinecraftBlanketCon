package net.deadlydiamond98.items.manaitems;

import net.deadlydiamond98.magiclib.items.consumables.MagicUpgrade;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;

public class MagicContainer extends MagicUpgrade {
    public MagicContainer(Settings settings, int useUntilManaIs, int addedMana, boolean consumed, boolean replenishMana, int cooldown) {
        super(settings, useUntilManaIs, addedMana, consumed, replenishMana, cooldown);
    }

    @Override
    public void addMana(PlayerEntity user, Hand hand) {
        super.addMana(user, hand);
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.ManaUpgrade, SoundCategory.PLAYERS, 1.0f, 0.5f);
    }
}
