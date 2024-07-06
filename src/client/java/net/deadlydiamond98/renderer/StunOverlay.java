package net.deadlydiamond98.renderer;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class StunOverlay<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier Stun_Overlay_Texture = new Identifier(ZeldaCraft.MOD_ID, "textures/entity/stun_overlay.png");
    private static final Identifier Ice_Overlay_Texture = new Identifier("textures/block/ice.png");
    private static final RenderLayer Stun_Overlay_Layer = RenderLayer.getEntityTranslucent(Stun_Overlay_Texture);
    private static final RenderLayer Ice_Overlay_Layer = RenderLayer.getEntityTranslucent(Ice_Overlay_Texture);
    public StunOverlay(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.hasStatusEffect(ZeldaStatusEffects.Stun_Status_Effect)) {
            StunStatusEffect stunStatusEffect = (StunStatusEffect) entity.getStatusEffect(ZeldaStatusEffects.Stun_Status_Effect).getEffectType();
            if (stunStatusEffect.getOverlay() != StunStatusEffect.OverlayType.CLOCK) {
                if (stunStatusEffect.getOverlay() == StunStatusEffect.OverlayType.ICE) {
                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(Ice_Overlay_Layer);
                    if (vertexConsumer != null) {
                        this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.75F);
                    }
                }
                else {
                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(Stun_Overlay_Layer);
                    if (vertexConsumer != null) {
                        this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.5F);
                    }
                }
            }
        }
    }
}
