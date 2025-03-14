package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.keese.KeeseEntity;
import net.deadlydiamond98.model.entity.KeeseEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class KeeseRenderer<T extends Entity> extends MobEntityRenderer<KeeseEntity, KeeseEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/keese.png");

    public KeeseRenderer(EntityRendererFactory.Context context) {
           super(context, new KeeseEntityModel(context.getPart(KeeseEntityModel.LAYER_LOCATION)), 0.25F);
    }

    public Identifier getTexture(KeeseEntity batEntity) {
            return TEXTURE;
    }

    protected void scale(KeeseEntity batEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
    }

    @Override
    protected void setupTransforms(KeeseEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
        matrices.translate(0.0F, MathHelper.cos(animationProgress * 0.3F) * 0.1F, 0.0F);
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta, scale);
    }
}