package net.deadlydiamond98.mixin;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.items.custombundle.Quiver;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(CrossbowItem.class)
public class CrossBowItemMixin {

    @Inject(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true)
    private void onReleaseUsing(ItemStack stack, World world, LivingEntity entity, int remainingUseTicks, CallbackInfo ci) {
        if (entity instanceof PlayerEntity user) {
            ItemStack quiverStack = findQuiver(user);
            if (quiverStack != null) {
                Quiver quiver = (Quiver) quiverStack.getItem();
                Optional<ItemStack> arrowStack = quiver.getFirstItem(quiverStack);
                if (arrowStack.isPresent()) {
                    if (!user.getAbilities().creativeMode) {
                        quiver.removeOneItem(quiverStack, arrowStack.get().getItem());
                    }
                    ci.cancel();
                }
            }
        }
    }

    @Unique
    private ItemStack findQuiver(PlayerEntity player) {

        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();

        for (Pair<SlotReference, ItemStack> entry : trinket.getAllEquipped()) {
            ItemStack itemstack = entry.getRight();
            if (itemstack.isIn(ZeldaTags.Items.Quivers)) {
                for (int i = 0; i < trinket.getEquipped(itemstack.getItem()).size(); i++) {
                    ItemStack quiver = trinket.getEquipped(itemstack.getItem()).get(i).getRight();
                    return quiver;
                }
            }
        }

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isIn(ZeldaTags.Items.Quivers)) {
                return stack;
            }
        }
        return null;
    }
}
