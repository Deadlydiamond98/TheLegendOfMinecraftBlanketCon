package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.octoroks.OctorokEntity;
import net.deadlydiamond98.model.entity.OctorokEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class OctorokRenderer extends MobEntityRenderer<OctorokEntity, OctorokEntityModel> {

    public OctorokRenderer(EntityRendererFactory.Context context) {
           super(context, new OctorokEntityModel(context.getPart(OctorokEntityModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    protected void setupTransforms(OctorokEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }
}