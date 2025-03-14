package net.deadlydiamond98.mixin.item;

import net.deadlydiamond98.util.CustomBundleUtil;
import net.deadlydiamond98.util.QuiverUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(CrossbowItem.class)
public class CrossBowItemMixin {

//    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
//    private void swapQuiver(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//        if (QuiverUtil.useQuiver(user)) {
//            cir.setReturnValue(TypedActionResult.success(user.getStackInHand(hand)));
//            cir.cancel();
//        }
//    }
//
//    @Inject(
//            method = "onStoppedUsing",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/item/CrossbowItem$LoadingSounds;end()Ljava/util/Optional;",
//                    shift = At.Shift.AFTER
//            ),
//            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
//            cancellable = true)
//    private void onReleaseUsing(ItemStack stack, World world, LivingEntity entity, int remainingUseTicks, CallbackInfo ci) {
//        if (entity instanceof PlayerEntity user) {
//            ItemStack quiverStack = QuiverUtil.findQuiver(user);
//            if (quiverStack != null) {
//
//                Optional<ItemStack> arrowStack = CustomBundleUtil.getFirstItem(quiverStack);
//
//                if (arrowStack.isPresent()) {
//
//                    if (!user.getAbilities().creativeMode) {
//                        CustomBundleUtil.removeOneItem(quiverStack, arrowStack.get().getItem());
//                    }
//                    ci.cancel();
//                }
//            }
//        }
//    }
}
