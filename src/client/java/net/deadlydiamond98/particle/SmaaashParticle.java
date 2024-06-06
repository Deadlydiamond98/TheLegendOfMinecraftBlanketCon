package net.deadlydiamond98.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SmaaashParticle extends SpriteBillboardParticle {
    private int tickCounter = 0;
    private final SpriteProvider spriteProvider;
    protected SmaaashParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityY = 0.125;
        this.maxAge = 30;
        this.setSprite(spriteProvider.getSprite(0, 3));
        this.scale = 1.0F;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.gravityStrength = 0.25F;
    }

    private void setSpriteForTick() {
        int frameIndex = tickCounter % 4;
        this.setSprite(spriteProvider.getSprite(frameIndex, 3));
    }
    @Override
    public void tick() {
        if (scale > 0) {
            scale -= 0.03F;
        }

        tickCounter++;
        setSpriteForTick();

        super.tick();

    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class SmaaashParticleFactory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public SmaaashParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public SmaaashParticle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SmaaashParticle(world, x, y, z, this.spriteProvider);
        }
    }
}

