package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.bomb.regular_bombs.BombItem;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.deadlydiamond98.items.custom.custombundle.BombBag;
import net.deadlydiamond98.items.custom.custombundle.CustomBundle;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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
        else if (item instanceof BombItem || item instanceof BombchuItem) {
            if (addItemToBag(player, stack, BombBag.class, cir)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Unique
    private <T extends CustomBundle> boolean addItemToBagTrinket(PlayerEntity player, ItemStack itemStack,
                                                                 Class<T> itemClass, CallbackInfo cir) {
        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
        if (trinket.isEquipped(ZeldaItems.Quiver)) {
            for (int i = 0; i < trinket.getEquipped(ZeldaItems.Quiver).size(); i++) {
                ItemStack stack = trinket.getEquipped(ZeldaItems.Quiver).get(i).getRight();
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
        }
        if (trinket.isEquipped(ZeldaItems.Better_Quiver)) {
            for (int i = 0; i < trinket.getEquipped(ZeldaItems.Better_Quiver).size(); i++) {
                ItemStack stack = trinket.getEquipped(ZeldaItems.Better_Quiver).get(i).getRight();
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
