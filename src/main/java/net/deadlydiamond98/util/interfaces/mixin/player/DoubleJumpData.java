package net.deadlydiamond98.util.interfaces.mixin.player;

public interface DoubleJumpData {
    boolean doubleJumpEnabled();
    void enableddDoubleJump(boolean doubleJump);
    boolean hasntDoubleJumpped();
    void canDoubleJump(boolean doubleJumpped);
    boolean wasOnGround();
    void setPrevGroundState(boolean wasOnGround);
}
