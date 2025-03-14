package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.octoroks.OctorokEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class OctorokEntityModel extends SinglePartEntityModel<OctorokEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "octorok_entity"), "main");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tentaclefl;
	private final ModelPart tentaclefr;
	private final ModelPart tentaclebl;
	private final ModelPart tentaclebr;

	public OctorokEntityModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.tentaclefl = this.body.getChild("tentaclefl");
        this.tentaclefr = this.body.getChild("tentaclefr");
        this.tentaclebl = this.body.getChild("tentaclebl");
        this.tentaclebr = this.body.getChild("tentaclebr");
    }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, 22.0F, -5.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -11.75F, -5.0F, 10.0F, 11.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 5).cuboid(-1.5F, -4.75F, -6.25F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.0F, -5.25F, -7.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 1.0F, 5.0F));

		ModelPartData tentaclefl = main.addChild("tentaclefl", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData cube_r1 = tentaclefl.addChild("cube_r1", ModelPartBuilder.create().uv(1, 21).mirrored().cuboid(-5.0F, -1.0F, -1.5F, 8.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3999F, -0.6956F, -0.583F));

		ModelPartData tentaclefr = main.addChild("tentaclefr", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, -1.0F, 0.0F));

		ModelPartData cube_r2 = tentaclefr.addChild("cube_r2", ModelPartBuilder.create().uv(1, 21).cuboid(-3.0F, -1.0F, -1.5F, 8.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3999F, 0.6956F, 0.583F));

		ModelPartData tentaclebl = main.addChild("tentaclebl", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 10.0F));

		ModelPartData cube_r3 = tentaclebl.addChild("cube_r3", ModelPartBuilder.create().uv(1, 21).mirrored().cuboid(-5.0F, -1.0F, -1.5F, 8.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3999F, 0.6956F, -0.583F));

		ModelPartData tentaclebr = main.addChild("tentaclebr", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, -1.0F, 10.0F));

		ModelPartData cube_r4 = tentaclebr.addChild("cube_r4", ModelPartBuilder.create().uv(1, 21).cuboid(-3.0F, -1.0F, -1.5F, 8.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3999F, -0.6956F, 0.583F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(OctorokEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = netHeadYaw * 0.009453292F;

		this.tentaclebl.yaw = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5f;
		this.tentaclebr.yaw = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5f;
		this.tentaclefl.yaw = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5f;
		this.tentaclefr.yaw = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5f;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		root.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}