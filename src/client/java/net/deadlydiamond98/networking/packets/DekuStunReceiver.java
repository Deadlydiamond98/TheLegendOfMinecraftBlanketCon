package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.networking.packets.client.AdvancementStatusPacket;
import net.deadlydiamond98.networking.packets.client.DekuStunOverlayPacket;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;

public class DekuStunReceiver {
    public static void receive(DekuStunOverlayPacket payload, ClientPlayNetworking.Context context) {

        MinecraftClient client = context.client();

        int entityId = payload.entityId();
        boolean hasEffect = payload.hasEffect();
        Entity entity = client.world.getEntityById(entityId);

        if (entity instanceof LivingEntity livingEntity) {
            client.execute(() -> {
                if (hasEffect) {
                    if (!livingEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(ZeldaStatusEffects.Stun_Status_Effect, 200));
                    }
                } else {
                    if (livingEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                        livingEntity.removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
                    }
                }
            });
        }
    }
}
