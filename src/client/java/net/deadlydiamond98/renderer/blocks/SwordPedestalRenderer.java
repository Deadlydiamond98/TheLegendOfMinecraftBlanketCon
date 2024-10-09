package net.deadlydiamond98.renderer.blocks;

import net.deadlydiamond98.blocks.entities.PedestalBlockEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class SwordPedestalRenderer<T extends BlockEntity> implements BlockEntityRenderer<PedestalBlockEntity> {

    public SwordPedestalRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(PedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getRotation()));

        matrices.push();
        matrices.translate(0, 0.2, 0);

        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90+45));

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                entity.getStack(0),
                ModelTransformationMode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                (int)entity.getPos().asLong()
        );
        matrices.pop();
        matrices.pop();
        matrices.pop();
    }
}