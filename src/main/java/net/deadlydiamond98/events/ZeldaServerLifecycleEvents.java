package net.deadlydiamond98.events;

import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.world.ServerWorld;

public class ZeldaServerLifecycleEvents {

    public static void register() {
        onServerStarted();
    }

    private static void onServerStarted() {
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            for (ServerWorld world : server.getWorlds()) {
                ZeldaWorldDataManager.getMeteorShowerState(world);
            }
        });
    }
}
