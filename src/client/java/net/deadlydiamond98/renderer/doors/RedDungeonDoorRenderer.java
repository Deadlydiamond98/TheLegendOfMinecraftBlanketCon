package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

public class RedDungeonDoorRenderer extends AbstractDungeonDoorRenderer {
    public RedDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/red_dungeon_door.png");
    }

    @Override
    public Identifier getLockedTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/locked_red_dungeon_door.png");
    }
}
