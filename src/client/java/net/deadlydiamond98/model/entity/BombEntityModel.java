// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BombEntityModel<T extends BombEntity> extends EntityModel<BombEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "bomb_entity"), "main");
	private final ModelPart top;
	private final ModelPart cube_r1;
	private final ModelPart base;
	public BombEntityModel(ModelPart root) {
		this.top = root.getChild("top");
		this.cube_r1 = this.top.getChild("cube_r1");
		this.base = root.getChild("base");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData top = modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.0F, 0.0F));

		ModelPartData cube_r1 = top.addChild("cube_r1", ModelPartBuilder.create().uv(17, 15).cuboid(0.0F, -0.5F, -0.5F, 0.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 21);
	}
	@Override
	public void setAngles(BombEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		top.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}