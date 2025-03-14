package net.deadlydiamond98.renderer.entity.bombs;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.bombchu.BombchuEntity;
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
import net.minecraft.util.math.RotationAxis;

public class BombchuEntityRenderer extends EntityRenderer<BombchuEntity> {
    private static final Identifier TEXTURE = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/bombchu_entity.png");

    private static final Identifier Low_Fuse_Overlay_Texture = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/low_fuse_overlay.png");
    private static final RenderLayer Low_Fuse_Layer = RenderLayer.getEntityTranslucent(Low_Fuse_Overlay_Texture);

    private final BombchuEntityModel<BombchuEntity> entityModel;
    public BombchuEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombchuEntityModel<>(ctx.getPart(BombchuEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombchuEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        Direction floor = entity.getAttachedFaceClient();

        switch (floor) {
            case DOWN -> {
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, 0.25, 0);
            }
            case UP -> {
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(entity.getPitch()));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.25, 0);
            }
            case NORTH -> {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.01625, 0);
            }
            case SOUTH -> {
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.01625, 0);
            }
            case EAST -> {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.01625, 0);
            }
            case WEST -> {
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(90));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw() + 180));
                matrices.translate(0, -0.01625, 0);
            }
        }

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));

        this.entityModel.setAngles(entity, 0 ,0, tickDelta, 0, 0);
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xffffff);

        if (entity.getFuse() <= 15) {
            VertexConsumer vertexCon = vertexConsumers.getBuffer(Low_Fuse_Layer);
            if (vertexCon != null) {

                float alphaFloat = (float) Math.abs(Math.sin(entity.getFuse() * 0.4) * 0.75);

                int alpha = (int) (alphaFloat * 255);

                int argbColor = (alpha << 24) | 0x00_FF_FF_FF;

                this.entityModel.render(matrices, vertexCon, light, OverlayTexture.DEFAULT_UV, argbColor);
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
