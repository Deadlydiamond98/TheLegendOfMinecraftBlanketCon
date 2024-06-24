package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicUpgrade extends Item {
    public MagicUpgrade(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ManaPlayerData userM = (ManaPlayerData) user;
            if (userM.getMaxMana() <= 500) {
                userM.setMaxMana(userM.getMaxMana() + 100);
                ManaHandler.addManaToPlayer(user, userM.getMaxMana());
                world.playSound(null, user.getBlockPos(), ZeldaSounds.ManaUpgrade, SoundCategory.PLAYERS, 1.0f, 0.5f);
                user.getStackInHand(hand).decrement(1);
                user.getItemCooldownManager().set(this, 10);
                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (ManaHandler.CanRemoveManaFromPlayer(user, userM.getMaxMana())) {
                ManaHandler.addManaToPlayer(user, userM.getMaxMana());
                world.playSound(null, user.getBlockPos(), ZeldaSounds.ManaUpgrade, SoundCategory.PLAYERS, 1.0f, 0.5f);
                user.getStackInHand(hand).decrement(1);
                user.getItemCooldownManager().set(this, 10);
                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }
}
