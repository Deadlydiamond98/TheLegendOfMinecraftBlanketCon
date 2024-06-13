package net.deadlydiamond98.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class RaycastUtil {
    public static HitResult getCollisionFromEntityFront(Entity entity, double range) {
        Vec3d startPos = entity.getPos();
        Vec3d direction = getDirection(entity);
        Vec3d endPos = startPos.add(direction.multiply(range));

        RaycastContext context = new RaycastContext(startPos, endPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity);
        World world = entity.getWorld();
        return world.raycast(context);
    }

    public static HitResult getCollisionDownwardFromEntity(Entity entity, double range) {
        Vec3d startPos = entity.getPos();
        Vec3d direction = getDownwardDirection(entity);
        Vec3d endPos = startPos.add(direction.multiply(range));

        RaycastContext context = new RaycastContext(startPos, endPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity);
        World world = entity.getWorld();
        return world.raycast(context);
    }

    private static Vec3d getDirection(Entity entity) {
        float pitch = entity.getPitch();
        float yaw = entity.getYaw();

        float pitchRad = (float) Math.toRadians(pitch);
        float yawRad = (float) Math.toRadians(yaw);

        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);

        return new Vec3d(x, y, z);
    }

    private static Vec3d getDownwardDirection(Entity entity) {
        float pitch = entity.getPitch() + 90;
        float yaw = entity.getYaw();

        float pitchRad = (float) Math.toRadians(pitch);
        float yawRad = (float) Math.toRadians(yaw);

        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);

        return new Vec3d(x, y, z).normalize();
    }
}