package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MagicDowngrade extends Item {
    public MagicDowngrade(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ManaPlayerData userM = (ManaPlayerData) user;
            if (userM.getMaxMana() > 100) {
                ManaHandler.removeManaFromPlayer(user, userM.getMaxMana());
                userM.setMaxMana(userM.getMaxMana() - 100);
                world.playSound(null, user.getBlockPos(), ZeldaSounds. NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 0.5f);
                user.getStackInHand(hand).decrement(1);
                user.getItemCooldownManager().set(this, 10);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
            else {
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }
}
