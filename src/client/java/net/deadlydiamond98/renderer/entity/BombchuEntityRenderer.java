package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.entities.BombchuEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.model.entity.BombchuEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class BombchuEntityRenderer extends EntityRenderer<BombchuEntity> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/bombchu_entity.png");

    private static final Identifier Low_Fuse_Overlay_Texture = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/low_fuse_overlay.png");
    private static final RenderLayer Low_Fuse_Layer = RenderLayer.getEntityTranslucent(Low_Fuse_Overlay_Texture);

    private final BombchuEntityModel<BombchuEntity> entityModel;
    public BombchuEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombchuEntityModel<>(ctx.getPart(BombchuEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombchuEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(entity.getPitch()));
        Direction floorDirection = entity.getAttachedFace();
        switch (floorDirection) {
            case NORTH -> {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch() + 90));
                matrices.translate(0, -0.2, 0);
            }
            case SOUTH -> {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch() + 270));
                matrices.translate(0, -0.2, 0);
            }
            case WEST -> {
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                matrices.translate(0, -0.2, 0);
            }
            case EAST -> {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                matrices.translate(0, -0.2, 0);
            }
            case UP -> {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.35, 0);
            }
            case DOWN -> {
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, 0, 0);
            }
        }

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        if (entity.getFuse() <= 15) {
            VertexConsumer vertexCon = vertexConsumers.getBuffer(Low_Fuse_Layer);
            if (vertexCon != null) {
                this.entityModel.render(matrices, vertexCon, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F,
                        (float) Math.abs(Math.sin(entity.getFuse() * 0.4) * 0.75));
            }
        }
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BombchuEntity entity) {
        return TEXTURE;
    }
}
