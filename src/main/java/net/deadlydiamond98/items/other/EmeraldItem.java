package net.deadlydiamond98.items.other;

import net.deadlydiamond98.util.interfaces.item.IPickupSound;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;

public class EmeraldItem extends Item implements IPickupSound {
    public EmeraldItem(Settings settings) {
        super(settings);
    }

    @Override
    public SoundEvent getSound() {
        return ZeldaSounds.EmeraldShardPickedUp;
    }
}
