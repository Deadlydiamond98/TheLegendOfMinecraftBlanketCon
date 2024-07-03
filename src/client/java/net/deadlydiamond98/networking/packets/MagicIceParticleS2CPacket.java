package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.particle.ZeldaParticles;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class MagicIceParticleS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        client.execute(() -> {
            MinecraftClient.getInstance().world.addImportantParticle(ZeldaParticles.Magic_Ice_Particle, x, y, z, 0, 0, 0);
        });
    }
}
