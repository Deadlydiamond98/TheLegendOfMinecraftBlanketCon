package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BeamEntity extends ProjectileEntity {
    private static final TrackedData<Boolean> IS_LEADER;
    private int beams;

    private Vec3d startPos;


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(IS_LEADER, false);
    }

    static {
        IS_LEADER = DataTracker.registerData(BeamEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    public BeamEntity(EntityType<BeamEntity> entityType, World world) {
        super(entityType, world);
    }

    public BeamEntity(World world, double x, double y, double z, double vx, double vy, double vz, Entity user, Vec3d startPos, boolean leader, int beams) {
        this(ZeldaEntities.Beam_Entity, world);
        this.setVelocity(vx, vy, vz);
        this.setPos(x, y, z);
        this.setOwner(user);
        this.startPos = startPos;
        this.hasLeader(leader);
        this.beams = beams;
    }

    public Boolean getLeader() {
        return this.dataTracker.get(IS_LEADER);
    }

    private void hasLeader(Boolean leader) {
        this.dataTracker.set(IS_LEADER, leader);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.getWorld().playSound(null, blockPos,
                this.getWorld().getBlockState(blockHitResult.getBlockPos()).getSoundGroup().getHitSound(),
                SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!entityHitResult.getEntity().equals(this.getOwner())) {
            if (this.getOwner() instanceof PlayerEntity user) {
                entity.damage(entity.getDamageSources().indirectMagic(user, user), 6.0F);
            } else if (this.getOwner() instanceof LivingEntity attacker) {
                entity.damage(entity.getDamageSources().indirectMagic(attacker, attacker), 3.0F);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3d vec3d = this.getVelocity();

        if (!this.getWorld().isClient()) {

            if (this.getOwner() != null) {
                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.onCollision(hitResult);
                }

                this.checkBlockCollision();

                if (this.age == 1 && this.beams > 0) {
                    this.getWorld().spawnEntity(new BeamEntity(this.getWorld(), this.startPos.getX(), this.startPos.getY(), this.startPos.getZ(),
                            vec3d.x * 1, vec3d.y * 1, vec3d.z * 1, this.getOwner(), this.startPos, false, this.beams - 1));
                }
            }
            else {
                this.discard();
            }
        }
        else {
            this.updateTrackedPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), 20);
            if (this.age % 2 == 0 && this.getLeader() && this.age > 5) {
                this.getWorld().addParticle(ZeldaParticles.Beam_Particle, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }

        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);

        this.setPitch((float) (Math.atan2(this.getVelocity().y, Math.sqrt(this.getVelocity().x * this.getVelocity().x + this.getVelocity().z * this.getVelocity().z)) * (180 / Math.PI)));
        this.setYaw((float) (Math.atan2(this.getVelocity().z, this.getVelocity().x) * (180 / Math.PI)));

        if (this.age >= 60) {
            this.discard();
        }
        if (this.age > 5) {
            float[] rgb = pickColor(random.nextBetween(0, 2));
            DustParticleEffect dustParticleEffect = new DustParticleEffect(new Vec3d(rgb[0], rgb[1], rgb[2]).toVector3f(), 1.0f);
            this.getWorld().addParticle(dustParticleEffect, this.getX(), this.getY(), this.getZ(), 0, 0 , 0);
        }
    }

    private static float[] pickColor(int hexCode) {

        String hex = switch (hexCode) {
            case 0 -> "#fd2d3c";
            case 1 -> "#fb802e";
            default -> "#ffad29";
        };

        //way too dumb to figure out this, thank you internet! Probably an easier way but I'm too sleep-deprived to care
        int color = Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
        float red = ((color >> 16) & 0xFF) / 255.0f;
        float green = ((color >> 8) & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        return new float[]{red, green, blue};
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = ParticleTypes.CRIT;

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(),
                        Math.floor(Math.random() * (2 + 2 + 1) - 2),
                        Math.floor(Math.random() * (2 + 2 + 1) - 2),
                        Math.floor(Math.random() * (2 + 2 + 1) - 2));
            }
        }
        super.handleStatus(status);

    }
}
