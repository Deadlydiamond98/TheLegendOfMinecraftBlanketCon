package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.ArmosEntity;
import net.deadlydiamond98.model.entity.ArmosEntityModel;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ArmosRenderer<T extends Entity> extends MobEntityRenderer<ArmosEntity, ArmosEntityModel<ArmosEntity>> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/armos.png");

    public ArmosRenderer(EntityRendererFactory.Context context) {
        super(context, new ArmosEntityModel<>(context.getPart(ArmosEntityModel.LAYER_LOCATION)), 0.25F);
        this.addFeature(new ArmosEyesRenderer<>(this));
    }

    public Identifier getTexture(ArmosEntity mob) {
            return TEXTURE;
    }

    @Override
    protected void setupTransforms(ArmosEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        bodyYaw = entity.getYawClient();
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public boolean shouldRender(ArmosEntity mobEntity, Frustum frustum, double d, double e, double f) {
        return true;
    }
}