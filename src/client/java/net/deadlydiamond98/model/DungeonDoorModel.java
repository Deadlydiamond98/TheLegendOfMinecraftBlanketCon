// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;


public class DungeonDoorModel extends EntityModel<Entity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "dungeon_door"), "main");

	private final ModelPart bb_main;

	public DungeonDoorModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -48.0F, -1.0F, 32.0F, 48.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 70, 51);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bb_main.render(matrices, vertices, light, overlay, color);
	}

	public ModelPart getPart() {
		return this.bb_main;
	}
}