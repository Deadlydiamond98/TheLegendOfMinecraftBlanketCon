package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.LikeLikeEntity;
import net.deadlydiamond98.entities.monsters.octoroks.OctorokEntity;
import net.deadlydiamond98.model.entity.LikeLikeModel;
import net.deadlydiamond98.model.entity.OctorokEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LikeLikeRenderer extends MobEntityRenderer<LikeLikeEntity, LikeLikeModel> {

    public LikeLikeRenderer(EntityRendererFactory.Context context) {
        super(context, new LikeLikeModel(context.getPart(LikeLikeModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    protected void setupTransforms(LikeLikeEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(LikeLikeEntity batEntity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/like_like.png");
    }
}