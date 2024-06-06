package net.deadlydiamond98.mixin;

import net.deadlydiamond98.items.custombundle.Quiver;
import net.deadlydiamond98.util.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
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
            PlayerData accessor = (PlayerData) player;

            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() instanceof Quiver) {
                    Quiver customBundle = (Quiver) stack.getItem();
                    Optional<ItemStack> arrowStack = customBundle.getFirstItem(stack);
                    if (arrowStack.isPresent()) {
                        if (accessor.hasArrowBeenRemoved()) {
                            ItemStack arrowToRemove = arrowStack.get();
                            cir.setReturnValue(arrowToRemove);
                            customBundle.removeOneItem(stack, arrowToRemove.getItem());
                            accessor.setArrowRemoved(false);
                            return;
                        }
                        if (arrowStack.isPresent()) {
                            ItemStack arrowToRemove = arrowStack.get();
                            cir.setReturnValue(arrowToRemove);
                            accessor.setArrowRemoved(true);
                            return;
                        }
                    }
                }
            }
        }
    }
}