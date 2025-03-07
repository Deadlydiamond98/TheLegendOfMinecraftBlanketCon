package net.deadlydiamond98.renderer.doors;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.doors.DungeonDoor;
import net.deadlydiamond98.blocks.entities.doors.DungeonDoorEntity;
import net.deadlydiamond98.model.DungeonDoorModel;
import net.deadlydiamond98.util.interfaces.block.Lockable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class AutoDungeonDoorRenderer extends DungeonDoorRenderer {

    public AutoDungeonDoorRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(String color) {
        return new Identifier(ZeldaCraft.MOD_ID, "textures/entity/doors/auto_" + color + "_dungeon_door.png");
    }
}