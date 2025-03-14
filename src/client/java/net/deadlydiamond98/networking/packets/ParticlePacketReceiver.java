package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.networking.packets.client.ParticlePacket;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleEffect;

public class ParticlePacketReceiver {

    public static void receive(ParticlePacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        int particle = payload.particleType();
        double x = payload.x();
        double y = payload.y();
        double z = payload.z();

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
