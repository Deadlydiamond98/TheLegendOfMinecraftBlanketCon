// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BubbleEntity;
import net.deadlydiamond98.entities.monsters.KeeseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BubbleEntityModel<T extends BubbleEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "bubble_entity"), "main");
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart wingr;
	private final ModelPart wingl;
	public BubbleEntityModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.wingl = main.getChild("wingl");
		this.wingr = main.getChild("wingr");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(10, 14).cuboid(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData wingr = main.addChild("wingr", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -6.0F, 0.0F));

		ModelPartData cube_r1 = wingr.addChild("cube_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-9.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData wingl = main.addChild("wingl", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -6.0F, 0.0F));

		ModelPartData cube_r2 = wingl.addChild("cube_r2", ModelPartBuilder.create().uv(24, 0).cuboid(-9.0F, -4.0F, 0.0F, 9.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 3.1416F));
		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.getAttackableState()) {
			this.wingr.yaw = MathHelper.cos(ageInTicks * 74 * 0.005f) * 0.25f * (float) Math.PI;
			this.wingl.yaw = -this.wingr.yaw;
			this.wingr.roll = 0;
			this.wingl.roll = -this.wingr.roll;
			this.wingr.pitch = MathHelper.cos(ageInTicks * 74 * 0.005f) * 0.5f;
			this.wingl.pitch = this.wingr.pitch;
		}
		else {
			this.wingr.yaw = 0;
			this.wingl.yaw = -this.wingr.yaw;
			this.wingr.roll = MathHelper.cos(ageInTicks * 74.48451F * 0.057453292F) * 3.1415927F * 0.25F;
			this.wingl.roll = -this.wingr.roll;
			this.wingr.pitch = -77.5F;
			this.wingl.pitch = this.wingr.pitch;
		}
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}