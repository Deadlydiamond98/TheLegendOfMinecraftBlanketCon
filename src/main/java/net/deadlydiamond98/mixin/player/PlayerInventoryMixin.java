package net.deadlydiamond98.mixin.player;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.util.CustomBundleUtil;
import net.deadlydiamond98.items.bundle.CustomBundle;
import net.deadlydiamond98.util.interfaces.item.IPickupEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "insertStack(ILnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    public void addStack(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = ((PlayerInventory) (Object) this).player;

        if (hasCustomZeldaBundle(player, stack)) {
            cancelPickup(cir);
        }

        if (stack.getItem() instanceof IPickupEffect effectItem) {
            mimicPickupZelda(player, stack, stack.getCount(), () -> effectItem.getEffects().forEach(statusEffect ->
                    player.addStatusEffect(new StatusEffectInstance(statusEffect, 200))));
            cancelPickup(cir);
        }
    }

    @Unique
    private boolean hasCustomZeldaBundle(PlayerEntity player, ItemStack insertStack) {

        boolean hasTrinketBag = hasCustomZeldaBundleTrinket(player, insertStack);

//        if (!hasTrinketBag) {
//            for (int i = 0; i < player.getInventory().size(); i++) {
//
//                ItemStack bundleStack = player.getInventory().getStack(i);
//
//                if (bundleStack.getItem() instanceof CustomBundle bundle) {
//
//                    boolean canInsert = CustomBundleUtil.canInsertItem(bundleStack, insertStack.getItem());
//                    boolean isFull = CustomBundleUtil.isFull(bundleStack);
//
//                    if (canInsert && !isFull) {
//                        int added = CustomBundleUtil.addToBundle(bundleStack, insertStack);
//
//                        mimicPickupZelda(player, insertStack, added, () -> bundle.playInsertSound(player));
//                        return true;
//                    }
//                }
//            }
//        }
        return hasTrinketBag;
    }

    @Unique
    private boolean hasCustomZeldaBundleTrinket(PlayerEntity player, ItemStack insertStack) {
//        Optional<TrinketComponent> bl = TrinketsApi.getTrinketComponent(player);
//
//        if (bl.isPresent()) {
//            TrinketComponent trinket = bl.get();
//
//            for (Pair<SlotReference, ItemStack> entry : trinket.getAllEquipped()) {
//                ItemStack bundleStack = entry.getRight();
//
//                if (bundleStack.getItem() instanceof CustomBundle bundle) {
//
//                    boolean canInsert = CustomBundleUtil.canInsertItem(bundleStack, insertStack.getItem());
//                    boolean isFull = CustomBundleUtil.isFull(bundleStack);
//
//                    if (canInsert && !isFull) {
//                        int added = CustomBundleUtil.addToBundle(bundleStack, insertStack);
//
//                        mimicPickupZelda(player, insertStack, added, () -> bundle.playInsertSound(player));
//                        return true;
//                    }
//                }
//            }
//        }
        return false;
    }

    @Unique
    private boolean mimicPickupZelda(PlayerEntity player, ItemStack itemStack, int stackCount, Runnable method) {
        if (stackCount > 0) {

            itemStack.decrement(stackCount);

            if (itemStack.isEmpty()) {

                player.increaseStat(Stats.PICKED_UP.getOrCreateStat(itemStack.getItem()), stackCount);
                method.run();
                return true;

            }
        }
        return false;
    }

    @Unique
    private void cancelPickup(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
        cir.cancel();
    }
}
