package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.util.interfaces.items.IPickupEffect;
import net.deadlydiamond98.items.items.bomb.regular_bombs.AbstractBombItem;
import net.deadlydiamond98.items.items.bomb.BombchuItem;
import net.deadlydiamond98.items.items.custombundle.BombBag;
import net.deadlydiamond98.items.items.custombundle.CustomBundle;
import net.deadlydiamond98.items.items.custombundle.Quiver;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "insertStack", at = @At("HEAD"), cancellable = true)
    public void addStack(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = ((PlayerInventory) (Object) this).player;
        Item item = stack.getItem();
        if (item instanceof ArrowItem) {
            if (addItemToBagTrinket(player, stack, Quiver.class, cir)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
            else if (addItemToBag(player, stack, Quiver.class, cir)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
        else if (item instanceof AbstractBombItem || item instanceof BombchuItem) {
            if (addItemToBag(player, stack, BombBag.class, cir)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }

        if (item instanceof IPickupEffect effectItem && player.getHealth() < player.getMaxHealth()) {
            effectItem.getEffects().forEach(statusEffect ->
                    player.addStatusEffect(new StatusEffectInstance(statusEffect, 200)));
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Unique
    private <T extends CustomBundle> boolean addItemToBagTrinket(PlayerEntity player, ItemStack itemStack,
                                                                 Class<T> itemClass, CallbackInfo cir) {

        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();

        for (Pair<SlotReference, ItemStack> entry : trinket.getAllEquipped()) {
            ItemStack itemstack = entry.getRight();
            if (itemstack.isIn(ZeldaTags.Items.Quivers)) {
                for (int i = 0; i < trinket.getEquipped(itemstack.getItem()).size(); i++) {
                    ItemStack quiver = trinket.getEquipped(itemstack.getItem()).get(i).getRight();
                    if (itemClass.isInstance(quiver.getItem())) {
                        T customBundle = (T) quiver.getItem();
                        if (customBundle.getItemBarStep(customBundle.getDefaultStack()) < 13) {
                            int added = customBundle.addToBundle(quiver, itemStack);
                            if (added > 0) {
                                itemStack.decrement(added);
                                if (itemStack.isEmpty()) {
                                    player.increaseStat(Stats.PICKED_UP.getOrCreateStat(itemStack.getItem()), added);
                                    player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F,
                                            0.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
                                    if (quiver.getItem() instanceof Quiver quiverItem) {
                                        quiverItem.updateFilledStatus(quiver);
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Unique
    private <T extends CustomBundle> boolean addItemToBag(PlayerEntity player, ItemStack itemStack,
                                                          Class<T> itemClass, CallbackInfo cir) {

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (itemClass.isInstance(stack.getItem())) {
                T customBundle = (T) stack.getItem();
                if (customBundle.getItemBarStep(customBundle.getDefaultStack()) < 13) {
                    int added = customBundle.addToBundle(stack, itemStack);
                    if (added > 0) {
                        itemStack.decrement(added);
                        if (itemStack.isEmpty()) {
                            player.increaseStat(Stats.PICKED_UP.getOrCreateStat(itemStack.getItem()), added);
                            player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F,
                                    0.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
