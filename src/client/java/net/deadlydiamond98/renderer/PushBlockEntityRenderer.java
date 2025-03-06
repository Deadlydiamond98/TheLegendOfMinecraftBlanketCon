package net.deadlydiamond98.renderer;

import net.deadlydiamond98.entities.PushBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PushBlockEntityRenderer extends EntityRenderer<PushBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public PushBlockEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
        this.blockRenderManager = context.getBlockRenderManager();
    }

    public void render(PushBlockEntity pushBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = pushBlockEntity.getBlockState();
        
        if (blockState.getRenderType() == BlockRenderType.MODEL) {
            
            World world = pushBlockEntity.getWorld();
            
            if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                
                matrixStack.push();
                
                BlockPos blockPos = BlockPos.ofFloored(pushBlockEntity.getX(), pushBlockEntity.getBoundingBox().maxY, pushBlockEntity.getZ());
                
                matrixStack.translate(-0.5, 0.0, -0.5);

                this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState),
                        blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), 
                        false, Random.create(), blockState.getRenderingSeed(pushBlockEntity.getPushBlockPos()),
                        OverlayTexture.DEFAULT_UV);
                
                matrixStack.pop();
                
                super.render(pushBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
            }
        }
    }

    public Identifier getTexture(PushBlockEntity fallingBlockEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
