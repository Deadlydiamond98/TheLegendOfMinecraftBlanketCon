package net.deadlydiamond98.events;

import net.deadlydiamond98.util.ManaHandler;
import net.deadlydiamond98.util.ManaPlayerData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class AfterRespawnEvent {

    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            ManaPlayerData oldMana = (ManaPlayerData) oldPlayer;
            ManaPlayerData newMana = (ManaPlayerData) newPlayer;
            newMana.setMaxMana(oldMana.getMaxMana());
        });
    }

}
