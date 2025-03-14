package net.deadlydiamond98.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class SnapParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    protected SnapParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y + 1, z);
        this.maxAge = 20;
        this.setSpriteForAge(spriteProvider);
        this.scale = 2.0F;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.gravityStrength = 0.0F;
    }
    @Override
    public void tick() {
        if (scale > 0) {
            scale -= 0.2F;
        }
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
    public static class SnapParticleFactory implements ParticleFactory<SimpleParticleType> {

        private final SpriteProvider spriteProvider;

        public SnapParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public SnapParticle createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SnapParticle(world, x, y, z, this.spriteProvider);
        }
    }
}

