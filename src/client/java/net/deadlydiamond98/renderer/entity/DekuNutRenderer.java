package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.projectiles.BaseBallEntity;
import net.deadlydiamond98.entities.projectiles.DekuNutEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class DekuNutRenderer<T extends Entity> extends EntityRenderer<DekuNutEntity> {
    public DekuNutRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(DekuNutEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0.0D, -0.25D, 0.0D);

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw()));
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(camera.getPitch()));
        //matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees());


        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));
        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f modelMatrix = matrixEntry.getPositionMatrix();
        Matrix3f normalMatrix = matrixEntry.getNormalMatrix();

        vertexConsumer.vertex(modelMatrix, -0.25F,  0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.25F,  0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, 0.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, 0.0F).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(DekuNutEntity entity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/item/deku_nut.png");
    }
}
