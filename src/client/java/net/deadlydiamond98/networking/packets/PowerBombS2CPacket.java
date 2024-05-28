package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.entities.BombEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;

public class PowerBombS2CPacket {

    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int entityId = buf.readInt();
        float newBombPower = buf.readFloat();

        client.execute(() -> {
            if (client.world != null) {
                Entity entity = client.world.getEntityById(entityId);
                if (entity instanceof BombEntity) {
                    ((BombEntity) entity).setPower(newBombPower);
                }
            }
        });
    }
}
