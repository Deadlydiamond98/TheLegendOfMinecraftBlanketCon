package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class BombEntityRenderer extends EntityRenderer<BombEntity> {
    private static final Identifier TEXTURE_I = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/bomb_entity.png");
    private static final Identifier TEXTURE_II = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/super_bomb_entity.png");

    private final BombEntityModel<BombEntity> entityModel;
    public BombEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombEntityModel<>(ctx.getPart(BombEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public Identifier getTexture(BombEntity entity) {
        if (entity.getBombType() == 2) {
            return TEXTURE_II;
        }
        return TEXTURE_I;
    }
}
