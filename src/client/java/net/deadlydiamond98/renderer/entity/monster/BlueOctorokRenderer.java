package net.deadlydiamond98.renderer.entity.monster;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.octoroks.OctorokEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class BlueOctorokRenderer extends OctorokRenderer {
    private static final Identifier TEXTURE = Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/blue_octorok.png");

    public BlueOctorokRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(OctorokEntity batEntity) {
            return TEXTURE;
    }
}