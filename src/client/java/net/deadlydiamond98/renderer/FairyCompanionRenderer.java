package net.deadlydiamond98.renderer;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.model.entity.FairyEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FairyCompanionRenderer<T extends PlayerEntity> extends LivingEntityRenderer<T, FairyEntityModel<T>> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/blue_fairy.png");
    private String color;

    public FairyCompanionRenderer(EntityRendererFactory.Context context) {
        super(context, new FairyEntityModel<>(context.getPart(FairyEntityModel.LAYER_LOCATION)), 0.25F);
        this.color = "blue";
    }

    public Identifier getTexture(PlayerEntity fairy) {
        return TEXTURE;
    }

    @Override
    public void render(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public void renderBody(T fairy, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        matrixStack.push();

        matrixStack.translate(0.0F, 0.3125F, 0.0F);
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(camera.getYaw()));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(360.0f - MathHelper.clamp(camera.getPitch(), -80, 80)));


        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        VertexConsumer vertexConsumer;
        vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(
                new Identifier(ZeldaCraft.MOD_ID, "textures/entity/" + this.color + "_fairy.png")
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

    protected void scale(T fairy, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
    }

    protected void setupTransforms(T fairy, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(fairy, matrixStack, f, g, h);
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}