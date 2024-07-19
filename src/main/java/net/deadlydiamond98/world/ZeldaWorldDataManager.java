package net.deadlydiamond98.world;

import net.deadlydiamond98.events.weather.MeteorShowerState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;

public class ZeldaWorldDataManager {
    private static final String DATA_NAME = "metor_shower_data";

    public static MeteorShowerState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(MeteorShowerState::fromNbt, MeteorShowerState::new, DATA_NAME);
    }

    public static void setMeteorShower(ServerWorld world, boolean value) {
        MeteorShowerState data = get(world);
        data.setIsMeteorShowering(value);
    }

    public static boolean getMeteorShower(ServerWorld world) {
        return get(world).getIsMeteorShowering();
    }
}
