package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo callbackInfo) {
        if ((MinecraftClient.getInstance().player) != null && !MinecraftClient.getInstance().isPaused()) {
            if ((MinecraftClient.getInstance().player).hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                KeyBinding.unpressAll();
                callbackInfo.cancel();
            }
        }
    }
}