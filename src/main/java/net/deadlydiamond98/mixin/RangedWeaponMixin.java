package net.deadlydiamond98.mixin;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.items.custombundle.CustomBundle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponMixin {
    private static boolean arrowRemoved = false;
    private static int countToShootArrowCuzOfDumbassMinecraftShootingArrowTwicePleaseFuckingWork = 0;
    @Inject(method = "getHeldProjectile", at = @At("HEAD"), cancellable = true)
    private static void getArrowFromCustomBundle(LivingEntity entity, Predicate<ItemStack> predicate, CallbackInfoReturnable<ItemStack> cir) {
        if (entity instanceof PlayerEntity && !arrowRemoved) {
            PlayerEntity player = (PlayerEntity) entity;
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() instanceof CustomBundle) {
                    CustomBundle customBundle = (CustomBundle) stack.getItem();
                    Optional<ItemStack> arrowStack = customBundle.removeOneItem(stack, Items.ARROW);
                    if (arrowStack.isPresent()) {
                        cir.setReturnValue(arrowStack.get());
                        ZeldaCraft.LOGGER.info("Arrow found and removed from quiver: " + arrowStack.get());
                        arrowRemoved = true;
                        return;
                    }
                }
            }
        }
    }

    @Inject(method = "getHeldProjectile", at = @At("TAIL"))
    private static void resetArrowFlag(CallbackInfoReturnable<ItemStack> cir) {
        if (arrowRemoved && countToShootArrowCuzOfDumbassMinecraftShootingArrowTwicePleaseFuckingWork >= 2) {
            arrowRemoved = false;
            countToShootArrowCuzOfDumbassMinecraftShootingArrowTwicePleaseFuckingWork = 0;
        }
        else {
            countToShootArrowCuzOfDumbassMinecraftShootingArrowTwicePleaseFuckingWork++;
        }
    }
}