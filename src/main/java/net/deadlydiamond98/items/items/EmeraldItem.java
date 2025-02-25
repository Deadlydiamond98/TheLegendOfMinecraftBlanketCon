package net.deadlydiamond98.items.items;

import net.deadlydiamond98.util.interfaces.items.IPickupSound;
import net.deadlydiamond98.sounds.ZeldaSounds;
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
