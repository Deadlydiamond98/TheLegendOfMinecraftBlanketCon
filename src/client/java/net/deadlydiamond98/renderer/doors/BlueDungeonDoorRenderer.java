package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

public class BlueDungeonDoorRenderer extends AbstractDungeonDoorRenderer {
    public BlueDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/blue_dungeon_door.png");
    }

    @Override
    public Identifier getLockedTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/locked_blue_dungeon_door.png");
    }
}
