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
        float beamWidth = 5.0f / 16.0f;
        float beamOffset = beamWidth * -0.5f;

        matrices.push();

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 90));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch() + 90));

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(BEAM_TEXTURE));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();


        int textureIndex = entity.age % 3;
        float textureOffset = textureIndex * 5 / 16.0f;

        float textureU1 = textureOffset;
        float textureU2 = textureOffset + beamWidth;
        float textureV1 = 1;
        float textureV2 = 0;

        int emissiveLight = 15728880;

        buffer.vertex(matrix4f, beamOffset, 0, 0).color(255, 255, 255, 255).texture(textureU1, textureV1).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamWidth + beamOffset, 0, 0).color(255, 255, 255, 255).texture(textureU2, textureV1).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamWidth + beamOffset, 1, 0).color(255, 255, 255, 255).texture(textureU2, textureV2).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();
        buffer.vertex(matrix4f, beamOffset, 1, 0).color(255, 255, 255, 255).texture(textureU1, textureV2).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, 0.0F, 0.0F, -1.0F).next();

        buffer.vertex(matrix4f, 0, 0, beamOffset).color(255, 255, 255, 255).texture(textureU1, textureV1).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, 0, beamWidth + beamOffset).color(255, 255, 255, 255).texture(textureU2, textureV1).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, 1, beamWidth + beamOffset).color(255, 255, 255, 255).texture(textureU2, textureV2).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, 1, beamOffset).color(255, 255, 255, 255).texture(textureU1, textureV2).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(matrix3f, -1.0F, 0.0F, 0.0F).next();
        matrices.pop();
    }

    @Override
    public Identifier getTexture(BeamEntity entity) {
        return BEAM_TEXTURE;
    }
}
