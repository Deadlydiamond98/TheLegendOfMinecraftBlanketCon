package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.tektites.RedTektite;
import net.deadlydiamond98.entities.monsters.tektites.TektiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class RedTektiteRenderer extends TektiteRenderer<RedTektite>{
    public RedTektiteRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TektiteEntity batEntity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/red_tektite.png");
    }
}
