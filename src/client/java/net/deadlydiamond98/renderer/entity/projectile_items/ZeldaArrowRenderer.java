package net.deadlydiamond98.renderer.entity.projectile_items;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public class ZeldaArrowRenderer<T extends PersistentProjectileEntity> extends ProjectileEntityRenderer<T> {
    public static final Identifier Silver_Texture = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/silver_arrow.png");
    public ZeldaArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(T entity) {
        return Silver_Texture;
    }
}
