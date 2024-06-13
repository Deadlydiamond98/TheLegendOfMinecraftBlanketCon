package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BombEntityRenderer extends EntityRenderer<BombEntity> {
    private static final Identifier TEXTURE_I = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/bomb_entity.png");
    private static final Identifier TEXTURE_II = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/super_bomb_entity.png");

    private static final Identifier Low_Fuse_Overlay_Texture = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/low_fuse_overlay.png");
    private static final RenderLayer Low_Fuse_Layer = RenderLayer.getEntityTranslucent(Low_Fuse_Overlay_Texture);

    private final BombEntityModel<BombEntity> entityModel;
    public BombEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombEntityModel<>(ctx.getPart(BombEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float time = entity.age + tickDelta;
        float scale = 1.0f + 0.05f * MathHelper.sin(time * 0.2f);
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + 90));
        matrices.scale(scale, scale, scale);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        if (entity.getFuse() <= 15) {
            VertexConsumer vertexCon = vertexConsumers.getBuffer(Low_Fuse_Layer);
            if (vertexCon != null) {
                this.entityModel.render(matrices, vertexCon, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F,
                        (float) Math.abs(Math.sin(entity.getFuse() * 0.4) * 0.5));
            }
        }
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BombEntity entity) {
        if (entity.getBombType() == 2) {
            return TEXTURE_II;
        }
        return TEXTURE_I;
    }
}
