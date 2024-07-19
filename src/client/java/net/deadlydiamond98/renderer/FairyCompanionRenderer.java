package net.deadlydiamond98.renderer;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.PlayerFairyCompanion;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.model.entity.FairyEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FairyCompanionRenderer extends EntityRenderer<PlayerFairyCompanion> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/blue_fairy.png");
    private String color;
    private final FairyEntityModel<PlayerFairyCompanion> entityModel;

    public FairyCompanionRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.entityModel = new FairyEntityModel<>(context.getPart(FairyEntityModel.LAYER_LOCATION));
        this.color = "blue";
    }

    public Identifier getTexture(PlayerFairyCompanion fairy) {
        return TEXTURE;
    }

    @Override
    public void render(PlayerFairyCompanion mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if (mobEntity.getVisable()) {
            matrixStack.push();
            matrixStack.translate(0, Math.sin(mobEntity.age * 0.1) * 0.1, 0);

            renderBody(mobEntity, matrixStack, vertexConsumerProvider);
            matrixStack.scale(0.5F, 0.5F, 0.5F);

            matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(mobEntity.getYaw() + 180));
            matrixStack.translate(0, 1.5, 0);
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.entityModel.getLayer(getTexture(mobEntity)));
            this.entityModel.animateModel(mobEntity, f, g, MinecraftClient.getInstance().getTickDelta());
            this.entityModel.setAngles(mobEntity, f, g, mobEntity.age, mobEntity.getYaw(), mobEntity.getPitch());
            this.entityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public void renderBody(PlayerFairyCompanion fairy, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
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
}