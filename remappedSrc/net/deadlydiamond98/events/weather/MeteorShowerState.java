package net.deadlydiamond98.events.weather;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class MeteorShowerState extends PersistentState {
    private boolean isMeteorShowering;

    public MeteorShowerState() {
        this.isMeteorShowering = false;
    }

    public boolean getIsMeteorShowering() {
        return isMeteorShowering;
    }

    public void setIsMeteorShowering(boolean value) {
        this.isMeteorShowering = value;
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("isMeteorShowering", this.isMeteorShowering);
        return nbt;
    }

    public static MeteorShowerState fromNbt(NbtCompound nbt) {
        MeteorShowerState data = new MeteorShowerState();
        data.isMeteorShowering = nbt.getBoolean("isMeteorShowering");
        return data;
    }
}
