package net.deadlydiamond98.util.interfaces.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.GlobalPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ZeldaPlayerData {
    boolean isFairy();
    void setFairyState(boolean fairyControl);
    void removeFairyEffect(PlayerEntity user);
    boolean getFairyFriend();
    void setFairyFriend(boolean fairyfriend);

    boolean shouldSearchStar();
    void setSearchStar(boolean searchStar);
    boolean canSpawnStar();
    void setTriedStarSpawn(boolean starSpawn);

    @Nullable GlobalPos getLastStarPos();
    void setLastStarPos(@Nullable GlobalPos pos);

    boolean doubleJumpEnabled();
    void enableddoubleJump(boolean doubleJump);
    boolean hasntDoubleJumpped();
    void canDoubleJump(boolean doubleJumpped);
    boolean wasOnGround();
    void setPrevGroundState(boolean wasOnGround);

    boolean canUseHook();
    void setHookUsability(boolean hookusable);
}
