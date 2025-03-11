package net.deadlydiamond98.util.interfaces.mixin.player;

import net.minecraft.util.math.GlobalPos;
import org.jetbrains.annotations.Nullable;

public interface StarCompassData {
    boolean shouldSearchStar();
    void setSearchStar(boolean searchStar);

    @Nullable
    GlobalPos getLastStarPos();
    void setLastStarPos(@Nullable GlobalPos pos);
}