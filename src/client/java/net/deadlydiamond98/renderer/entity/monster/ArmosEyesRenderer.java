package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.ArmosEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ArmosEyesRenderer<T extends ArmosEntity, M extends EntityModel<ArmosEntity>> extends FeatureRenderer<ArmosEntity, M> {

    private static final RenderLayer EYES = RenderLayer.getItemEntityTranslucentCull(Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/armos_eyes.png"));

    public ArmosEyesRenderer(FeatureRendererContext<ArmosEntity, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmosEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(EYES);

        float alphaFloat = (float) entity.getEyeAlpha();

        int alpha = (int) (alphaFloat * 255);

        int argbColor = (alpha << 24) | 0x00_FF_FF_FF;

        this.getContextModel().render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, argbColor);
    }
}
