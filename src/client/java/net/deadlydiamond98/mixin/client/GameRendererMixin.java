package net.deadlydiamond98.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.renderer.transformations.FairyPlayerRenderer;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
    //-----------------------------------------------------------------------------------------------------------------//
    //If anyone is looking at this, I am very confident this code is shit, I appologize if you need to bleach your eyes//
    //-----------------------------------------------------------------------------------------------------------------//
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
    //                                                                                                                 //
//    @Shadow @Final private MinecraftClient client;
//    @Shadow @Final private Camera camera;
//    @Shadow abstract double getFov(Camera camera, float tickDelta, boolean changingFov);
//
//    @Shadow public abstract Matrix4f getBasicProjectionMatrix(double fov);
//
//    @Shadow public abstract void loadProjectionMatrix(Matrix4f projectionMatrix);
//
//    @Shadow private boolean renderingPanorama;
//
//    @Shadow @Final private LightmapTextureManager lightmapTextureManager;
//
//    @Shadow @Final private BufferBuilderStorage buffers;
//
//    @Unique
//    private FairyPlayerRenderer<AbstractClientPlayerEntity> fairyRenderer;
//    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
//    private void RenderFairy(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
//        Camera camera = this.camera;
//        if (!this.renderingPanorama && this.client.player != null && ((OtherPlayerData) this.client.player).getFairyFriend()) {
//            RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
//            renderFairyCompanion(tickDelta, camera, matrices);
//        }
//    }
//
//    @Unique
//    private void renderFairyCompanion(float tickDelta, Camera camera, MatrixStack matrices) {
//        if (client != null && client.player != null) {
//            if (client.options.getPerspective().isFirstPerson()) {
//                RenderSystem.enableDepthTest();
//                this.loadProjectionMatrix(this.getBasicProjectionMatrix(this.getFov(camera, tickDelta, false)));
//                matrices.loadIdentity();
//                matrices.push();
//                PlayerEntityRenderer playerRenderer = (PlayerEntityRenderer) client.getEntityRenderDispatcher().getRenderer(client.player);
//
//                if (playerRenderer instanceof NaviAccessor naviAccessor) {
//                    NaviState naviState = naviAccessor.getNaviState(client.player.getUuid());
//                    if (naviState == null) {
//                        Vec3d initialPosition = client.player.getPos();
//                        EntityRendererFactory.Context context = ((NaviAccessor) playerRenderer).getContext();
//                        this.fairyRenderer = new FairyPlayerRenderer<>(context);
//                        naviState = new NaviState(initialPosition, context);
//                        naviAccessor.setNaviState(client.player.getUuid(), naviState);
//                    }
//
//                    Vec3d naviPosition = naviState.getPosition();
//                    Vec3d cameraPos = client.player.getCameraPosVec(tickDelta);
//                    Vec3d relativePosition = cameraPos.subtract(naviPosition);
//
//                    float playerYaw = client.player.getYaw(tickDelta);
//                    float playerPitch = client.player.getPitch(tickDelta);
//                    float yawRadians = -playerYaw * (float) Math.PI / 180.0F;
//                    float pitchRadians = playerPitch * (float) Math.PI / 180.0F;
//
//                    double adjustedX = relativePosition.x * Math.cos(yawRadians) - relativePosition.z * Math.sin(yawRadians);
//                    double adjustedZ = relativePosition.x * Math.sin(yawRadians) + relativePosition.z * Math.cos(yawRadians);
//
//                    double adjustedY = -relativePosition.y * Math.cos(pitchRadians) - adjustedZ * Math.sin(pitchRadians);
//                    adjustedZ = -relativePosition.y * Math.sin(pitchRadians) + adjustedZ * Math.cos(pitchRadians);
//
//                    matrices.translate(adjustedX, adjustedY, adjustedZ);
//                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(playerYaw));
//
//                    naviState.renderNavi(client.player, 0, tickDelta, matrices,
//                            client.getBufferBuilders().getEntityVertexConsumers(),
//                            client.getEntityRenderDispatcher().getLight(client.player, tickDelta), false);
//                }
//                matrices.pop();
//                this.lightmapTextureManager.enable();
//                this.buffers.getEntityVertexConsumers().draw();
//                this.lightmapTextureManager.disable();
//                RenderSystem.disableDepthTest();
//            }
//        }
//    }
}
