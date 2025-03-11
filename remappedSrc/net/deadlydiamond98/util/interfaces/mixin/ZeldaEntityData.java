package net.deadlydiamond98.util.interfaces.mixin;

import net.minecraft.util.math.Vec3d;

public interface ZeldaEntityData {
    Vec3d getLastGroundPos();
    void setLastGroundPos(Vec3d lastPos);
}
