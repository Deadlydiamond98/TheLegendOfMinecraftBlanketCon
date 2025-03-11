package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.doors.DungeonDoor;
import net.deadlydiamond98.blocks.entities.doors.DungeonDoorEntity;
import net.deadlydiamond98.model.DungeonDoorModel;
import net.deadlydiamond98.util.interfaces.block.ILockable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class DungeonDoorRenderer implements BlockEntityRenderer<DungeonDoorEntity> {
    private final DungeonDoorModel model;

    public DungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new DungeonDoorModel(ctx.getLayerModelPart(DungeonDoorModel.LAYER_LOCATION));
    }

    @Override
    public void render(DungeonDoorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getCachedState();

        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));

        double doorY = entity.getOpeningPosition();
        double prevDoorY = entity.getPrevopeningPosition();

        double currentPos = MathHelper.lerp(tickDelta * 0.5, prevDoorY, doorY);

        matrices.translate(0.5, -1.501 - currentPos, -0.5);
        entity.setPrevopeningPosition(currentPos);

        int yaw = blockState.get(DungeonDoor.FACING).getHorizontal() * 90;

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(this.getDoorTexture(entity)));
        model.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
    }

    private Identifier getDoorTexture(DungeonDoorEntity entity) {
        BlockState blockState = entity.getCachedState();
        String color = ((DungeonDoor) blockState.getBlock()).getColor().id;

        ILockable.LockType lockType = blockState.get(DungeonDoor.LOCKED);

        if (!lockType.isUnlocked()) {
            return getLockedTexture(color, lockType.asString());
        }
        return getTexture(color);
    }

    public Identifier getTexture(String color) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/" + color + "_dungeon_door.png");
    }

    public Identifier getLockedTexture(String color, String lock) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/" + lock + "_locked_" + color + "_dungeon_door.png");
    }
}