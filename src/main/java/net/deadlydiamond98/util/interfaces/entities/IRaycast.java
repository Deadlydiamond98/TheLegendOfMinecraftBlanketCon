package net.deadlydiamond98.util.interfaces.entities;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public interface IRaycast {

    World getWorld();
    Vec3d getPos();
    float getHeight();

    default HitResult doRaycast(Vec3d start, float yaw, float pitch, double length) {
        Vec3d direction = getDirectionVector(yaw, pitch);
        Vec3d end = start.add(direction.multiply(length));

        return this.getWorld().raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                (Entity) this
        ));
    }

    default Vec3d getDirectionVector(float yaw, float pitch) {
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);

        return new Vec3d(x, y, z);
    }

    default Vec3d getPerpendicularOffset(float yaw, double halfWidth) {
        double yawRad = Math.toRadians(yaw);

        double x = Math.cos(yawRad) * halfWidth;
        double z = Math.sin(yawRad) * halfWidth;

        return new Vec3d(x, 0, z);
    }

    default Vec3d getCenterPos() {
        return this.getPos().add(0, this.getHeight() / 2.0, 0);
    }
}
