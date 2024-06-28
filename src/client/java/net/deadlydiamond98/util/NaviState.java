package net.deadlydiamond98.util;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

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
    public int fairyType;
    public boolean fireSound;
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
        // 0 = normal, 1 = navi, 2 = tatl
        this.fairyType = 0;
        this.fireSound = true;
        this.naviRenderer = new FairyCompanionRenderer<>(context);
    }

    public void renderNavi(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
                           VertexConsumerProvider vertexConsumers, int light, boolean transform) {

        setupColor(player);

        if (!this.startPosInit) {
            this.naviPosition = player.getPos().add(0, 2, 0);
            this.startPosInit = true;
        }

        if (this.tempScale == 0) {
            this.naviPosition = player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0);
        }

        this.naviPrevPosition = this.naviPosition;
        this.naviPrevYaw = this.naviYaw;

        moveAround(player);

        this.naviPosition = this.getPosition().add(this.getVelocity());

        this.naviYaw = (float) (MathHelper.atan2(this.getVelocity().z, this.getVelocity().x) * (180F / Math.PI)) - 90.0F;

        matrices.push();

        double interpolatedX = MathHelper.lerp(tickDelta, this.naviPrevPosition.x, this.naviPosition.x);
        double interpolatedY = MathHelper.lerp(tickDelta, this.naviPrevPosition.y, this.naviPosition.y);
        double interpolatedZ = MathHelper.lerp(tickDelta, this.naviPrevPosition.z, this.naviPosition.z);
        double playerInterpolatedX = MathHelper.lerp(tickDelta, player.prevX, player.getX());
        double playerInterpolatedY = MathHelper.lerp(tickDelta, player.prevY, player.getY());
        double playerInterpolatedZ = MathHelper.lerp(tickDelta, player.prevZ, player.getZ());

        if (transform) {
            matrices.translate(interpolatedX - playerInterpolatedX, interpolatedY - playerInterpolatedY, interpolatedZ - playerInterpolatedZ);
        }
        matrices.scale(this.tempScale, this.tempScale, this.tempScale);

        this.naviRenderer.renderBody(player, matrices, vertexConsumers);

        if (transform) {
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, this.naviPrevYaw, this.naviYaw) + 180));
        }

        this.naviRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.pop();
    }

    private void setupColor(AbstractClientPlayerEntity player) {
        TrinketComponent trinket = TrinketsApi.getTrinketComponent(player).get();
        if (trinket.isEquipped(ZeldaItems.Fairy_Bell)) {
            ItemStack bell = trinket.getEquipped(ZeldaItems.Fairy_Bell).get(0).getRight();
            if (bell.hasNbt() && bell.getNbt().contains("Color")) {
                NbtCompound nbt = bell.getOrCreateNbt();
                String fairyType = nbt.getString("Color");
                switch (fairyType) {
                    case "navi" -> {
                        this.naviRenderer.setColor("blue");
                        this.setFairyType(1);
                    }
                    case "tatl" -> {
                        this.naviRenderer.setColor("yellow");
                        this.setFairyType(2);
                    }
                    default -> {
                        this.naviRenderer.setColor(fairyType);
                        this.setFairyType(0);
                    }
                }
            }
        }
        else {
            this.naviRenderer.setColor("blue");
            this.setFairyType(0);
        }
    }

    private void moveAround(AbstractClientPlayerEntity player) {
        double distanceToTarget = this.getPosition().distanceTo(player.getPos());
        if (distanceToTarget > FOLLOW_DISTANCE * 4) {
            this.naviPosition = player.getPos().add(0, 2, 0);
        }
        if (distanceToTarget > FOLLOW_DISTANCE * 2) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED);
        }
        else if (distanceToTarget > FOLLOW_DISTANCE) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED * 0.5);
        }
        else if (this.naviIdle <= 800) {
            this.circleAroundPlayer(player);
            if (this.naviIdle == 300) {
                if (this.getFairyType() == 1) {
                    ZeldaClientPackets.sendFairySound(7);
                }
                else if (this.getFairyType() == 2) {
                    ZeldaClientPackets.sendFairySound(8);
                }
            }
        }
        else {
            this.setVelocityTowards(player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0), SPEED * 0.5);
            if (this.getPosition().distanceTo(player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0)) <= 0.2) {
                this.tempScale = 0;
                if (this.fireSound) {
                    if (this.getFairyType() == 2) {
                        ZeldaClientPackets.sendFairySound(3);
                    }
                    else {
                        ZeldaClientPackets.sendFairySound(1);
                    }
                    this.fireSound = false;
                }
            }
        }

        if (player.getVelocity().horizontalLength() == 0) {
            this.naviIdle++;
        }
        else {
            this.naviIdle = 0;
            if (this.tempScale != 1) {
                this.tempScale = 1;
                this.fireSound = true;
                this.setVelocityTowards(player.getPos().add(0.0, 2.0, 0.0), SPEED);

                if (this.getFairyType() == 2) {
                    ZeldaClientPackets.sendFairySound(4);
                }
                else {
                    ZeldaClientPackets.sendFairySound(2);
                }
            }
        }
    }

    public void circleAroundPlayer(AbstractClientPlayerEntity player) {
        double radius = 2.0;
        double angleIncrement = 0.02;

        this.currentAngle += angleIncrement;
        if (this.currentAngle >= 2 * Math.PI) {
            this.currentAngle -= 2 * Math.PI;
        }

        double targetX = player.getX() + radius * Math.cos(this.currentAngle);
        double targetZ = player.getZ() + radius * Math.sin(this.currentAngle);
        Vec3d targetPosition = new Vec3d(targetX,
                MathHelper.lerp(0.2, this.getPosition().getY(), player.getEyeY() + player.getRandom().nextBetween(-2, 2)),
                targetZ);

        this.setVelocityTowards(targetPosition, 0.02);
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

    public int getFairyType() {
        return this.fairyType;
    }

    public void setFairyType(int fairyType) {
        this.fairyType = fairyType;
    }
}