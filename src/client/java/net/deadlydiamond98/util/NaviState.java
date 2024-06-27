package net.deadlydiamond98.util;

import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.Vec3d;

public class NaviState {
    public static final float SPEED = 0.2f;
    public static final float FOLLOW_DISTANCE = 5.0f;

    public Vec3d naviPosition;
    public boolean startPosInit;
    public Vec3d naviPrevPosition;
    public Vec3d naviVelocity;
    public float naviYaw;
    public float currentAngle;
    public float naviPrevYaw;
    public int naviIdle;
    public final FairyCompanionRenderer<AbstractClientPlayerEntity> naviRenderer;
    public int tempScale;

    public NaviState(Vec3d initialPosition, EntityRendererFactory.Context context) {
        this.naviPosition = initialPosition;
        this.startPosInit = false;
        this.naviPrevPosition = this.naviPosition;
        this.naviVelocity = Vec3d.ZERO;
        this.naviYaw = 0.0f;
        this.currentAngle = 0.0f;
        this.naviPrevYaw = 0.0f;
        this.naviIdle = 0;
        this.tempScale = 1;
        this.naviRenderer = new FairyCompanionRenderer<>(context);
    }

    public void setVelocityTowards(Vec3d targetPosition, double speed) {
        Vec3d direction = targetPosition.subtract(this.getPosition()).normalize();
        this.setVelocity(direction.multiply(speed));
    }

    public void setVelocity(Vec3d velocity) {
        this.naviVelocity = velocity;
    }

    public Vec3d getVelocity() {
        return this.naviVelocity;
    }

    public Vec3d getPosition() {
        return this.naviPosition;
    }

    public float getYaw() {
        return this.naviYaw;
    }
}