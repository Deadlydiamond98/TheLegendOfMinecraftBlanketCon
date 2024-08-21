package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.entities.projectiles.HookshotEntity;
import net.deadlydiamond98.model.entity.HookshotHeadModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookshotRenderer extends EntityRenderer<HookshotEntity> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/block/anvil.png");
    private static final Identifier CHAIN_TEXTURE = new Identifier("minecraft", "textures/block/chain.png");
    private final HookshotHeadModel<HookshotEntity> entityModel;
    public HookshotRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new HookshotHeadModel<>(ctx.getPart(HookshotHeadModel.LAYER_LOCATION));
    }

    @Override
    public void render(HookshotEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        PlayerEntity player = (PlayerEntity) entity.getOwner();

        if (player != null) {
            int brightness = 15728880;

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + 180));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
            matrices.translate(0, -0.75f, 0);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
            this.entityModel.render(matrices, vertexConsumer, brightness, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();

            //Chain Rendering
            matrices.push();
            Vec3d handPos = player.getLerpedPos(tickDelta).add(0, player.getEyeHeight(player.getPose()) - 0.6, 0)
                    .add(player.getRotationVec(tickDelta).normalize().crossProduct(new Vec3d(0, 1, 0))
                            .multiply(0.5));

            Vec3d playerPos = handPos.subtract(entity.getLerpedPos(tickDelta));
            Vec3d headPos = entity.getLerpedPos(tickDelta).add(0, 0.2, 0).subtract(entity.getLerpedPos(tickDelta)).subtract(playerPos);

            VertexConsumer vertexConsumerChain = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(CHAIN_TEXTURE));
            matrices.translate(playerPos.x, playerPos.y, playerPos.z);
            renderChain(headPos, matrices, vertexConsumerChain, brightness, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        }
    }

    @Override
    public boolean shouldRender(HookshotEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    public static void renderChain(Vec3d end, MatrixStack matrices, VertexConsumer buffer, int light, int overlayCoords) {
        double distance = end.horizontalLength();
        float chainWidth = 3F / 16F;
        float chainOffset = chainWidth * -0.5F;
        float chainLength = (float) end.length();
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (Math.atan2(end.x, end.z) * (double) (180F / (float) Math.PI))));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (-(Math.atan2(end.y, distance) * (double) (180F / (float) Math.PI))) - 90.0F));
        matrices.translate(0, -chainLength, 0);
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        // x links
        buffer.vertex(matrix4f, chainOffset, 0, 0).color(255, 255, 255, 255).texture((float) 0, (float) chainLength).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainWidth + chainOffset, 0, 0).color(255, 255, 255, 255).texture((float) chainWidth, (float) chainLength).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainWidth + chainOffset, chainLength, 0).color(255, 255, 255, 255).texture((float) chainWidth, (float) 0).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainOffset, chainLength, 0).color(255, 255, 255, 255).texture((float) 0, (float) 0).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        float pixelSkip = 2.5F / 16F;
        // z links
        buffer.vertex(matrix4f, 0, pixelSkip, chainOffset).color(255, 255, 255, 255).texture((float) chainWidth, (float) chainLength + pixelSkip).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, pixelSkip, chainWidth + chainOffset).color(255, 255, 255, 255).texture((float) chainWidth * 2, (float) chainLength + pixelSkip).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, chainLength + pixelSkip, chainWidth + chainOffset).color(255, 255, 255, 255).texture((float) chainWidth * 2, (float) pixelSkip).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, chainLength + pixelSkip, chainOffset).color(255, 255, 255, 255).texture((float) chainWidth, (float) pixelSkip).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        matrices.pop();
    }


    @Override
    public Identifier getTexture(HookshotEntity entity) {
        return TEXTURE;
    }
}
