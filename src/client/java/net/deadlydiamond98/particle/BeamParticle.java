package net.deadlydiamond98.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class BeamParticle extends SpriteBillboardParticle {
    private int tickCounter = 0;
    private final SpriteProvider spriteProvider;
    protected BeamParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.maxAge = 20;
        this.setSprite(spriteProvider.getSprite(0, 8));
        this.scale = 2.0F;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.gravityStrength = 0.0F;
    }

    private void setSpriteForTick() {
        int frameIndex = tickCounter % 8;
        this.setSprite(spriteProvider.getSprite(frameIndex, 8));
    }
    @Override
    public void tick() {
        if (scale > 0) {
            scale -= 0.2F;
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
    public static class BeamParticleFactory implements ParticleFactory<DefaultParticleType> {

        private final SpriteProvider spriteProvider;

        public BeamParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public BeamParticle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new BeamParticle(world, x, y, z, this.spriteProvider);
        }
    }
}

