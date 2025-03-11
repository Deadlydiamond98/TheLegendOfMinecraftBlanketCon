package net.deadlydiamond98.particle;

import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaParticles {
    public static SimpleParticleType Smaaash_Particle;
    public static SimpleParticleType Explosion_Particle;
    public static SimpleParticleType Snap_Particle;
    public static SimpleParticleType Magic_Fire_Particle;
    public static SimpleParticleType Magic_Ice_Particle;
    public static SimpleParticleType Magic_Ice_Particle_Bullet;
    public static SimpleParticleType Beam_Particle;
    public static SimpleParticleType Meteor_Shower_Rain_Particle;
    public static SimpleParticleType Item_Baseball;


    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(ZeldaCraft.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        Smaaash_Particle = registerParticle("smaaash_particle", FabricParticleTypes.simple(true));
        Snap_Particle = registerParticle("snap_particle", FabricParticleTypes.simple(true));
        Explosion_Particle = registerParticle("bomb_particle", FabricParticleTypes.simple(true));
        Magic_Fire_Particle = registerParticle("magic_fire_particle", FabricParticleTypes.simple(true));
        Magic_Ice_Particle = registerParticle("magic_ice_particle", FabricParticleTypes.simple(true));
        Magic_Ice_Particle_Bullet = registerParticle("magic_ice_particle_bullet", FabricParticleTypes.simple(true));
        Beam_Particle = registerParticle("beam_particle", FabricParticleTypes.simple(true));
        Meteor_Shower_Rain_Particle = registerParticle("meteor_shower_rain_particle", FabricParticleTypes.simple(false));
        ZeldaCraft.LOGGER.info("Registering Particles for " + ZeldaCraft.MOD_ID);
    }
}
