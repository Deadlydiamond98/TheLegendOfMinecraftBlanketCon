// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.tektites.TektiteEntity;
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
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "tektite_entity"), "main");

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
		this.mainPivotY = main.pivotY + 2.5f;
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


		if (entity.isOnGround()) {
			this.main.pivotY = this.mainPivotY + oscillate(-1, 0, time, 2);

			// back right leg
			this.backrightlega.setAngles(
					oscillate(toRad(18.2273f), toRad(21.3938f), time, 2),
					oscillate(toRad(-17.133f), toRad(-12.8784f), time, 2),
					lerpValueFromPrev(entity, "backrightlega_roll", oscillate(toRad(-10.1089f), toRad(-22.2835f), time, 2))
			);
			this.backrightlegb.roll = lerpValueFromPrev(entity, "backrightlegb_roll", this.backrightlega.roll + oscillate(toRad(63.0f), toRad(73.5f), time, 2));

			// back left leg
			this.backleftlega.setAngles(
					this.backrightlega.pitch,
					-this.backrightlega.yaw,
					-entity.limbValues.getOrDefault("backrightlega_roll", this.backrightlega.roll)
			);
			this.backleftlegb.roll = -entity.limbValues.getOrDefault("backrightlegb_roll", this.backrightlegb.roll);

			// front right leg
			this.rightlega.setAngles(
					oscillate(toRad(-15.6263f), toRad(-20.2103f), time, 2),
					oscillate(toRad(22.9193f), toRad(19.0568f), time, 2),
					lerpValueFromPrev(entity, "rightlega_roll", oscillate(toRad(-10.6867f), toRad(-23.4281f), time, 2))
			);
			this.rightlegb.roll = lerpValueFromPrev(entity, "rightlegb_roll", this.rightlega.roll + oscillate(toRad(67.5f), toRad(80.0f), time, 2));

			// front left leg
			this.leftlega.setAngles(
					this.rightlega.pitch,
					-this.rightlega.yaw,
					-entity.limbValues.getOrDefault("rightlega_roll", this.rightlega.roll)
			);
			this.leftlegb.roll = -entity.limbValues.getOrDefault("rightlegb_roll", this.rightlegb.roll);
		}
		else if (!entity.isOnGround() && entity.getVelocity().length() > 0.1f) {

			this.main.pivotY = this.mainPivotY;

			// back right leg
			this.backrightlega.setAngles(
					oscillate(toRad(18.2273f), toRad(21.3938f), time, 2),
					oscillate(toRad(-17.133f), toRad(-12.8784f), time, 2),
					clampValueFromPrev(entity,"backrightlega_roll", 1.0f)
			);

			this.backrightlegb.roll = clampValueFromPrev(entity,"backrightlegb_roll", -1.0f);

			// back left leg
			this.backleftlega.setAngles(
					this.backrightlega.pitch,
					-this.backrightlega.yaw,
					-entity.limbValues.getOrDefault("backrightlega_roll", this.backrightlega.roll)
			);
			this.backleftlegb.roll = -entity.limbValues.getOrDefault("backrightlegb_roll", this.backrightlegb.roll);


			// front right leg
			this.rightlega.setAngles(
					oscillate(toRad(-15.6263f), toRad(-20.2103f), time, 2),
					oscillate(toRad(22.9193f), toRad(19.0568f), time, 2),
					this.rightlega.roll = clampValueFromPrev(entity,"rightlega_roll", 1.0f)
			);

			this.rightlegb.roll = clampValueFromPrev(entity,"rightlegb_roll", -1.0f);


			// front left leg
			this.leftlega.setAngles(
					this.rightlega.pitch,
					-this.rightlega.yaw,
					-entity.limbValues.getOrDefault("rightlega_roll", this.rightlega.roll)
			);
			this.leftlegb.roll = -entity.limbValues.getOrDefault("rightlegb_roll", this.rightlegb.roll);
		}
	}

	private float clampValueFromPrev(T entity, String key, float increment) {
		float clampRadMin = toRad(0);
		float clampRadMax = toRad(80);

		float value = entity.limbValues.getOrDefault(key, 0.0f);
		value = MathHelper.clamp(value + toRad(increment), clampRadMin, clampRadMax);
		entity.limbValues.put(key, value);
		return value;
	}

	public float lerpValueFromPrev(T entity, String key, float newValue) {
		float currentValue = entity.limbValues.getOrDefault(key, newValue);
		float lerpedValue = MathHelper.lerp(0.1f, currentValue, newValue);
		entity.limbValues.put(key, lerpedValue);
		return lerpedValue;
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
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		main.render(matrices, vertices, light, overlay, color);
	}
}