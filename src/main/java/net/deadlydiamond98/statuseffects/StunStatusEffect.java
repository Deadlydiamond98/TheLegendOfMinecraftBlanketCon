package net.deadlydiamond98.statuseffects;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
    private static final EntityAttributeModifier Stun_Modifier = new EntityAttributeModifier(
            "stunModifier",
            0.0f,
            EntityAttributeModifier.Operation.MULTIPLY_TOTAL
    );
    private OverlayType overlay;

    protected StunStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2d3a9c);
        this.overlay = OverlayType.DEKU;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        notifyPlayers(entity, true, overlay);
        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(Stun_Modifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        notifyPlayers(entity, false, overlay);
        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(Stun_Modifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        notifyPlayers(entity, true, overlay);
        entity.setVelocity(0, 0, 0);
    }

    private void notifyPlayers(LivingEntity entity, boolean apply, OverlayType hasOverlay) {
        for (PlayerEntity player : entity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                double distance = entity.squaredDistanceTo(serverPlayer);
                if (distance < 10000) {
                    ZeldaServerPackets.sendDekuStunOverlayPacket(serverPlayer, entity.getId(), apply, hasOverlay);
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
