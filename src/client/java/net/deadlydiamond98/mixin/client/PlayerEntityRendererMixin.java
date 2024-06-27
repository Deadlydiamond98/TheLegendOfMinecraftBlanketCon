package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.renderer.transformations.FairyPlayerRenderer;
import net.deadlydiamond98.util.NaviAccessor;
import net.deadlydiamond98.util.NaviState;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin implements NaviAccessor {
    @Unique
    private final Map<UUID, NaviState> naviStateMap = new HashMap<>();
    @Unique
    private EntityRendererFactory.Context context;
    @Unique
    private FairyPlayerRenderer<AbstractClientPlayerEntity> fairyRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
        this.fairyRenderer = new FairyPlayerRenderer<>(context);
        this.context = context;
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
                          VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {

        naviStateMap.computeIfAbsent(player.getUuid(), uuid -> new NaviState(player.getPos(), context));

        NaviState naviState = naviStateMap.get(player.getUuid());

        renderNavi(player, yaw, tickDelta, matrices, vertexConsumers, light, naviState);
        if (((OtherPlayerData) player).isFairy()) {
            matrices.push();
            this.fairyRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
            info.cancel();
        }
    }

    @Override
    public void renderNavi(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
                            VertexConsumerProvider vertexConsumers, int light, NaviState naviState) {
        if (!naviState.startPosInit) {
            naviState.naviPosition = player.getPos().add(0, 2, 0);
            naviState.startPosInit = true;
        }

        if (naviState.tempScale == 0) {
            naviState.naviPosition = player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0);
        }

        naviState.naviPrevPosition = naviState.naviPosition;
        naviState.naviPrevYaw = naviState.naviYaw;

        double distanceToTarget = naviState.getPosition().distanceTo(player.getPos());
        if (distanceToTarget > NaviState.FOLLOW_DISTANCE * 2) {
            naviState.setVelocityTowards(player.getPos().add(0, 2, 0), NaviState.SPEED);
        }
        else if (distanceToTarget > NaviState.FOLLOW_DISTANCE) {
            naviState.setVelocityTowards(player.getPos().add(0, 2, 0), NaviState.SPEED * 0.5);
        }
        else if (naviState.naviIdle <= 300) {
            circleAroundPlayer(player, naviState);
        }
        else {
            naviState.setVelocityTowards(player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0), NaviState.SPEED * 0.5);
            if (naviState.getPosition().distanceTo(player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0)) <= 0.2) {
                naviState.tempScale = 0;
            }
        }

        if (player.getVelocity().horizontalLength() == 0) {
            naviState.naviIdle++;
        }
        else {
            naviState.naviIdle = 0;
            naviState.tempScale = 1;
        }

        naviState.naviPosition = naviState.getPosition().add(naviState.getVelocity());

        naviState.naviYaw = (float) (MathHelper.atan2(naviState.getVelocity().z, naviState.getVelocity().x) * (180F / Math.PI)) - 90.0F;

        matrices.push();

        double interpolatedX = MathHelper.lerp(tickDelta, naviState.naviPrevPosition.x, naviState.naviPosition.x);
        double interpolatedY = MathHelper.lerp(tickDelta, naviState.naviPrevPosition.y, naviState.naviPosition.y);
        double interpolatedZ = MathHelper.lerp(tickDelta, naviState.naviPrevPosition.z, naviState.naviPosition.z);
        double playerInterpolatedX = MathHelper.lerp(tickDelta, player.prevX, player.getX());
        double playerInterpolatedY = MathHelper.lerp(tickDelta, player.prevY, player.getY());
        double playerInterpolatedZ = MathHelper.lerp(tickDelta, player.prevZ, player.getZ());

        matrices.translate(interpolatedX - playerInterpolatedX, interpolatedY - playerInterpolatedY, interpolatedZ - playerInterpolatedZ);
        matrices.scale(naviState.tempScale, naviState.tempScale, naviState.tempScale);

        naviState.naviRenderer.renderBody(player, matrices, vertexConsumers);

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, naviState.naviPrevYaw, naviState.naviYaw) + 180));

        naviState.naviRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.pop();
    }

    private void circleAroundPlayer(AbstractClientPlayerEntity player, NaviState naviState) {
        double radius = 2.0;
        double angleIncrement = 0.02;

        naviState.currentAngle += angleIncrement;
        if (naviState.currentAngle >= 2 * Math.PI) {
            naviState.currentAngle -= 2 * Math.PI;
        }

        double targetX = player.getX() + radius * Math.cos(naviState.currentAngle);
        double targetZ = player.getZ() + radius * Math.sin(naviState.currentAngle);
        Vec3d targetPosition = new Vec3d(targetX,
                MathHelper.lerp(0.2, naviState.getPosition().getY(), player.getEyeY() + player.getRandom().nextBetween(-2, 2)),
                targetZ);

        naviState.setVelocityTowards(targetPosition, 0.02);
    }

    @Override
    public NaviState getNaviState(UUID playerUUID) {
        return this.naviStateMap.get(playerUUID);
    }
    @Override
    public void setNaviState(UUID playerUUID, NaviState naviState) {
        this.naviStateMap.put(playerUUID, naviState);
    }

    @Override
    public EntityRendererFactory.Context getContext() {
        return this.context;
    }
}
