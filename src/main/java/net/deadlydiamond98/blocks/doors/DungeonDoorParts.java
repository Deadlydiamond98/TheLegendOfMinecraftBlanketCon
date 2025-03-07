package net.deadlydiamond98.blocks.doors;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public enum DungeonDoorParts implements StringIdentifiable {
    TL(0, 2, "top_left"),
    TR(1, 2, "top_right"),
    
    CL(0, 1, "middle_left"),
    CR(1, 1, "middle_right"),
    
    BL(0, 0, "bottom_left"),
    BR(1, 0, "bottom_right");

    private final String name;
    
    private final int x;
    private final int y;
    
    DungeonDoorParts(int x, int y, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public boolean isBasePos() {
        return this == BL;
    }

    public boolean isLeft() {
        return this.getX() == 0;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
