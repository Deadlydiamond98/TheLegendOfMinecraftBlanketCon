package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.projectiles.BeamEntity;
import net.deadlydiamond98.entities.projectiles.MagicIceProjectileEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BeamProjectileRenderer<T extends Entity> extends EntityRenderer<BeamEntity> {

    public static final Identifier BEAM_TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/beam.png");
    public BeamProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        matrices.push();

        Vec3d initPos = new Vec3d(entity.getInitPos().x, entity.getInitPos().y, entity.getInitPos().z);

        Vec3d startPos = initPos.subtract(entity.getLerpedPos(tickDelta));
        Vec3d endPos = entity.getLerpedPos(tickDelta).add(0, 0.2, 0).subtract(entity.getLerpedPos(tickDelta)).subtract(startPos);

        ZeldaCraft.LOGGER.info("StartPos: " + startPos + "\nEndPos: " + endPos);
        VertexConsumer vertexConsumerBeam = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(BEAM_TEXTURE));
        matrices.translate(startPos.x, startPos.y, startPos.z);
        renderBeam(endPos, matrices, vertexConsumerBeam, 15728880, OverlayTexture.DEFAULT_UV, entity.age);
        matrices.pop();
    }

    public static void renderBeam(Vec3d end, MatrixStack matrices, VertexConsumer buffer, int light, int overlayCoords, int age) {
        double distance = end.horizontalLength();
        float beamWidth = 5.0f / 16.0f;
        float beamOffset = beamWidth * -0.5f;
        float beamLength = (float) end.length();
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (Math.atan2(end.x, end.z) * (double) (180F / (float) Math.PI))));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (-(Math.atan2(end.y, distance) * (double) (180F / (float) Math.PI))) - 90.0F));
        matrices.translate(0, -beamLength, 0);
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();

        int textureIndex = age % 3;
        float textureOffset = textureIndex * 5 / 16.0f;

        float textureU1 = textureOffset;
        float textureU2 = textureOffset + beamWidth;
        float textureV1 = beamLength / 16.0f;
        float textureV2 = 0;

        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(age % 360));

        buffer.vertex(matrix4f, beamOffset, 0, 0).color(255, 255, 255, 255).texture(textureU1, textureV1).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamWidth + beamOffset, 0, 0).color(255, 255, 255, 255).texture(textureU2, textureV1).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamWidth + beamOffset, beamLength, 0).color(255, 255, 255, 255).texture(textureU2, textureV2).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamOffset, beamLength, 0).color(255, 255, 255, 255).texture(textureU1, textureV2).overlay(overlayCoords).light(light).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();

        buffer.vertex(matrix4f, 0, 0, beamOffset).color(255, 255, 255, 255).texture(textureU1, textureV1).overlay(overlayCoords).light(light).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, 0, beamWidth + beamOffset).color(255, 255, 255, 255).texture(textureU2, textureV1).overlay(overlayCoords).light(light).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, beamLength, beamWidth + beamOffset).color(255, 255, 255, 255).texture(textureU2, textureV2).overlay(overlayCoords).light(light).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, beamLength, beamOffset).color(255, 255, 255, 255).texture(textureU1, textureV2).overlay(overlayCoords).light(light).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(BeamEntity entity) {
        return BEAM_TEXTURE;
    }
}
