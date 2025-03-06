package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.renderer.StunOverlay;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaLivingEntityData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@Unique
	private float lastLimbSwing;
	@Unique
	private float lastLimbSwingAmount;
	@Unique
	private float lastAgeInTicks;
	@Unique
	private float lastNetHeadYaw;
	@Unique
	private float lastHeadPitch;
	@Unique
	private boolean freezeAnimations;


	@Shadow
	protected abstract boolean addFeature(FeatureRenderer<T, M> feature);

	@Shadow protected M model;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(CallbackInfo ci) {
		this.addFeature(new StunOverlay<>((FeatureRendererContext<T, M>) this));
		this.lastLimbSwing = 0;
		this.lastLimbSwingAmount = 0;
		this.lastAgeInTicks = 0;
		this.lastNetHeadYaw = 0;
		this.lastHeadPitch = 0;
		this.freezeAnimations = false;
	}

	@Inject(method = "shouldFlipUpsideDown", at = @At(value = "HEAD"), cancellable = true)
	private static void flip(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		String string = Formatting.strip(entity.getName().getString());
		boolean dinnerbone = (entity instanceof PlayerEntity || entity.hasCustomName()) &&
				("Dinnerbone".equals(string) || "Grumm".equals(string)) &&
				(!(entity instanceof PlayerEntity) || ((PlayerEntity) entity).isPartVisible(PlayerModelPart.CAPE));

		cir.setReturnValue(dinnerbone ^ ((ZeldaLivingEntityData) entity).flipped());
	}

	@Inject(
			method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;",
					shift = At.Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void setupAnimations(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci, float h, float j, float k, float m, float l, float n, float o) {
		if (livingEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
			this.model.setAngles(livingEntity, this.lastLimbSwing, this.lastLimbSwingAmount, this.lastAgeInTicks, this.lastNetHeadYaw, this.lastHeadPitch);
			this.model.animateModel(livingEntity, this.lastLimbSwing, this.lastLimbSwingAmount, this.lastAgeInTicks);
			this.freezeAnimations = true;
		}
		if (!livingEntity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
			this.lastLimbSwing = o;
			this.lastLimbSwingAmount = n;
			this.lastAgeInTicks = l;
			this.lastNetHeadYaw = k;
			this.lastHeadPitch = m;
		}
	}
}