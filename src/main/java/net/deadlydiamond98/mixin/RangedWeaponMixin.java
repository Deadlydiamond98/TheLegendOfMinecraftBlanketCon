package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponMixin {
    @Inject(method = "getHeldProjectile", at = @At("HEAD"), cancellable = true)
    private static void getArrowFromQuiver(LivingEntity entity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            OtherPlayerData accessor = (OtherPlayerData) player;


            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
            if (trinket.isEquipped(ZeldaItems.Quiver)) {
                for (int i = 0; i < trinket.getEquipped(ZeldaItems.Quiver).size(); i++) {
                    ItemStack stack = trinket.getEquipped(ZeldaItems.Quiver).get(i).getRight();
                    handleQuiver(stack, accessor, cir);
                    if (cir.getReturnValue() != null) {
                        return;
                    }
                }
            }
            if (trinket.isEquipped(ZeldaItems.Better_Quiver)) {
                for (int i = 0; i < trinket.getEquipped(ZeldaItems.Better_Quiver).size(); i++) {
                    ItemStack stack = trinket.getEquipped(ZeldaItems.Better_Quiver).get(i).getRight();
                    handleQuiver(stack, accessor, cir);
                    if (cir.getReturnValue() != null) {
                        return;
                    }
                }
            }

            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() instanceof Quiver) {
                    handleQuiver(stack, accessor, cir);
                    if (cir.getReturnValue() != null) {
                        return;
                    }
                }
            }
        }
    }

    @Unique
    private static void handleQuiver(ItemStack stack, OtherPlayerData accessor, CallbackInfoReturnable<ItemStack> cir) {
        Quiver customBundle = (Quiver) stack.getItem();
        Optional<ItemStack> arrowStack = customBundle.getFirstItem(stack);
        if (arrowStack.isPresent()) {
            if (accessor.hasArrowBeenRemoved()) {
                ItemStack arrowToRemove = arrowStack.get();
                cir.setReturnValue(arrowToRemove);
                customBundle.removeOneItem(stack, arrowToRemove.getItem());
                accessor.setArrowRemoved(false);
            } else {
                ItemStack arrowToRemove = arrowStack.get();
                cir.setReturnValue(arrowToRemove);
                accessor.setArrowRemoved(true);
            }
        }
    }
}