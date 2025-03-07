package net.deadlydiamond98.renderer.blocks;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.entities.loot.LootSkullBlockEntity;
import net.deadlydiamond98.blocks.loot.LootSkullBlock;
import net.deadlydiamond98.model.LootSkullModel;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationPropertyHelper;

public class LootSkullRenderer<T extends BlockEntity> implements BlockEntityRenderer<LootSkullBlockEntity> {

    private final LootSkullModel model;

    public LootSkullRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new LootSkullModel(ctx.getLayerModelPart(LootSkullModel.LAYER_LOCATION));
    }

    @Override
    public void render(LootSkullBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.5F, 0.0F, 0.5F);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0, -1.5, 0);

        BlockState blockState = entity.getCachedState();
        String skullType = ((LootSkullBlock) blockState.getBlock()).getLootSkullType().id;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(this.getSkullTexture(skullType)));

        int k = blockState.get(SkullBlock.ROTATION);
        float yaw = RotationPropertyHelper.toDegrees(k);

        model.setHeadRotation(0, yaw, 0.0F);
        model.render(matrices, vertexConsumer, 200, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
    }

    public Identifier getSkullTexture(String skullType) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/block/loot_skull/" + skullType + ".png");
    }

}
