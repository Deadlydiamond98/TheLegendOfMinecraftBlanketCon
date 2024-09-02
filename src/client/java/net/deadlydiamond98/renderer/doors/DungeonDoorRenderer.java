package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DungeonDoorRenderer extends AbstractDungeonDoorRenderer {
    public DungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getLockedTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/locked_dungeon_door.png");
    }

    @Override
    public Identifier getTexture() {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/dungeon_door.png");
    }
}
