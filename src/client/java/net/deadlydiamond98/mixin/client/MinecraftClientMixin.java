package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.ZeldacraftMusic;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Nullables;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public Screen currentScreen;

    @Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
    private void getMusicForEventsZelda(CallbackInfoReturnable<MusicSound> cir) {
        if (!(this.currentScreen instanceof CreditsScreen) && this.player != null) {
            if (validMeteor()) {
                cir.setReturnValue(ZeldacraftMusic.starMusic);
            }
        }
    }

    @Unique
    private boolean validMeteor() {
        return this.player.getWorld().getRegistryKey() == World.OVERWORLD && ZeldaSeverTickEvent.meteorShower.isMeteorShowerActive();
    }
}
