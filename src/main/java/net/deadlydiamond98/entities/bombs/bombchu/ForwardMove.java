package net.deadlydiamond98.entities.bombs.bombchu;

public enum ForwardMove {
    LEFT(-1),
    NORMAL(0),
    RIGHT(1);

    private final int direction;

    ForwardMove(int direction) {
        this.direction = direction;
    }

    public float getRotation(float frontDist) {
        if (frontDist == 0) {
            frontDist = 1;
        }

        return (6 * (1 / frontDist)) * this.direction;
    }
}