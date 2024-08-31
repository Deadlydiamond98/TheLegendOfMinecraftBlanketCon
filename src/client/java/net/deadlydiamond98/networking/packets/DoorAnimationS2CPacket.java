package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.blocks.entities.DungeonDoorEntity;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

public class DoorAnimationS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        int openingTicks = buf.readInt();
        int rotation = buf.readInt();

        client.execute(() -> {
            if (client.world != null) {
                BlockEntity entity = client.world.getBlockEntity(pos);
                if (entity instanceof DungeonDoorEntity door) {
                    door.setOpeningPosition(openingTicks);
                    door.setRotation(rotation);
                }
            }
        });
    }
}
