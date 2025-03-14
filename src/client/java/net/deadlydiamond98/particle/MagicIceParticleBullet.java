package net.deadlydiamond98.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class MagicIceParticleBullet extends SpriteBillboardParticle {
    private int tickCounter = 0;
    private final SpriteProvider spriteProvider;
    protected MagicIceParticleBullet(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.maxAge = 20;
        this.setSprite(spriteProvider.getSprite(0, 15));
        this.scale = 2.0F;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.gravityStrength = 0.0F;
    }

    private void setSpriteForTick() {
        int frameIndex = tickCounter % 14;
        this.setSprite(spriteProvider.getSprite(frameIndex, 14));
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
    public static class MagicIceBulletParticleFactory implements ParticleFactory<SimpleParticleType> {

        private final SpriteProvider spriteProvider;

        public MagicIceBulletParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public MagicIceParticleBullet createParticle(SimpleParticleType type, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new MagicIceParticleBullet(world, x, y, z, this.spriteProvider);
        }
    }
}

