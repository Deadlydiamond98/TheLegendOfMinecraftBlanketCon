package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BlueTektite;
import net.deadlydiamond98.entities.monsters.RedTektite;
import net.deadlydiamond98.entities.monsters.TektiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class BlueTektiteRenderer extends TektiteRenderer<BlueTektite>{
    public BlueTektiteRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TektiteEntity batEntity) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/blue_tektite.png");
    }
}
