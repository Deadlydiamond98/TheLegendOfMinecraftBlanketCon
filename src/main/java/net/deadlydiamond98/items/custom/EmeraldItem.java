package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.items.PickupSound;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;

public class EmeraldItem extends Item implements PickupSound {
    public EmeraldItem(Settings settings) {
        super(settings);
    }

    @Override
    public SoundEvent getSound() {
        return ZeldaSounds.EmeraldShardPickedUp;
    }
}
