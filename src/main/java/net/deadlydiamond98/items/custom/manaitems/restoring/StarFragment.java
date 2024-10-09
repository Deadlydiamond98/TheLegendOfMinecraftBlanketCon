package net.deadlydiamond98.items.custom.manaitems.restoring;

import net.deadlydiamond98.magiclib.items.consumables.MagicConsumable;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;

public class StarFragment extends MagicConsumable {
    public StarFragment(Settings settings, int amountToGive, boolean consumed, int cooldown) {
        super(settings, amountToGive, consumed, cooldown);
    }

    @Override
    protected void afterUse(PlayerEntity user) {
        super.afterUse(user);
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}
