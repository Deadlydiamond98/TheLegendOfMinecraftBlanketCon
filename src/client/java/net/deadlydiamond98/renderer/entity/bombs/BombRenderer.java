package net.deadlydiamond98.renderer.entity.bombs;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.model.entity.BombEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BombRenderer extends AbstractBombRenderer<BombEntity> {

    public BombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BombEntity entity) {
        return Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/bomb_entity.png");
    }
}
