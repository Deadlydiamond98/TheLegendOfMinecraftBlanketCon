package net.deadlydiamond98.renderer.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.onoff.AbstractOnOffBlock;
import net.deadlydiamond98.blocks.entities.onoff.CrystalSwitchBlockEntity;
import net.deadlydiamond98.model.CrystalSwitchModel;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class CrystalSwitchRenderer<T extends BlockEntity> implements BlockEntityRenderer<CrystalSwitchBlockEntity> {

    private final CrystalSwitchModel model;
    private final CrystalSwitchModel outline;

    public CrystalSwitchRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new CrystalSwitchModel(ctx.getLayerModelPart(CrystalSwitchModel.LAYER_LOCATION));
        this.outline = new CrystalSwitchModel(ctx.getLayerModelPart(CrystalSwitchModel.LAYER_LOCATION));
    }

    @Override
    public void render(CrystalSwitchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        float time = entity.getTicks();

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));

        matrices.translate(0.5, -2.25 + (Math.sin(time * 0.05) * 0.05), -0.5);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 0.1f));

        matrices.push();

        matrices.translate(0.0, 2.53, 0.0);

        matrices.scale(-1.025f, -1.025f, -1.025f);

        VertexConsumer vertexConsumerOutline = vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(this.getOutlineTexture(), false));

        outline.render(matrices, vertexConsumerOutline, LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE, overlay, 0xffffff);

        matrices.pop();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(this.getOrbTexture(entity)));
        model.render(matrices, vertexConsumer, 200, overlay, 0xffffff);

        matrices.pop();
    }

    public Identifier getOrbTexture(CrystalSwitchBlockEntity entity) {

        String color = "red";

        World world = entity.getWorld();
        if (world != null) {
            BlockState blockState = world.getBlockState(entity.getPos());

            if (blockState.isOf(ZeldaBlocks.Crystal_Switch)) {
                color = blockState.get(AbstractOnOffBlock.TRIGGERED) ? "red" : "blue";
            }
        }

        return Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/crystal_switch/" + color + "_crystal_switch_orb.png");
    }

    public Identifier getOutlineTexture() {
        return Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/crystal_switch/crystal_switch_outline.png");
    }
}
