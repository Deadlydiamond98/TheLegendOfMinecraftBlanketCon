package net.deadlydiamond98.events;

import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class EntityDamagedEvent {
    public static void register() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof PlayerEntity attackedEntity) {
                if (attackedEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
                    attackedEntity.removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
                }
            }
            return ActionResult.PASS;
        });

    }
}
