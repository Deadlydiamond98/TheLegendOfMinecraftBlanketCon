package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.renderer.entity.monster.FairyRenderer;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin{

    private static final Identifier FAIRY_TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/blue_fairy.png");
    private FairyCompanionRenderer<AbstractClientPlayerEntity> fairyRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
        fairyRenderer = new FairyCompanionRenderer<>(context);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (((OtherPlayerData) player).isFairy()) {
            matrices.push();
            fairyRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
            info.cancel();
        }
    }

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void onGetTexture(AbstractClientPlayerEntity player, CallbackInfoReturnable<Identifier> cir) {
        if (((OtherPlayerData) player).isFairy()) {
            cir.setReturnValue(FAIRY_TEXTURE);
        }
    }
}
