package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    // This Mixin disables keyboard inputs when stunned


    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo callbackInfo) {
        if ((MinecraftClient.getInstance().player) != null && !MinecraftClient.getInstance().isPaused() && (MinecraftClient.getInstance().player).isAlive()) {
            if ((MinecraftClient.getInstance().player).hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                int chatKey = MinecraftClient.getInstance().options.chatKey.getDefaultKey().getCode();
                int screenshotKey = MinecraftClient.getInstance().options.screenshotKey.getDefaultKey().getCode();
                if (key != chatKey && key != screenshotKey && key != GLFW.GLFW_KEY_ESCAPE) {
                    KeyBinding.unpressAll();
                    InputUtil.Key inputKey = InputUtil.fromKeyCode(key, scancode);
                    KeyBinding.setKeyPressed(inputKey, false);
                    callbackInfo.cancel();
                }
            }
        }
    }
}