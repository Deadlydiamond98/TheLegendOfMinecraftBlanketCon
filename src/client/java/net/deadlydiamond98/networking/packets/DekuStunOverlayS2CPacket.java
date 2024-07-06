package net.deadlydiamond98.networking.packets;

import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;

public class DekuStunOverlayS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender responseSender) {
        int entityId = buf.readInt();
        boolean hasEffect = buf.readBoolean();
        StunStatusEffect.OverlayType overlayType = buf.readEnumConstant(StunStatusEffect.OverlayType.class);
        Entity entity = client.world.getEntityById(entityId);

        if (entity instanceof LivingEntity livingEntity) {
            client.execute(() -> {
                if (hasEffect) {
                    StunStatusEffect effect = (StunStatusEffect) ZeldaStatusEffects.Stun_Status_Effect;
                    effect.giveOverlay(overlayType);
                    if (!livingEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(effect, 200));
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
