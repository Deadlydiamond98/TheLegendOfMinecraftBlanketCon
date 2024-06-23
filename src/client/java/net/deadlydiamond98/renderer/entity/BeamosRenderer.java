package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.deadlydiamond98.model.entity.BeamosEntityModel;
import net.deadlydiamond98.model.entity.KeeseEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class BeamosRenderer<T extends Entity> extends MobEntityRenderer<BeamosEntity, BeamosEntityModel<BeamosEntity>> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/beamos.png");

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

    protected void setupTransforms(BeamosEntity mob, MatrixStack matrixStack, float f, float g, float h) {
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        g = mob.getYawClient();
        super.setupTransforms(mob, matrixStack, f, g, h);
    }
}