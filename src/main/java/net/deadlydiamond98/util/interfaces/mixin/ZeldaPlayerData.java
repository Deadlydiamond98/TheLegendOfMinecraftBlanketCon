package net.deadlydiamond98.util.interfaces.mixin;

import net.deadlydiamond98.util.interfaces.mixin.player.DoubleJumpData;
import net.deadlydiamond98.util.interfaces.mixin.player.StarCompassData;
import net.minecraft.entity.player.PlayerEntity;

public interface ZeldaPlayerData extends StarCompassData, DoubleJumpData {
    boolean isFairy();
    void setFairyState(boolean fairyControl);
    void removeFairyEffect(PlayerEntity user);
    boolean getFairyFriend();
    void setFairyFriend(boolean fairyfriend);

    boolean canSpawnStar();
    void setTriedStarSpawn(boolean starSpawn);

    boolean canUseHook();
    void setHookUsability(boolean hookusable);

    void updateAdvancementClient(boolean hasAdvancement);
    boolean hasAdvancement(String advancementID);
}
