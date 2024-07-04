package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BeamEntity extends ProjectileEntity {

    private static final TrackedData<Vector3f> initialPos;
    private final List<Vec3d> startPos = new ArrayList<>();
    private int deathTimer;
    private boolean slatedForRemoval;
    public BeamEntity(EntityType<BeamEntity> entityType, World world) {
        super(entityType, world);
    }

    public BeamEntity(World world, double x, double y, double z, double vx, double vy, double vz, Entity user) {
        this(ZeldaEntities.Beam_Entity, world);
        this.setVelocity(vx, vy, vz);
        this.setPos(x, y, z);
        this.setOwner(user);
        this.startPos.add(this.getPos());
        setInitPos(startPos.get(0).toVector3f());
        this.slatedForRemoval = false;
        this.deathTimer = 0;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(initialPos, this.getPos().toVector3f());
    }

    static {
        initialPos = DataTracker.registerData(BeamEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    }

    public Vector3f getInitPos() {
        return this.dataTracker.get(initialPos);
    }

    public void setInitPos(Vector3f vec3f) {
        this.dataTracker.set(initialPos, vec3f);
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
        if (this.getOwner() instanceof PlayerEntity user) {
            entity.damage(entity.getDamageSources().playerAttack(user), 2.0F);
        } else if (this.getOwner() instanceof LivingEntity attacker) {
            entity.damage(entity.getDamageSources().mobAttack(attacker), 2.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {

            if (this.getOwner() != null) {
                HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);

                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.onCollision(hitResult);
                }

                this.checkBlockCollision();

                if (!this.slatedForRemoval) {
                    if (this.startPos.size() != 0) {
                        this.startPos.add(this.getPos().lerp(this.startPos.get(0), 0.1));
                    }
                    else {
                        this.startPos.add(this.getPos());
                    }
                }

                if ((this.startPos.size() > 5 || this.slatedForRemoval) && this.startPos.size() > 1) {
                    this.startPos.remove(0);
                    this.setInitPos(this.startPos.get(0).toVector3f());
                }
            }
            else {
                this.discard();
            }
        }

        if (this.slatedForRemoval) {
            this.deathTimer++;
        }

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);

        this.setPitch((float) (Math.atan2(this.getVelocity().y, Math.sqrt(this.getVelocity().x * this.getVelocity().x + this.getVelocity().z * this.getVelocity().z)) * (180 / Math.PI)));
        this.setYaw((float) (Math.atan2(this.getVelocity().z, this.getVelocity().x) * (180 / Math.PI)));

        if (this.age >= 60) {
            this.discard();
        }
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

    @Override
    public void remove(RemovalReason reason) {
        if (reason != RemovalReason.DISCARDED || this.startPos.size() <= 1 || this.deathTimer > 10) {
            super.remove(reason);
        }
        this.slatedForRemoval = true;
        this.setVelocity(this.getVelocity().multiply(0.1));
    }
}
