package net.deadlydiamond98.items.custom.manaitems.restoring;

import net.deadlydiamond98.items.custom.PickupSound;
import net.deadlydiamond98.magiclib.items.consumables.MagicConsumable;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class StarFragment extends MagicConsumable implements PickupSound {
    public StarFragment(Settings settings, int amountToGive, boolean consumed, int cooldown) {
        super(settings, amountToGive, consumed, cooldown);
    }

    @Override
    protected void afterUse(PlayerEntity user) {
        super.afterUse(user);
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public SoundEvent getSound() {
        return ZeldaSounds.StarPickedUp;
    }
}
