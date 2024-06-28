package net.deadlydiamond98.mixin.client;

import net.deadlydiamond98.renderer.FairyCompanionRenderer;
import net.deadlydiamond98.renderer.transformations.FairyPlayerRenderer;
import net.deadlydiamond98.util.NaviAccessor;
import net.deadlydiamond98.util.NaviState;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin implements NaviAccessor {
    @Unique
    private final Map<UUID, NaviState> naviStateMap = new HashMap<>();
    @Unique
    private EntityRendererFactory.Context context;
    @Unique
    private FairyPlayerRenderer<AbstractClientPlayerEntity> fairyRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
        this.fairyRenderer = new FairyPlayerRenderer<>(context);
        this.context = context;
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
                          VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {

        naviStateMap.computeIfAbsent(player.getUuid(), uuid -> new NaviState(player.getPos(), context));

        NaviState naviState = naviStateMap.get(player.getUuid());

        if (((OtherPlayerData) player).getFairyFriend()) {
            naviState.renderNavi(player, yaw, tickDelta, matrices, vertexConsumers, light, true);
        }

        if (((OtherPlayerData) player).isFairy()) {
            matrices.push();
            this.fairyRenderer.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
            info.cancel();
        }
    }
    @Override
    public NaviState getNaviState(UUID playerUUID) {
        return this.naviStateMap.get(playerUUID);
    }
    @Override
    public void setNaviState(UUID playerUUID, NaviState naviState) {
        this.naviStateMap.put(playerUUID, naviState);
    }

    @Override
    public EntityRendererFactory.Context getContext() {
        return this.context;
    }
}
