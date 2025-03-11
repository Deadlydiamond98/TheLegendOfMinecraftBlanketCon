package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.model.entity.FairyEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FairyRenderer<T extends Entity> extends MobEntityRenderer<FairyEntity, FairyEntityModel<FairyEntity>> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/blue_fairy.png");

    public FairyRenderer(EntityRendererFactory.Context context) {
           super(context, new FairyEntityModel<>(context.getPart(FairyEntityModel.LAYER_LOCATION)), 0.25F);
    }

    public Identifier getTexture(FairyEntity fairy) {
            return TEXTURE;
    }

    @Override
    public void render(FairyEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        renderBody(mobEntity, matrixStack, vertexConsumerProvider);
    }

    private void renderBody(FairyEntity fairy, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        matrixStack.push();

        matrixStack.translate(0.0F, 0.3125F, 0.0F);
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(camera.getYaw()));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(360.0f - MathHelper.clamp(camera.getPitch(), -80, 80)));


        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(
                new Identifier(ZeldaCraft.MOD_ID, "textures/entity/" + fairy.getColor() + "_fairy.png")
        ));
        float minUV = 0.0F;
        float maxUV = 0.375F;

        int light = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.15F,  0.15F, 0.01F).color(255, 255, 255, 255).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.15F,  0.15F, 0.01F).color(255, 255, 255, 255).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.15F, -0.15F, 0.01F).color(255, 255, 255, 255).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.15F, -0.15F, 0.01F).color(255, 255, 255, 255).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();

        vertexConsumer.vertex(modelMatrix, -0.17F,  0.17F, 0.0F).color(255, 255, 255, 50).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.17F,  0.17F, 0.0F).color(255, 255, 255, 50).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.17F, -0.17F, 0.0F).color(255, 255, 255, 50).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.17F, -0.17F, 0.0F).color(255, 255, 255, 50).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();

        vertexConsumer.vertex(modelMatrix, -0.18F,  0.18F, 0.0F).color(255, 255, 255, 10).texture(minUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.18F,  0.18F, 0.0F).color(255, 255, 255, 10).texture(maxUV, maxUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  0.18F, -0.18F, 0.0F).color(255, 255, 255, 10).texture(maxUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.18F, -0.18F, 0.0F).color(255, 255, 255, 10).texture(minUV, minUV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();

        matrixStack.pop();
    }

    protected void scale(FairyEntity fairy, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
    }

    protected void setupTransforms(FairyEntity fairy, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(fairy, matrixStack, f, g, h);
    }
}