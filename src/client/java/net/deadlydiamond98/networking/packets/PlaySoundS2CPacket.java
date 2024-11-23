package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class PlaySoundS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {

        int sound = buf.readVarInt();


        SoundEvent soundevent;
        switch (sound) {
            default -> soundevent = ZeldaSounds.ShootingStarFalling;
            case 2 -> soundevent = ZeldaSounds.AdvancementRegular;
            case 3 -> soundevent = ZeldaSounds.AdvancementGoal;
        }

        client.execute(() -> {
            client.player.playSound(soundevent, SoundCategory.MASTER, 1.0f, 1.0f);
        });
    }
}
