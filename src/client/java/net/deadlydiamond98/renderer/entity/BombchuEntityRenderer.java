package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.entities.BombchuEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.deadlydiamond98.model.entity.BombchuEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class BombchuEntityRenderer extends EntityRenderer<BombchuEntity> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/bombchu_entity.png");

    private final BombchuEntityModel<BombchuEntity> entityModel;
    public BombchuEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombchuEntityModel<>(ctx.getPart(BombchuEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombchuEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(BombchuEntity entity) {
        return TEXTURE;
    }
}
