package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.deadlydiamond98.model.entity.KeeseEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class KeeseRenderer<T extends Entity> extends MobEntityRenderer<KeeseEntity, KeeseEntityModel<KeeseEntity>> {
    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/keese.png");

    public KeeseRenderer(EntityRendererFactory.Context context) {
           super(context, new KeeseEntityModel<KeeseEntity>(context.getPart(KeeseEntityModel.LAYER_LOCATION)), 0.25F);
    }

    public Identifier getTexture(KeeseEntity batEntity) {
            return TEXTURE;
    }

    protected void scale(KeeseEntity batEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.5F, 0.5F, 0.5F);
    }

    protected void setupTransforms(KeeseEntity batEntity, MatrixStack matrixStack, float f, float g, float h) {
        matrixStack.translate(0.0F, MathHelper.cos(f * 0.3F) * 0.1F, 0.0F);

        super.setupTransforms(batEntity, matrixStack, f, g, h);
    }
}