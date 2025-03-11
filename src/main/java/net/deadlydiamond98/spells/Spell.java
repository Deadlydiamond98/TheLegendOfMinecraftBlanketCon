package net.deadlydiamond98.spells;

import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public abstract class Spell {

    public abstract void doSpellAction(PlayerEntity user, World world);

    public void notEnoughManaAction(PlayerEntity user, World world) {
        world.playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
    }

    public abstract int cost();
}
