// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.entities.BombchuEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import javax.swing.text.html.parser.Entity;

public class BombchuEntityModel<T extends BombchuEntity> extends EntityModel<BombchuEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "bombchu_entity"), "main");
	private final ModelPart bomb;

	private final ModelPart tail;
	public BombchuEntityModel(ModelPart root) {
		this.bomb = root.getChild("bomb");
		this.tail = bomb.getChild("tail");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bomb = modelPartData.addChild("bomb", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -0.01F, -4.0F, 6.0F, 4.0F, 7.0F, new Dilation(0.0F))
				.uv(25, 10).cuboid(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = bomb.addChild("cube_r1", ModelPartBuilder.create().uv(0, 11).cuboid(-0.5F, 0.0F, -0.75F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, -3.0F, 0.0F, -1.0908F, 0.0F));

		ModelPartData cube_r2 = bomb.addChild("cube_r2", ModelPartBuilder.create().uv(20, 0).cuboid(-2.25F, -2.4F, 0.0F, 4.0F, 3.0F, 2.0F, new Dilation(-1.0F)), ModelTransform.of(-1.0F, 1.5F, -5.0F, -0.0411F, -0.3027F, 0.1372F));

		ModelPartData cube_r3 = bomb.addChild("cube_r3", ModelPartBuilder.create().uv(20, 0).mirrored().cuboid(-1.75F, -2.4F, 0.0F, 4.0F, 3.0F, 2.0F, new Dilation(-1.0F)).mirrored(false), ModelTransform.of(1.0F, 1.5F, -5.0F, -0.0411F, 0.3027F, -0.1372F));

		ModelPartData cube_r4 = bomb.addChild("cube_r4", ModelPartBuilder.create().uv(8, 11).cuboid(-0.5F, 0.0F, -0.75F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -3.0F, 0.0F, 1.0908F, 0.0F));

		ModelPartData tail = bomb.addChild("tail", ModelPartBuilder.create().uv(-2, 0).cuboid(-0.5F, 0.5F, 3.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(BombchuEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bomb.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}