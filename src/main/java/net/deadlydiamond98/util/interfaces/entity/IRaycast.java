package net.deadlydiamond98.util.interfaces.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public interface IRaycast {

    World getRaycastWorld();
    Vec3d getRaycastPos();
    float getRaycastHeight();

    default HitResult doRaycast(Vec3d start, float yaw, float pitch, double length) {
        Vec3d direction = getDirectionVector(yaw, pitch);
        Vec3d end = start.add(direction.multiply(length));


        return getRaycastWorld().raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                (Entity) this
        ));
    }

    default HitResult doRaycast(Vec3d start, Vec3d velocity, double length) {
        if (velocity.lengthSquared() < 1.0e-8) {
            return getRaycastWorld().raycast(new RaycastContext(
                    start,
                    start,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    (Entity) this
            ));
        }


        Vec3d direction = velocity.normalize();
        Vec3d end = start.add(direction.multiply(length));

        return getRaycastWorld().raycast(new RaycastContext(
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
        return getRaycastPos().add(0, getRaycastHeight() / 2.0, 0);
    }

    private static Vec3d up() {
        return new Vec3d(0, 1, 0);
    }
}
