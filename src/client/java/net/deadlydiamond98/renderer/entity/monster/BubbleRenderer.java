package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.model.entity.BubbleEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BubbleRenderer<T extends Entity> extends MobEntityRenderer<BubbleEntity, BubbleEntityModel<BubbleEntity>> {
    private static final Identifier TEXTURE = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/bubble.png");

    public BubbleRenderer(EntityRendererFactory.Context context) {
           super(context, new BubbleEntityModel<>(context.getPart(BubbleEntityModel.LAYER_LOCATION)), 0.25F);
    }

    public Identifier getTexture(BubbleEntity batEntity) {
            return TEXTURE;
    }

    @Override
    public void render(BubbleEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(BubbleEntity entity, MatrixStack matrices, float amount) {
        super.scale(entity, matrices, amount);
        matrices.scale(1.25f, 1.25f, 1.25f);
    }

    @Override
    protected void setupTransforms(BubbleEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        if (!entity.getAttackableState()) {
            matrices.translate(0.0F, MathHelper.cos(animationProgress * 0.3F) * 0.1F, 0.0F);
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta, scale);
    }
}