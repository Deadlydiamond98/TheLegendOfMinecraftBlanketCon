package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.entities.projectiles.HookshotEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.model.entity.HookshotHeadModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookshotRenderer extends EntityRenderer<HookshotEntity> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/block/chain.png");
    private static final Identifier CHAIN_TEXTURE = new Identifier("minecraft", "textures/block/anvil.png");
    private final HookshotHeadModel<HookshotEntity> entityModel;
    protected HookshotRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new HookshotHeadModel<>(ctx.getPart(HookshotHeadModel.LAYER_LOCATION));
    }

    @Override
    public void render(HookshotEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        PlayerEntity player = MinecraftClient.getInstance().player;

        if (!(player == null)) {
            matrices.push();

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
            this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            Vec3d start = new Vec3d(
                    player.getX() + 0.4 * Math.cos(Math.toRadians(player.bodyYaw)),
                    player.getEyeY(),
                    player.getZ() + 0.4 * Math.sin(player.bodyYaw)
                    );
            Vec3d end = start.subtract(
                    entity.getX(),
                    entity.getY(),
                    entity.getZ()
            );


            renderChain(start, end, entity, tickDelta, matrices, vertexConsumers, light);

            matrices.pop();
        }
    }

    public void renderChain(Vec3d start, Vec3d end, Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(CHAIN_TEXTURE));
        Vec3d direction = end.subtract(start).normalize();
        double length = start.distanceTo(end);
        double segmentLength = 0.1;
        int segments = (int) (length / segmentLength);

        for (int i = 0; i < segments; i++) {
            double t = i * segmentLength;
            Vec3d segmentStart = start.add(direction.multiply(t));
            Vec3d segmentEnd = start.add(direction.multiply(t + segmentLength));
            renderChainSegment(segmentStart, segmentEnd, matrices, vertexConsumer, light);
        }
    }

    private void renderChainSegment(Vec3d start, Vec3d end, MatrixStack matrices, VertexConsumer vertexConsumer, int light) {
        float x1 = (float) start.x;
        float y1 = (float) start.y;
        float z1 = (float) start.z;
        float x2 = (float) end.x;
        float y2 = (float) end.y;
        float z2 = (float) end.z;

        float u1 = 0.0F;
        float v1 = 0.0F;
        float u2 = 3.0F / 16.0F;
        float v2 = 1.0F;

        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(255, 255, 255, 255).texture(u1, v1).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(255, 255, 255, 255).texture(u2, v1).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(255, 255, 255, 255).texture(u2, v2).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(255, 255, 255, 255).texture(u1, v2).light(light).next();

        u1 = 3.0F / 16.0F;
        u2 = 6.0F / 16.0F;

        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(255, 255, 255, 255).texture(u1, v2).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(255, 255, 255, 255).texture(u1, v1).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x2, y2, z2).color(255, 255, 255, 255).texture(u2, v1).light(light).next();
        vertexConsumer.vertex(matrices.peek().getPositionMatrix(), x1, y1, z1).color(255, 255, 255, 255).texture(u2, v2).light(light).next();
    }

    @Override
    public Identifier getTexture(HookshotEntity entity) {
        return null;
    }
}
