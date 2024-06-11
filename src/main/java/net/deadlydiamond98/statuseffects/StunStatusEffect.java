package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class StunStatusEffect extends StatusEffect {
    protected StunStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2d3a9c);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        for (PlayerEntity player : entity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                double distance = entity.squaredDistanceTo(serverPlayer);
                if (distance < 10000) {
                    ZeldaServerPackets.sendDekuStunOverlayPacket(serverPlayer, entity.getId(), true);
                }
            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        for (PlayerEntity player : entity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                double distance = entity.squaredDistanceTo(serverPlayer);
                if (distance < 10000) {
                    ZeldaServerPackets.sendDekuStunOverlayPacket(serverPlayer, entity.getId(), false);
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (entity instanceof ServerPlayerEntity player) {
            player.networkHandler.requestTeleport(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
        }
        entity.setVelocity(0, 0, 0);
        entity.setPitch(entity.getPitch());
        entity.setYaw(entity.getYaw());
    }
}
