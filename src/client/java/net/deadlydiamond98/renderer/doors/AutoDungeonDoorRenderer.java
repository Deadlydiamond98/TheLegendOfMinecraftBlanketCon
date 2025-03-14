package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;

public class AutoDungeonDoorRenderer extends DungeonDoorRenderer {

    public AutoDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(String color) {
        return Identifier.of(ZeldaCraft.MOD_ID, "textures/entity/doors/auto_" + color + "_dungeon_door.png");
    }
}