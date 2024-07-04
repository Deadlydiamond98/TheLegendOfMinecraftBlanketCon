package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class PlayShootingStarSoundS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {

        client.execute(() -> {
            client.player.playSound(ZeldaSounds.ShootingStarFalling, SoundCategory.MASTER, 1.0f, 1.0f);
        });
    }
}
