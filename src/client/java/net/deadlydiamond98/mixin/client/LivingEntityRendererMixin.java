package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.renderer.StunOverlay;
import net.deadlydiamond98.renderer.entity.monster.FairyRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@Shadow
	protected abstract boolean addFeature(FeatureRenderer<T, M> feature);
	@Unique
	private boolean hasStunOverlay;
	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(CallbackInfo ci) {
		this.hasStunOverlay = false;
	}


	@Inject(method = "render", at = @At("HEAD"))
	private void addCustomFeature(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (!hasStunOverlay) {
			this.addFeature(new StunOverlay<>((FeatureRendererContext<T, M>) this));
			hasStunOverlay = true;
		}
	}
}