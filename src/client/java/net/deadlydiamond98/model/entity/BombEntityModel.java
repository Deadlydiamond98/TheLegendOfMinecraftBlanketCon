// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.deadlydiamond98.model.entity;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BombEntityModel<T extends AbstractBombEntity> extends EntityModel<AbstractBombEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(ZeldaCraft.MOD_ID, "bomb_entity"), "main");
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
	public void setAngles(AbstractBombEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		top.render(matrices, vertices, light, overlay, color);
		base.render(matrices, vertices, light, overlay, color);
	}
}