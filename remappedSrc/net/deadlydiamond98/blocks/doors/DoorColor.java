package net.deadlydiamond98.blocks.doors;

public enum DoorColor {
    DEFAULT("wood"),
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    public final String id;

    DoorColor(String id) {
        this.id = id;
    }
}