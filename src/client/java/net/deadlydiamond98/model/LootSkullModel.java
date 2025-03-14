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

public class LootSkullModel extends EntityModel<Entity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "loot_skull"), "main");

	private final ModelPart main;

	public LootSkullModel(ModelPart root) {
		this.main = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F))
		.uv(24, 6).cuboid(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 25.0F, -1.0F, -0.2182F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		main.render(matrices, vertices, light, overlay, color);
	}

	public void setHeadRotation(float animationProgress, float yaw, float pitch) {
		this.main.yaw = yaw * 0.017453292F;
		this.main.pitch = pitch * 0.017453292F;
	}
}