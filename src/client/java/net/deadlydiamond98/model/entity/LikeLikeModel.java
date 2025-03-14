package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.LikeLikeEntity;
import net.deadlydiamond98.entities.monsters.octoroks.OctorokEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LikeLikeModel extends SinglePartEntityModel<LikeLikeEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "like_like_entity"), "main");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart top;
	private final ModelPart middle;
	private final ModelPart bottom;

	public LikeLikeModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.top = body.getChild("top");
		this.middle = body.getChild("middle");
		this.bottom = body.getChild("bottom");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData top = body.addChild("top", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-13.0F, -16.0F, 3.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 0).cuboid(-14.0F, -15.0F, 2.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 8.0F, -8.0F));

		ModelPartData middle = body.addChild("middle", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bottom = body.addChild("bottom", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(LikeLikeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		body.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}