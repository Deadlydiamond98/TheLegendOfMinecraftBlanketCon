package net.deadlydiamond98.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.EndRodParticle;

public class ZeldaParticleFactory {
    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Smaaash_Particle, SmaaashParticle.SmaaashParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Explosion_Particle, ExplosionParticle.ExplosionParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Snap_Particle, SnapParticle.SnapParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Magic_Fire_Particle, MagicFireParticle.MagicFireParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Magic_Ice_Particle, MagicIceParticle.MagicIceParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Magic_Ice_Particle_Bullet, MagicIceParticleBullet.MagicIceBulletParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Beam_Particle, BeamParticle.BeamParticleFactory::new);
        ParticleFactoryRegistry.getInstance().register(ZeldaParticles.Meteor_Shower_Rain_Particle, MeteorShowerRainParticle.Factory::new);
    }
}
