package net.deadlydiamond98.world.zeldadungeons.base;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DungeonEntrance {
    public enum EntranceType {
        OPENING,
        WOOD_DOOR,
        LOCKED_DOOR,
        CRACKED_DOOR,
        CRACKED_WALL,
        BOSS_DOOR;
    }

    private EntranceType type;
    private BlockPos pos;
    private Direction direction;

    public DungeonEntrance(EntranceType type, BlockPos pos, Direction direction) {
        this.type = type;
        this.pos = pos;
        this.direction = direction;
    }

    public EntranceType getType() {
        return this.type;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isOpening() {
        return this.type == EntranceType.OPENING;
    }

    public boolean doorsCanKiss() {
        return this.type != EntranceType.OPENING && this.type != EntranceType.CRACKED_DOOR;
    }
}
