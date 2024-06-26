package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MagicCandy extends Item {
    public MagicCandy(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ManaPlayerData userM = (ManaPlayerData) user;
        if (!world.isClient()) {
            if (ManaHandler.CanAddManaToPlayer(user, userM.getMaxMana()) || user.isCreative()) {
                return super.use(world, user, hand);
            }
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            ManaPlayerData userM = (ManaPlayerData) user;
            ManaHandler.addManaToPlayer(player, userM.getMaxMana());
        }
        world.playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed, SoundCategory.PLAYERS, 1.0f, 0.5f);
        return super.finishUsing(stack, world, user);
    }
}
