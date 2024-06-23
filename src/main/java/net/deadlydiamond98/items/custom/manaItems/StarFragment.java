package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StarFragment extends Item {
    private int amountToGive;
    public StarFragment(Settings settings, int amountToGive) {
        super(settings);
        this.amountToGive = amountToGive;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (ManaHandler.CanAddManaToPlayer(user, this.amountToGive) || user.isCreative()) {
                ManaHandler.addManaToPlayer(user, this.amountToGive);
                world.playSound(null, user.getBlockPos(), ZeldaSounds.StarUsed, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
