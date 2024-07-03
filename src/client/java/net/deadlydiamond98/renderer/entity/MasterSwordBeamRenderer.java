package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.projectiles.MasterSwordBeamEntity;
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

public class MasterSwordBeamRenderer<T extends Entity> extends EntityRenderer<MasterSwordBeamEntity> {
    public MasterSwordBeamRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(MasterSwordBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(2.0F, 2.0F, 2.0F);


        Vec3d velocity = entity.getVelocity();
        float yawAngle = (float) (Math.atan2(velocity.z, velocity.x) * (180 / Math.PI));
        float pitchAngle = (float) (Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)) * (180 / Math.PI));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yawAngle + 90));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-pitchAngle + 90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));
        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f modelMatrix = matrixEntry.getPositionMatrix();
        Matrix3f normalMatrix = matrixEntry.getNormalMatrix();

        float v = 0.25f * (entity.age % 4);

        //Render
        int emissiveLight = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.25F, 0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25F, 0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(1.0F, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.25F, -0.25F, 0.0F).color(255, 255, 255, 255).texture(0.0F, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(MasterSwordBeamEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(MasterSwordBeamEntity entity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/master_sword_beam.png");
    }
}
