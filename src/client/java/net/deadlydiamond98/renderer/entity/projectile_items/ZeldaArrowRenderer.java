package net.deadlydiamond98.renderer.entity.projectile_items;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.arrows.SilverArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public class ZeldaArrowRenderer<T extends PersistentProjectileEntity> extends ProjectileEntityRenderer<T> {
    public static final Identifier Silver_Texture = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/silver_arrow.png");
    public static final Identifier Bomb_Texture = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/bomb_arrow.png");
    public ZeldaArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity instanceof SilverArrowEntity) {
            return Silver_Texture;
        }
        return Bomb_Texture;
    }
}
