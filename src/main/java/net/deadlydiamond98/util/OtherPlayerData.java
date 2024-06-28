package net.deadlydiamond98.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public interface OtherPlayerData {
    boolean hasArrowBeenRemoved();
    void setArrowRemoved(boolean removed);
    boolean isFairy();
    void setFairyState(boolean fairyControl);
    void removeFairyEffect(PlayerEntity user);
    boolean getFairyFriend();
    void setFairyFriend(boolean fairyfriend);
}
