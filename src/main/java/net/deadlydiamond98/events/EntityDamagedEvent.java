package net.deadlydiamond98.events;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.ManaHandler;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class EntityDamagedEvent {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player && ManaHandler.CanRemoveManaFromPlayer(player, 5)) {
                TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
                if (trinket.isEquipped(ZeldaItems.Red_Ring)) {
                    ManaHandler.removeManaFromPlayer(player, 5);
                }
                else if (trinket.isEquipped(ZeldaItems.Blue_Ring)) {
                    ManaHandler.removeManaFromPlayer(player, 5);
                }
            }
            return true;
        });

    }
}
