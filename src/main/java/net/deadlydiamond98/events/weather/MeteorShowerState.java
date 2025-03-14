package net.deadlydiamond98.events.weather;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.RaidManager;
import net.minecraft.world.PersistentState;

public class MeteorShowerState extends PersistentState {
    private boolean isMeteorShowering;

    public static PersistentState.Type<MeteorShowerState> getPersistentStateType() {
        return new PersistentState.Type(
                MeteorShowerState::new,
                (nbt, registryLookup) -> fromNbt((NbtCompound) nbt),
                DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES);
    }

    public MeteorShowerState() {
        this.isMeteorShowering = false;
    }

    public boolean getIsMeteorShowering() {
        return this.isMeteorShowering;
    }

    public void setIsMeteorShowering(boolean value) {
        this.isMeteorShowering = value;
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean("isMeteorShowering", this.isMeteorShowering);
        return nbt;
    }

    public static MeteorShowerState fromNbt(NbtCompound nbt) {
        MeteorShowerState data = new MeteorShowerState();
        data.isMeteorShowering = nbt.getBoolean("isMeteorShowering");
        return data;
    }
}
