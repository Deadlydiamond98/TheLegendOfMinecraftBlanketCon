package net.deadlydiamond98.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class SwordBeamEntity extends ProjectileEntity {
    public SwordBeamEntity(EntityType<SwordBeamEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().sendMessage(Text.literal("testing touch"));
        DustParticleEffect dustParticleEffect = new DustParticleEffect(new Vec3d(1.0f, 1.0f, 1.0f).toVector3f(), 1.0f);
        entityHitResult.getEntity().getWorld().addParticle(dustParticleEffect, this.getX(), this.getY(), this.getZ(), 0, 0 , 0);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);

        this.setPitch(0);
        this.setYaw(0);

        if (this.age >= 15) {
            this.discard();
        }
        float[] rgb = pickColor(random.nextBetween(0, 3));
        DustParticleEffect dustParticleEffect = new DustParticleEffect(new Vec3d(rgb[0], rgb[1], rgb[2]).toVector3f(), 1.0f);
        this.getWorld().addParticle(dustParticleEffect, this.getX(), this.getY(), this.getZ(), 0, 0 , 0);
    }

    private static float[] pickColor(int hexCode) {

        String hex;
        switch (hexCode) {
            case 0: hex = "#f88008"; break;
            case 1: hex = "#80b0e0"; break;
            case 2: hex = "#c0c0c1"; break;
            default: hex = "#f8b808"; break;
        }

        //way too dumb to figure out this, thank you internet! Probably an easier way but I'm too sleep-deprived to care
        int color = Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
        float red = ((color >> 16) & 0xFF) / 255.0f;
        float green = ((color >> 8) & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        return new float[]{red, green, blue};
    }

    @Override
    protected void initDataTracker() {
    }
}
