package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.networking.packets.client.DekuStunOverlayPacket;
import net.deadlydiamond98.networking.packets.client.EntityStatsPacket;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaLivingEntityData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;

public class EntityStatsReceiver {

    public static void receive(EntityStatsPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        int entityId = payload.entityId();
        boolean flip = payload.flip();
        Entity entity = client.world.getEntityById(entityId);


        if (entity instanceof LivingEntity livingEntity) {
            client.execute(() -> {
                ((ZeldaLivingEntityData) livingEntity).setflipped(flip);
            });
        }
    }
}
