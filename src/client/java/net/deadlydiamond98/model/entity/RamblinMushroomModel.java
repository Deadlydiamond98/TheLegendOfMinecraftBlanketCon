// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.RamblinMushroomEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RamblinMushroomModel extends SinglePartEntityModel<RamblinMushroomEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new Identifier(ZeldaCraft.MOD_ID, "ramblin_mushroom_entity"), "main");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart mushroom;
	private final ModelPart legl;
	private final ModelPart legr;

	public RamblinMushroomModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.mushroom = body.getChild("mushroom");
		this.legl = body.getChild("legl");
		this.legr = body.getChild("legr");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData mushroom = body.addChild("mushroom", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-5.0F, -6.0F, -5.0F, 10.0F, 3.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData legl = body.addChild("legl", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F, -0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 7).cuboid(-0.5F, 1.75F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.75F, -2.75F, 0.0F));

		ModelPartData legr = body.addChild("legr", ModelPartBuilder.create().uv(4, 13).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 6).cuboid(-0.5F, 2.0F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.75F, -3.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(RamblinMushroomEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}