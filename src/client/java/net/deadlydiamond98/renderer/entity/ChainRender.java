package net.deadlydiamond98.renderer.entity;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public interface ChainRender {

    default Identifier getChainTexture() {
        return new Identifier("minecraft", "textures/block/chain.png");
    }

    default void renderChain(Vec3d end, MatrixStack matrices, VertexConsumer buffer, int light, int overlayCoords) {
        double distance = end.horizontalLength();
        float chainWidth = 3F / 16F;
        float chainOffset = chainWidth * -0.5F;
        float chainLength = (float) end.length();

        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) (Math.atan2(end.x, end.z) * (double) (180F / (float) Math.PI))));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (-(Math.atan2(end.y, distance) * (double) (180F / (float) Math.PI))) - 90.0F));

        matrices.translate(0, -chainLength, 0);

        MatrixStack.Entry entry = matrices.peek();

        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();

        // x links

        buffer.vertex(matrix4f, chainOffset, 0, 0).color(255, 255, 255, 255)
                .texture(0, chainLength)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainWidth + chainOffset, 0, 0).color(255, 255, 255, 255)
                .texture(chainWidth, chainLength)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainWidth + chainOffset, chainLength, 0).color(255, 255, 255, 255)
                .texture(chainWidth, 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, chainOffset, chainLength, 0).color(255, 255, 255, 255)
                .texture(0, 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();

        // z links

        buffer.vertex(matrix4f, 0, 0, chainOffset).color(255, 255, 255, 255).
                texture(chainWidth, chainLength + 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, 0, chainWidth + chainOffset).color(255, 255, 255, 255)
                .texture(chainWidth * 2, chainLength + 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, chainLength, chainWidth + chainOffset).color(255, 255, 255, 255)
                .texture(chainWidth * 2, 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
        buffer.vertex(matrix4f, 0, chainLength, chainOffset).color(255, 255, 255, 255)
                .texture(chainWidth, 0)
                .overlay(overlayCoords).light(light)
                .normal(matrix3f, 0.0F, -1.0F, 0.0F).next();

        matrices.pop();
    }
}
