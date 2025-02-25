package net.deadlydiamond98.entities.bombs.bombchu;

import net.deadlydiamond98.util.interfaces.entities.IRaycast;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public interface ISurfaceSticker extends IRaycast {

    void setVelocity(double x, double y, double z);

    enum FloorAttachState {
        FLOOR("Floor", Direction.DOWN),
        CEILING("Ceiling", Direction.UP),
        NORTH("North", Direction.NORTH),
        SOUTH("South", Direction.SOUTH),
        EAST("East", Direction.EAST),
        WEST("West", Direction.WEST),
        DETACHED("Detached", Direction.DOWN),
        THROWN("Thrown", Direction.DOWN);

        private final String name;
        private final Direction direction;

        FloorAttachState(String name, @Nullable Direction direction) {
            this.name = name;
            this.direction = direction;
        }

        public boolean canApplyGravity() {
            return this == DETACHED || this == THROWN;
        }

        public boolean ceiling() {
            return this == CEILING;
        }

        public FloorAttachState getByName(String name) {
            for (FloorAttachState state : values()) {
                if (state.name.equals(name)) {
                    return state;
                }
            }
            return null;
        }

        public String getName() {
            return this.name;
        }

        public Direction getDirection() {
            return this.direction;
        }
    }

    default FloorAttachState changeSide(BlockHitResult frontHit) {
        Direction direction = frontHit.getSide().getOpposite();
        return switch (direction) {
            case DOWN -> FloorAttachState.FLOOR;
            case UP -> FloorAttachState.CEILING;
            case NORTH -> FloorAttachState.NORTH;
            case SOUTH -> FloorAttachState.SOUTH;
            case EAST -> FloorAttachState.EAST;
            case WEST -> FloorAttachState.WEST;
        };
    }

    default Vec3d updateVelocityDirection(FloorAttachState floor, float yaw, float pitch, double speed) {
        double radYaw = Math.toRadians(yaw);
        double radPitch = Math.toRadians(pitch);

        double sinYaw = Math.sin(radYaw);
        double cosYaw = Math.cos(radYaw);
        double sinPitch = Math.sin(radPitch);
        double cosPitch = Math.cos(radPitch);

        Vec3d velocity;

        switch (floor) {
            case CEILING -> velocity = new Vec3d(
                    -sinYaw * cosPitch,
                    sinPitch,
                    cosYaw * cosPitch
            );
            case NORTH, SOUTH -> velocity = new Vec3d(
                    -sinYaw,
                    -sinPitch,
                    0
            );
            case EAST, WEST -> velocity = new Vec3d(
                    0,
                    -sinPitch,
                    cosYaw
            );
            default -> velocity = new Vec3d(
                    -sinYaw * cosPitch,
                    -sinPitch,
                    cosYaw * cosPitch
            );
        }
        return velocity.multiply(speed);
    }
}
