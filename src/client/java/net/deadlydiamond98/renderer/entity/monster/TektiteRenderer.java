package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.entities.monsters.ArmosEntity;
import net.deadlydiamond98.entities.monsters.tektites.TektiteEntity;
import net.deadlydiamond98.model.entity.TektiteModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class TektiteRenderer<T extends TektiteEntity> extends MobEntityRenderer<T, TektiteModel<T>> {

    public TektiteRenderer(EntityRendererFactory.Context context) {
           super(context, new TektiteModel<>(context.getPart(TektiteModel.LAYER_LOCATION)), 0.25F);
    }

    public abstract Identifier getTexture(TektiteEntity batEntity);

    @Override
    public void render(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        super.scale(entity, matrices, amount);
        matrices.scale(1.25f, 1.25f, 1.25f);

    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        bodyYaw = entity.getYawClient();
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta, scale);
    }
}