package net.deadlydiamond98.events.onoffswitch;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;

public class OnOffState extends PersistentState {
    private final Map<String, Boolean> onOffGroups = new HashMap<>();

    public void updateOnOff(String key, boolean value) {
        this.onOffGroups.put(key, value);
    }

    public boolean getOnOff(String key) {
        return this.onOffGroups.getOrDefault(key, true);
    }

    public boolean hasOnOffState(String key) {
        return this.onOffGroups.containsKey(key);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound flagsNbt = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : this.onOffGroups.entrySet()) {
            flagsNbt.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put("flags", flagsNbt);
        return nbt;
    }

    public static OnOffState fromNbt(NbtCompound nbt) {
        OnOffState state = new OnOffState();
        if (nbt.contains("flags", NbtElement.COMPOUND_TYPE)) {
            NbtCompound flagsNbt = nbt.getCompound("flags");
            for (String key : flagsNbt.getKeys()) {
                state.onOffGroups.put(key, flagsNbt.getBoolean(key));
            }
        }
        return state;
    }
}