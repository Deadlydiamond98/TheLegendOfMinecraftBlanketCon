package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.networking.packets.client.ZeldaSoundPacket;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ZeldaSoundReceiver {

    public static void receive(ZeldaSoundPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        int sound = payload.soundType();

        SoundEvent soundevent;
        switch (sound) {
            default -> soundevent = ZeldaSounds.ShootingStarFalling;
            case 2 -> soundevent = ZeldaSounds.AdvancementRegular;
            case 3 -> soundevent = ZeldaSounds.AdvancementGoal;
        }

        client.execute(() -> {
            client.player.playSound(soundevent, 1.0f, 1.0f);
        });
    }
}
