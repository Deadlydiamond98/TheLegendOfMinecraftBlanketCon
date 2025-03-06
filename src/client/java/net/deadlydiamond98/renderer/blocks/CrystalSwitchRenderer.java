package net.deadlydiamond98.renderer.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.entities.CrystalSwitchBlockEntity;
import net.deadlydiamond98.items.items.other.BindingRod;
import net.deadlydiamond98.items.items.other.EmeraldItem;
import net.deadlydiamond98.model.CrystalSwitchModel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

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

        outline.render(matrices, vertexConsumerOutline, LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(this.getOrbTexture()));
        model.render(matrices, vertexConsumer, 200, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.getMainHandStack().getItem() instanceof BindingRod) {
            for (BlockPos pos : entity.getConnectedBlocks()) {
                drawLine(entity.getPos(), pos, matrices, vertexConsumers);
            }
        }

        matrices.pop();
    }

    private void drawLine(BlockPos start, BlockPos end, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLines());
        matrices.push();

        Vec3d startVec = Vec3d.ofCenter(start);
        Vec3d endVec = Vec3d.ofCenter(end);

        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        buffer.vertex(positionMatrix, (float) startVec.x, (float) startVec.y, (float) startVec.z).color(255, 0, 0, 255).next();
        buffer.vertex(positionMatrix, (float) endVec.x, (float) endVec.y, (float) endVec.z).color(255, 0, 0, 255).next();

        matrices.pop();
    }

    public Identifier getOrbTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/crystal_switch/red_crystal_switch_orb.png");
    }

    public Identifier getOutlineTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/crystal_switch/crystal_switch_outline.png");
    }
}
