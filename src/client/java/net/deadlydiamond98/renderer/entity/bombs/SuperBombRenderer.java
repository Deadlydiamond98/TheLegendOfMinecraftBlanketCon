package net.deadlydiamond98.renderer.entity.bombs;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.SuperBombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class SuperBombRenderer extends AbstractBombRenderer<SuperBombEntity>{
    public SuperBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(SuperBombEntity entity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/super_bomb_entity.png");
    }
}
