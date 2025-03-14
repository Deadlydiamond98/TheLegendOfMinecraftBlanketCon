package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class StunStatusEffect extends StatusEffect {

    public enum OverlayType {
        DEKU,
        ICE,
        CLOCK
    }

    private OverlayType overlay;

    protected StunStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2d3a9c);
        this.overlay = OverlayType.DEKU;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        notifyPlayers(entity, true, overlay);
        entity.setVelocity(0, 0, 0);
        return super.applyUpdateEffect(entity, amplifier);
    }

    private void notifyPlayers(LivingEntity entity, boolean apply, OverlayType hasOverlay) {
        for (PlayerEntity player : entity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                double distance = entity.squaredDistanceTo(serverPlayer);
                if (distance < 10000) {
                    ZeldaServerPackets.sendDekuStunOverlayPacket(serverPlayer, entity.getId(), apply);
                }
            }
        }
    }

    public void giveOverlay(OverlayType overlay) {
        this.overlay = overlay;
    }

    public OverlayType getOverlay() {
        return this.overlay;
    }
}
