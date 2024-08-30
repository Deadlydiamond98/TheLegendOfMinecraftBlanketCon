package net.deadlydiamond98.renderer;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.entities.DungeonDoorEntity;
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

public class DungeonDoorRenderer<T extends BlockEntity> implements BlockEntityRenderer<DungeonDoorEntity> {
    private final DungeonDoorModel model;

    public DungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new DungeonDoorModel(ctx.getLayerModelPart(DungeonDoorModel.LAYER_LOCATION));
    }

    @Override
    public void render(DungeonDoorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));

        double doorY = entity.getOpeningPosition();
        double prevDoorY = entity.getPrevopeningPosition();

        double currentPos = MathHelper.lerp(tickDelta * 0.5, prevDoorY, doorY);

        matrices.translate(0.5, -1.501 - currentPos, -0.5);
        entity.setPrevopeningPosition(currentPos);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(
                new Identifier(ZeldaCraft.MOD_ID, "textures/entity/dungeon_door.png")));
        this.model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }


}