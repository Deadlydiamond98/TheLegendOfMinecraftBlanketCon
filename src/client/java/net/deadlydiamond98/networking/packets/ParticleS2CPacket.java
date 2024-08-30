package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.particle.ZeldaParticles;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;

public class ParticleS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int particle = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        client.execute(() -> {
            ParticleEffect parameters;

            switch (particle) {
                case 1 -> parameters = ZeldaParticles.Smaaash_Particle;
                case 2 -> parameters = ZeldaParticles.Snap_Particle;
                case 3 -> parameters = ZeldaParticles.Magic_Ice_Particle;
                default -> parameters = ZeldaParticles.Explosion_Particle;
            }

            MinecraftClient.getInstance().world.addImportantParticle(parameters, x, y, z, 0, 0, 0);
        });
    }
}
