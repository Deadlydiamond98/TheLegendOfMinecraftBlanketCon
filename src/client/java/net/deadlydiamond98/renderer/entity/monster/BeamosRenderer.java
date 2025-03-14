package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.deadlydiamond98.model.entity.BeamosEntityModel;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BeamosRenderer<T extends Entity> extends MobEntityRenderer<BeamosEntity, BeamosEntityModel<BeamosEntity>> {
    private static final Identifier TEXTURE = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/beamos.png");

    public BeamosRenderer(EntityRendererFactory.Context context) {
           super(context, new BeamosEntityModel<BeamosEntity>(context.getPart(BeamosEntityModel.LAYER_LOCATION)), 0.25F);
    }

    public Identifier getTexture(BeamosEntity mob) {
            return TEXTURE;
    }

    @Override
    protected void scale(BeamosEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(1.5f, 1.5f, 1.5f);
        super.scale(entity, matrices, amount);
    }

    @Override
    public boolean shouldRender(BeamosEntity mobEntity, Frustum frustum, double d, double e, double f) {
        return true;
    }

    @Override
    protected void setupTransforms(BeamosEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        bodyYaw = entity.getYawClient();
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta, scale);
    }
}