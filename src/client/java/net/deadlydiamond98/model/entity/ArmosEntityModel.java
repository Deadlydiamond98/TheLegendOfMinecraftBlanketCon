// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.ArmosEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;

public class ArmosEntityModel<T extends HostileEntity> extends EntityModel<ArmosEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "armos_entity"), "main");

	private final ModelPart main;
	private final ModelPart armos;

	public ArmosEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.armos = this.main.getChild("armos");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData base = main.addChild("base", ModelPartBuilder.create().uv(0, 14).cuboid(-5.5F, -4.0F, -5.5F, 11.0F, 2.0F, 11.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData armos = main.addChild("armos", ModelPartBuilder.create().uv(0, 27).cuboid(-4.0F, -23.0F, -2.0F, 8.0F, 19.0F, 4.0F, new Dilation(0.04F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData hands = armos.addChild("hands", ModelPartBuilder.create(), ModelTransform.pivot(1.75F, -18.75F, 0.0F));

		ModelPartData cube_r1 = hands.addChild("cube_r1", ModelPartBuilder.create().uv(24, 35).cuboid(-11.0F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
		.uv(36, 35).cuboid(0.0F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, -3.25F, 0.0F, -0.48F, 0.0F, 0.0F));

		ModelPartData head = armos.addChild("head", ModelPartBuilder.create().uv(45, 22).cuboid(-4.5F, -7.5F, -4.5F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(40, 6).cuboid(-4.5F, -7.25F, -4.75F, 8.0F, 8.0F, 8.0F, new Dilation(-0.4F)), ModelTransform.pivot(0.5F, -23.5F, 0.5F));

		ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(48, 42).mirrored().cuboid(-1.75F, -5.86F, 0.0F, 4.0F, 7.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.0F, -5.5F, -0.5F, 0.0F, 0.0F, 0.3054F));

		ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(48, 42).cuboid(-2.25F, -5.86F, 0.0F, 4.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -5.5F, -0.5F, 0.0F, 0.0F, -0.3054F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(ArmosEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.getTriggered()) {
			this.armos.yaw = (float) Math.toRadians(11.25f * Math.sin(ageInTicks * 4));
		} else {
			this.armos.yaw = 0;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}