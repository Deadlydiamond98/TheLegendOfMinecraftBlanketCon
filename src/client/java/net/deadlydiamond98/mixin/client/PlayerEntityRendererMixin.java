package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.renderer.transformations.FairyPlayerRenderer;
import net.deadlydiamond98.util.interfaces.ZeldaPlayerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    // this mixin is used to apply custom models to the player for some accessories
    @Unique
    private FairyPlayerRenderer<AbstractClientPlayerEntity> fairyRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
        this.fairyRenderer = new FairyPlayerRenderer<>(context);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
                          VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {

        if (((ZeldaPlayerData) player).isFairy()) {
            matrices.push();
            this.fairyRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
            info.cancel();
        }
    }
}
