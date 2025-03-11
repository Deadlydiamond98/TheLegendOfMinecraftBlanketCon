package net.deadlydiamond98.events;

import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ZeldaUseEntityCallbackEvent {

    public static void registerUseEntityEvent() {
        UseEntityCallback.EVENT.register(ZeldaUseEntityCallbackEvent::onEntityInteract);
    }

    private static ActionResult onEntityInteract(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {

        if (player.getStackInHand(hand).getItem() == Items.GLASS_BOTTLE && entity instanceof FairyEntity) {
            player.getStackInHand(hand).decrement(1);

            ItemStack stack = new ItemStack(ZeldaItems.Fairy_Bottle);
            NbtCompound nbt = new NbtCompound();
            nbt.putString("fairycolor", ((FairyEntity) entity).getColor());
            stack.setNbt(nbt);
            player.giveItemStack(stack);
            world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS);

            entity.discard();
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
