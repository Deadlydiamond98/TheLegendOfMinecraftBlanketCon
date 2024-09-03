// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.TektiteEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TektiteModel<T extends TektiteEntity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "tektite_entity"), "main");

	private final float mainPivotY;
	private final ModelPart main;
	private final ModelPart eye;
	private final ModelPart backrightlega;
	private final ModelPart backrightlegb;
	private final ModelPart rightlega;
	private final ModelPart rightlegb;
	private final ModelPart leftlega;
	private final ModelPart leftlegb;
	private final ModelPart backleftlega;
	private final ModelPart backleftlegb;

	public TektiteModel(ModelPart root) {
        this.main = root.getChild("main");
		this.mainPivotY = this.main.pivotY + 2.5f;
		this.eye = main.getChild("eye");

		this.backrightlega = main.getChild("backrightlega");
		this.backrightlegb = backrightlega.getChild("backrightlegb");

		this.rightlega = main.getChild("rightlega");
		this.rightlegb = rightlega.getChild("rightlegb");

		this.leftlega = main.getChild("leftlega");
		this.leftlegb = leftlega.getChild("leftlegb");

		this.backleftlega = main.getChild("backleftlega");
		this.backleftlegb = backleftlega.getChild("backleftlegb");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -5.0F, 8.0F, 6.0F, 7.0F, new Dilation(0.0F))
		.uv(30, 4).cuboid(-4.0F, -3.0F, 2.0F, 8.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0F, 0.0F));

		ModelPartData eye = main.addChild("eye", ModelPartBuilder.create().uv(20, 18).cuboid(-0.5F, -1.0F, -5.1F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData backrightlega = main.addChild("backrightlega", ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.75F, 3.0F));

		ModelPartData backrightlegb = backrightlega.addChild("backrightlegb", ModelPartBuilder.create().uv(14, 21).cuboid(-0.5F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));

		ModelPartData rightlega = main.addChild("rightlega", ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.5F, -2.0F));

		ModelPartData rightlegb = rightlega.addChild("rightlegb", ModelPartBuilder.create().uv(14, 21).cuboid(-0.5F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));

		ModelPartData leftlega = main.addChild("leftlega", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.5F, -2.0F));

		ModelPartData leftlegb = leftlega.addChild("leftlegb", ModelPartBuilder.create().uv(0, 17).cuboid(-7.5F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));

		ModelPartData backleftlega = main.addChild("backleftlega", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.75F, 3.0F));

		ModelPartData backleftlegb = backleftlega.addChild("backleftlegb", ModelPartBuilder.create().uv(0, 17).cuboid(-7.5F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		Entity viewedEntity = MinecraftClient.getInstance().getCameraEntity();
		float time = ageInTicks / 20.0f;

		if (viewedEntity != null) {
			Vec3d vec3d = ((Entity)viewedEntity).getCameraPosVec(0.0F);
			Vec3d vec3d2 = entity.getCameraPosVec(0.0F);
			double d = vec3d.y - vec3d2.y;
			if (d > 0.0) {
				this.eye.pivotY = -1.0F;
			} else {
				this.eye.pivotY = 0.0F;
			}

			Vec3d vec3d3 = entity.getRotationVec(0.0F);
			vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
			Vec3d vec3d4 = (new Vec3d(vec3d2.x - vec3d.x, 0.0, vec3d2.z - vec3d.z)).normalize().rotateY(1.5707964F);
			double e = vec3d3.dotProduct(vec3d4);
			this.eye.pivotX = MathHelper.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
		}

		if (entity.getTektiteOnGround()) {

			this.main.pivotY = this.mainPivotY + oscillate(-1, 0, time, 2);

			// back right leg
			this.backrightlega.setAngles(
					oscillate(toRad(18.2273f), toRad(21.3938f), time, 2),
					oscillate(toRad(-17.133f), toRad(-12.8784f), time, 2),
					MathHelper.lerp(0.1f, this.backrightlega.roll, oscillate(toRad(-10.1089f), toRad(-22.2835f), time, 2))
			);
			this.backrightlegb.roll = MathHelper.lerp(0.1f, this.backrightlegb.roll, this.backrightlega.roll + oscillate(toRad(63.0f), toRad(73.5f), time, 2));

			// back left leg
			this.backleftlega.setAngles(
					this.backrightlega.pitch,
					-this.backrightlega.yaw,
					MathHelper.lerp(0.1f, this.backleftlega.roll, -this.backrightlega.roll)
			);
			this.backleftlegb.roll = MathHelper.lerp(0.1f, this.backleftlegb.roll, -this.backrightlegb.roll);

			// front right leg
			this.rightlega.setAngles(
					oscillate(toRad(-15.6263f), toRad(-20.2103f), time, 2),
					oscillate(toRad(22.9193f), toRad(19.0568f), time, 2),
					MathHelper.lerp(0.1f, this.rightlega.roll, oscillate(toRad(-10.6867f), toRad(-23.4281f), time, 2))
			);
			this.rightlegb.roll = MathHelper.lerp(0.1f, this.rightlegb.roll, this.rightlega.roll + oscillate(toRad(67.5f), toRad(80.0f), time, 2));

			// front left leg
			this.leftlega.setAngles(
					this.rightlega.pitch,
					-this.rightlega.yaw,
					MathHelper.lerp(0.1f, this.leftlega.roll, -this.rightlega.roll)
			);
			this.leftlegb.roll = MathHelper.lerp(0.1f, this.leftlegb.roll, -this.rightlegb.roll);
		}
		else {
			this.main.pivotY = this.mainPivotY;

			// back right leg
			this.backrightlega.setAngles(
					oscillate(toRad(18.2273f), toRad(21.3938f), time, 2),
					oscillate(toRad(-17.133f), toRad(-12.8784f), time, 2),
					this.backrightlega.roll += toRad(1.0f)
			);
			this.backrightlega.roll = MathHelper.clamp(this.backrightlega.roll, 0, 80);

			this.backrightlegb.roll += toRad(-1.0f);
			this.backrightlegb.roll = MathHelper.clamp(this.backrightlegb.roll, 0, 80);

			// back left leg
			this.backleftlega.setAngles(
					this.backrightlega.pitch,
					-this.backrightlega.yaw,
					-this.backrightlega.roll
			);
			this.backleftlegb.roll = -this.backrightlegb.roll;


			// front right leg
			this.rightlega.setAngles(
					oscillate(toRad(-15.6263f), toRad(-20.2103f), time, 2),
					oscillate(toRad(22.9193f), toRad(19.0568f), time, 2),
					this.rightlega.roll += toRad(1.0f)
			);
			this.rightlega.roll = MathHelper.clamp(this.rightlega.roll, 0, 80);

			this.rightlegb.roll += toRad(-1.0f);
			this.rightlegb.roll = MathHelper.clamp(this.rightlegb.roll, 0, 80);


			// front left leg
			this.leftlega.setAngles(
					this.rightlega.pitch,
					-this.rightlega.yaw,
					-this.rightlega.roll
			);
			this.leftlegb.roll = -this.rightlegb.roll;
		}
	}

	public static float toRad(float degrees) {
		return (float) (degrees * Math.PI / 180.0);
	}

	public static float oscillate(float a, float b, float time, float period) {
		float midpoint = (a + b) / 2;
		float amplitude = (b - a) / 2;

		return midpoint + amplitude * (float)Math.sin((2 * Math.PI / period) * time);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}