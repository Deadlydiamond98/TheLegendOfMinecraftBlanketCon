package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public ClientWorld world;

    @Unique
    private static final RegistryEntry<SoundEvent> STAR_MUSIC = Registries.SOUND_EVENT.getEntry(Registries.SOUND_EVENT.getKey(ZeldaSounds.StarMusic).get()).orElseThrow();
    @Unique
    private static final MusicSound starMusic = new MusicSound(STAR_MUSIC, 12000, 24000, true);

    @Unique
    private MinecraftClient getClient() {
        return (MinecraftClient)(Object)this;
    }

    @Inject(method = "getMusicType",
            at = @At("HEAD"),
            cancellable = true)
    private void getMusicForEventsZelda(CallbackInfoReturnable<MusicSound> cir) {
        if (this.player != null && this.world != null) {
            if (validMeteor(getClient())) {
                cir.setReturnValue(starMusic);
            }
        }
    }

    @Unique
    private static boolean validMeteor(MinecraftClient client) {
        RegistryKey<World> world = client.world.getRegistryKey();
        return ZeldaSeverTickEvent.meteorShower.isMeteorShowerActive() && world == World.OVERWORLD;
    }
}
