package net.deadlydiamond98.renderer.entity.projectile_items;

import net.deadlydiamond98.entities.projectiles.BoomerangProjectile;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class BoomerangProjectileRenderer<T extends Entity> extends EntityRenderer<BoomerangProjectile> {
    public BoomerangProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BoomerangProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }

    @Override
    public Identifier getTexture(BoomerangProjectile entity) {
        return null;
    }
}
