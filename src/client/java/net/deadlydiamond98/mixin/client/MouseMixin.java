package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    // disables mouse input when stunned
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        if ((MinecraftClient.getInstance().player) != null && !MinecraftClient.getInstance().isPaused() && (MinecraftClient.getInstance().player).isAlive()) {
            if ((MinecraftClient.getInstance().player).hasStatusEffect(ZeldaStatusEffects.Sword_Sick_Status_Effect)
                    && isSword((MinecraftClient.getInstance().player).getMainHandStack().getItem())) {
                KeyBinding.setKeyPressed(InputUtil.Type.MOUSE.createFromCode(0), false);
                callbackInfo.cancel();
            }
            else if ((MinecraftClient.getInstance().player).hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                KeyBinding.unpressAll();
                callbackInfo.cancel();
            }
        }
    }

    // checks if an item qualifies as a sword, for when the sword sick debuf is applied

    @Unique
    private boolean isSword(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem;
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if ((MinecraftClient.getInstance().player) != null && !MinecraftClient.getInstance().isPaused() && (MinecraftClient.getInstance().player).isAlive()) {
            if ((MinecraftClient.getInstance().player).hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                KeyBinding.unpressAll();
                callbackInfo.cancel();
            }
        }
    }

    // disables camera movement

    @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
    private void onCursorPos(long window, double xpos, double ypos, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().player != null && !MinecraftClient.getInstance().isPaused() && MinecraftClient.getInstance().player.isAlive()) {
            if (MinecraftClient.getInstance().player.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                callbackInfo.cancel();
            }
        }
    }
}