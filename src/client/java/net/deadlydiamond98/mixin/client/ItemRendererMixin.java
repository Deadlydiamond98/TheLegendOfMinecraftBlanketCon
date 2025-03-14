package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.dungeon.SecretStone;
import net.deadlydiamond98.blocks.entities.onoff.CrystalSwitchBlockEntity;
import net.deadlydiamond98.blocks.redstoneish.pushblock.PushBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {


    @Unique
    private static final CrystalSwitchBlockEntity RENDER_CRYSTAL_SWITCH = new CrystalSwitchBlockEntity(BlockPos.ORIGIN, ZeldaBlocks.Crystal_Switch.getDefaultState());


    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onRender(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        Item item = stack.getItem();

        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            BlockState blockState = block.getDefaultState();

            matrices.push();
            if (blockState.isOf(ZeldaBlocks.Crystal_Switch)) {

                PlayerEntity player = MinecraftClient.getInstance().player;

                if (player != null) {
                    matrices.translate(0.5, 0.5, 0.5);
                    matrices.translate(0, (Math.sin(player.age * 0.05) * 0.05), 0);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(player.age * 0.1f));
                    matrices.translate(-0.5, -0.5, -0.5);
                }

                MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity((BlockEntity) RENDER_CRYSTAL_SWITCH,
                        matrices, vertexConsumers, light, overlay);
            }

            if (renderMode == ModelTransformationMode.GUI) {
                if (block instanceof SecretStone) {
                    renderSpecialZeldaBlockIcon(matrices, vertexConsumers, "secret");
                }
                else if (block instanceof PushBlock) {
                    renderSpecialZeldaBlockIcon(matrices, vertexConsumers, "push");
                }
            }

            matrices.pop();
        }
    }

    @Unique
    private void renderSpecialZeldaBlockIcon(MatrixStack matrices, VertexConsumerProvider vertexConsumers, String icon) {

        matrices.translate(0.5, 0.5, 0.5);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45));
        matrices.translate(-0.5, -0.5, -0.5);

        matrices.scale(0.4f, 0.4f, 1);

        matrices.translate(2.1, 1.35, -0.5);

        MatrixStack.Entry entry = matrices.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        VertexConsumer vertexConsumer;
        vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(
                Identifier.of(ZeldaCraft.MOD_ID, "textures/item/icon/" + icon + "_icon.png")
        ));

        int light = LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE;
        vertexConsumer.vertex(modelMatrix, -1,  1, 0.0F).color(255, 255, 255, 255).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  1,  1, 0.0F).color(255, 255, 255, 255).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix,  1, -1, 0.0F).color(255, 255, 255, 255).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
        vertexConsumer.vertex(modelMatrix, -1, -1, 0.0F).color(255, 255, 255, 255).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(entry, 0, 1, 0);
    }
}

