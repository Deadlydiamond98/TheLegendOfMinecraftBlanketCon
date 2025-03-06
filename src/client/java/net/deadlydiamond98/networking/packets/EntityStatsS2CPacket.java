package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.util.interfaces.mixin.ZeldaLivingEntityData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;

public class EntityStatsS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int entityId = buf.readInt();
        boolean flip = buf.readBoolean();
        Entity entity = client.world.getEntityById(entityId);


        if (entity instanceof LivingEntity livingEntity) {
            client.execute(() -> {
                ((ZeldaLivingEntityData) livingEntity).setflipped(flip);
            });
        }
    }
}
