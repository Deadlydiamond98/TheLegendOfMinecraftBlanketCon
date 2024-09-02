package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.blocks.entities.doors.AbstractDungeonDoorEntity;
import net.deadlydiamond98.model.DungeonDoorModel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public abstract class AbstractDungeonDoorRenderer<T extends BlockEntity> implements BlockEntityRenderer<AbstractDungeonDoorEntity> {
    private final DungeonDoorModel model;

    public AbstractDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new DungeonDoorModel(ctx.getLayerModelPart(DungeonDoorModel.LAYER_LOCATION));
    }

    @Override
    public void render(AbstractDungeonDoorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));

        double doorY = entity.getOpeningPosition();
        double prevDoorY = entity.getPrevopeningPosition();

        double currentPos = MathHelper.lerp(tickDelta * 0.5, prevDoorY, doorY);

        matrices.translate(0.5, -1.501 - currentPos, -0.5);
        entity.setPrevopeningPosition(currentPos);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRotation()));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(this.getDoorTexture(entity)));
        model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }

    private Identifier getDoorTexture(AbstractDungeonDoorEntity entity) {
        if (entity.getLocked()) {
            return getLockedTexture();
        }
        return getTexture();
    }

    public abstract Identifier getLockedTexture();

    public abstract Identifier getTexture();
}