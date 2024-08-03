package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.magiclib.items.consumables.MagicDowngrade;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
