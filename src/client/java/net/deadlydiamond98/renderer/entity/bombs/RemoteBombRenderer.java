package net.deadlydiamond98.renderer.entity.bombs;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.RemoteBombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class RemoteBombRenderer extends AbstractBombRenderer<RemoteBombEntity> {

    public RemoteBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(RemoteBombEntity entity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/remote_bomb_entity.png");
    }
}
