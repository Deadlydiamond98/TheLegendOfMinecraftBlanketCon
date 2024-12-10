package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.LikeLikeEntity;
import net.deadlydiamond98.entities.monsters.RamblinMushroomEntity;
import net.deadlydiamond98.model.entity.LikeLikeModel;
import net.deadlydiamond98.model.entity.RamblinMushroomModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RamblinMushroomRenderer extends MobEntityRenderer<RamblinMushroomEntity, RamblinMushroomModel> {

    public RamblinMushroomRenderer(EntityRendererFactory.Context context) {
        super(context, new RamblinMushroomModel(context.getPart(RamblinMushroomModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    protected void setupTransforms(RamblinMushroomEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(RamblinMushroomEntity entity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/ramblin_mushroom.png");
    }
}