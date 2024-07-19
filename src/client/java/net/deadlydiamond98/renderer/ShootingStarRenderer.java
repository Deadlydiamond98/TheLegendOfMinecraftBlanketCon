package net.deadlydiamond98.renderer;

import net.deadlydiamond98.entities.ShootingStar;
import net.deadlydiamond98.entities.projectiles.boomerangs.BaseBoomerangProjectile;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class ShootingStarRenderer<T extends Entity> extends EntityRenderer<ShootingStar> {
    private final ItemRenderer itemRenderer;
    public ShootingStarRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ShootingStar entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        float rotation = entity.age + tickDelta;

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation * 30));

        ItemStack itemStack = ZeldaItems.Star_Fragment.getDefaultStack();
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.getWorld(), (LivingEntity) null, entity.getId());

        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND,
                false, matrices, vertexConsumers, 15728880, OverlayTexture.DEFAULT_UV, bakedModel);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(ShootingStar entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(ShootingStar entity) {
        return null;
    }
}
