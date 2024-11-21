package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.ZeldacraftMusic;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
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

    @Unique
    private MinecraftClient getClient() {
        return (MinecraftClient)(Object)this;
    }

    @Inject(method = "getMusicType",
            at = @At("RETURN"),
            cancellable = true)
    private void getMusicForEventsZelda(CallbackInfoReturnable<MusicSound> cir) {
        MusicSound musicSound = Nullables.map(getClient().currentScreen, Screen::getMusic);
        if (musicSound == null && getClient().player != null) {
            assert getClient().player != null;
            if (getClient().player.getWorld() != null) {
                if (validMeteor(getClient())) {
                    cir.setReturnValue(ZeldacraftMusic.starMusic);
                }
            }
        }
    }

    @Unique
    private static boolean validMeteor(MinecraftClient client) {
        return client.player.getWorld().getRegistryKey() == World.OVERWORLD && ZeldaSeverTickEvent.meteorShower.isMeteorShowerActive();
    }
}
