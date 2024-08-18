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

    boolean canSpawnStar();
    void setTriedStarSpawn(boolean starSpawn);

    boolean doubleJumpEnabled();
    void enableddoubleJump(boolean doubleJump);
    boolean hasntDoubleJumpped();
    void canDoubleJump(boolean doubleJumpped);
    boolean wasOnGround();
    void setOnGround(boolean wasOnGround);

    boolean canUseHook();
    void setHookUsability(boolean hookusable);
}
