package net.deadlydiamond98.util;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public interface NaviAccessor {
    NaviState getNaviState(UUID playerUUID);
    void setNaviState(UUID playerUUID, NaviState naviState);
    EntityRendererFactory.Context getContext();

}