package net.deadlydiamond98.util;

import net.minecraft.nbt.NbtCompound;

public interface OtherPlayerData {
    boolean hasArrowBeenRemoved();
    void setArrowRemoved(boolean removed);
    NbtCompound getPersistentData();
}
