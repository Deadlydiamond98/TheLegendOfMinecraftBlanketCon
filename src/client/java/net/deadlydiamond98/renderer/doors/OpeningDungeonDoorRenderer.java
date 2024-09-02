package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

public class OpeningDungeonDoorRenderer extends DungeonDoorRenderer {
    public OpeningDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/auto_dungeon_door.png");
    }
}
