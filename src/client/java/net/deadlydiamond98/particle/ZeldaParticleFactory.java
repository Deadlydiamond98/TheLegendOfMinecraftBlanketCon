package net.deadlydiamond98.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class ZeldaParticleFactory {
    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Smaaash_Particle, SmaaashParticle.SmaaashParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Explosion_Particle, ExplosionParticle.ExplosionParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Snap_Particle, SnapParticle.SnapParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Magic_Fire_Particle, MagicFireParticle.MagicFireParticleFactory::new);
    }
}
