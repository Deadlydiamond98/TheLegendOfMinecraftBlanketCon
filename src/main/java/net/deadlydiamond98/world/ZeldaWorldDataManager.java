package net.deadlydiamond98.world;

import net.deadlydiamond98.events.onoffswitch.OnOffState;
import net.deadlydiamond98.events.weather.MeteorShowerState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentStateManager;

public class ZeldaWorldDataManager {
    private static final String METOR_SHOWER_DATA = "metor_shower_data";
    private static final String ON_OFF_DATA = "on_off_data";

    public static MeteorShowerState getMeteorShowerState(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(MeteorShowerState.getPersistentStateType(), METOR_SHOWER_DATA);
    }

    public static void setMeteorShower(ServerWorld world, boolean value) {
        MeteorShowerState data = getMeteorShowerState(world);
        data.setIsMeteorShowering(value);
    }

    public static boolean getMeteorShower(ServerWorld world) {
        return getMeteorShowerState(world).getIsMeteorShowering();
    }

    public static OnOffState getOnOffState(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(OnOffState.getPersistentStateType(), ON_OFF_DATA);
    }

    public static void applyOnOff(ServerWorld world, String key, boolean value) {
        OnOffState state = getOnOffState(world);
        state.updateOnOff(key, value);
        state.markDirty();
    }

    public static boolean getOnOff(ServerWorld world, String key) {
        return getOnOffState(world).getOnOff(key);
    }

    public static boolean hasOnOffState(ServerWorld world, String key) {
        return getOnOffState(world).hasOnOffState(key);
    }
}
