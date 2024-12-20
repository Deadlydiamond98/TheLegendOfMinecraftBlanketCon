package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.custom.custombundle.Quiver;
import net.deadlydiamond98.util.ZeldaPlayerData;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Pair;
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

            TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();

            for (Pair<SlotReference, ItemStack> entry : trinket.getAllEquipped()) {
                ItemStack stack = entry.getRight();
                if (stack.isIn(ZeldaTags.Items.Quivers)) {
                    handleQuiver(stack, cir);
                    if (cir.getReturnValue() != null) {
                        return;
                    }
                }
            }

            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.isIn(ZeldaTags.Items.Quivers)) {
                    handleQuiver(stack, cir);
                    if (cir.getReturnValue() != null) {
                        return;
                    }
                }
            }
        }
    }

    @Unique
    private static void handleQuiver(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        Quiver customBundle = (Quiver) stack.getItem();
        Optional<ItemStack> arrowStack = customBundle.getFirstItem(stack);
        if (arrowStack.isPresent()) {
            ItemStack arrowToRemove = arrowStack.get();
            cir.setReturnValue(arrowToRemove);
        }
    }
}