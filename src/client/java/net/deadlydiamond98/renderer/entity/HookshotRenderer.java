package net.deadlydiamond98.renderer.entity;

import net.deadlydiamond98.entities.projectiles.HookshotEntity;
import net.deadlydiamond98.items.other.HookshotItem;
import net.deadlydiamond98.model.entity.HookshotHeadModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookshotRenderer extends EntityRenderer<HookshotEntity> implements ChainRender{
    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/block/anvil.png");
    private final HookshotHeadModel<HookshotEntity> entityModel;
    public HookshotRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new HookshotHeadModel<>(ctx.getPart(HookshotHeadModel.LAYER_LOCATION));
    }

    @Override
    public void render(HookshotEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        PlayerEntity player = (PlayerEntity) entity.getOwner();

        if (player != null) {
            int brightness = 15728880;

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw() + 180));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
            matrices.translate(0, -0.75f, 0);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
            this.entityModel.render(matrices, vertexConsumer, brightness, OverlayTexture.DEFAULT_UV, 0xffffff);
            matrices.pop();

            //Chain Rendering
            int handSide = (player.getMainArm() == Arm.RIGHT) ? 1 : -1;

            float bodyYawRadians = MathHelper.lerp(tickDelta, player.prevBodyYaw, player.bodyYaw) * 0.017453292f;

            double sinYaw = Math.sin(bodyYawRadians);
            double cosYaw = Math.cos(bodyYawRadians);
            double forwardOffset = 0.6;
            double sideOffset = handSide * 0.35;
            double downOffset;

            double startX = MathHelper.lerp(tickDelta, player.prevX, player.getX());
            double startY = MathHelper.lerp(tickDelta, player.prevY, player.getY()) + player.getStandingEyeHeight();
            double startZ = MathHelper.lerp(tickDelta, player.prevZ, player.getZ());

            if ((this.dispatcher.gameOptions == null || this.dispatcher.gameOptions.getPerspective().isFirstPerson()) && player == MinecraftClient.getInstance().player) {

                downOffset = -0.625;

                float h = player.getHandSwingProgress(tickDelta);
                float k = MathHelper.sin(MathHelper.sqrt(h) * (float) Math.PI);

                double fov = 960.0 / (double)this.dispatcher.gameOptions.getFov().getValue();
                Vec3d vec3d = this.dispatcher.camera.getProjection().getPosition((float)handSide * 0.75f, (float) downOffset);

                vec3d = vec3d.multiply(fov - 3.5);

                vec3d = vec3d.rotateY(-k * 0.5f);
                vec3d = vec3d.rotateX(k * 0.7f);

                startX += vec3d.x;
                startY += vec3d.y;
                startZ += vec3d.z;
            }
            else {
                downOffset = 0.725;

                if (player.isInSneakingPose()) {
                    downOffset += 0.1875;
                    forwardOffset -= 0.15;
                }

                startX = MathHelper.lerp(tickDelta, player.prevX, player.getX())
                        - cosYaw * sideOffset
                        - sinYaw * forwardOffset;
                startY = player.prevY + (double) player.getStandingEyeHeight()
                        + (player.getY() - player.prevY) * tickDelta
                        - downOffset;
                startZ = MathHelper.lerp(tickDelta, player.prevZ, player.getZ())
                        - sinYaw * sideOffset
                        + cosYaw * forwardOffset;
            }

            double endX = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
            double endY = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) + 0.25;
            double endZ = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());

            Vec3d headPos = entity.getLerpedPos(tickDelta).add(0, 0.25, 0);


            double chainDx = startX - headPos.x;
            double chainDy = startY - headPos.y;
            double chainDz = startZ - headPos.z;

            matrices.push();
            matrices.translate(
                    endX - MathHelper.lerp(tickDelta, entity.prevX, entity.getX()),
                    endY - MathHelper.lerp(tickDelta, entity.prevY, entity.getY()),
                    endZ - MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ())
            );

            VertexConsumer chainConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getChainTexture()));

            renderChain(new Vec3d(chainDx, chainDy, chainDz), matrices, chainConsumer, 15728880, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        }
    }

    @Override
    public boolean shouldRender(HookshotEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }


    @Override
    public Identifier getTexture(HookshotEntity entity) {
        return TEXTURE;
    }
}
