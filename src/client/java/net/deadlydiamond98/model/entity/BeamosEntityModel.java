// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.BeamosEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BeamosEntityModel<T extends HostileEntity> extends EntityModel<BeamosEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "beamos_entity"), "main");
	private final ModelPart body;
	private final ModelPart eye;
	private final ModelPart connector;
	public BeamosEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.eye = body.getChild("eye");
		this.connector = root.getChild("connector");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-5.0F, -9.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F))
				.uv(28, 24).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

		ModelPartData bodyb_r1 = body.addChild("bodyb_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -9.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData spikestop = body.addChild("spikestop", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, 4.0F));

		ModelPartData cube_r1 = spikestop.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData cube_r2 = spikestop.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r3 = spikestop.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, -4.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r4 = spikestop.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData spikesmiddle = body.addChild("spikesmiddle", ModelPartBuilder.create(), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r5 = spikesmiddle.addChild("cube_r5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r6 = spikesmiddle.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r7 = spikesmiddle.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r8 = spikesmiddle.addChild("cube_r8", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 4.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData spikesbottom = body.addChild("spikesbottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -19.0F, 4.0F));

		ModelPartData cube_r9 = spikesbottom.addChild("cube_r9", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 18.0F, -4.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r10 = spikesbottom.addChild("cube_r10", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 18.0F, -8.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r11 = spikesbottom.addChild("cube_r11", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 18.0F, -4.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData cube_r12 = spikesbottom.addChild("cube_r12", ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 18.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData eye = body.addChild("eye", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, -7.0F, 3.25F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData connector = modelPartData.addChild("connector", ModelPartBuilder.create().uv(28, 0).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(BeamosEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		Entity viewedEntity = MinecraftClient.getInstance().getCameraEntity();
		if (entity.hasBeamTarget()) {
			viewedEntity = entity.getBeamTarget();
		}

		if (viewedEntity != null) {
			Vec3d vec3d = ((Entity)viewedEntity).getCameraPosVec(0.0F);
			Vec3d vec3d2 = entity.getCameraPosVec(0.0F);
			double d = vec3d.y - vec3d2.y;
			if (d > 0.0) {
				this.eye.pivotY = 0.0F;
			} else {
				this.eye.pivotY = 1.0F;
			}

			Vec3d vec3d3 = entity.getRotationVec(0.0F);
			vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
			Vec3d vec3d4 = (new Vec3d(vec3d2.x - vec3d.x, 0.0, vec3d2.z - vec3d.z)).normalize().rotateY(1.5707964F);
			double e = vec3d3.dotProduct(vec3d4);
			this.eye.pivotX = -MathHelper.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
		}
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		connector.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}