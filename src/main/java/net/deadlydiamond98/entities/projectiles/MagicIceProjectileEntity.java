package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
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

import java.util.List;

import static net.minecraft.block.CampfireBlock.LIT;
import static net.minecraft.block.FluidBlock.LEVEL;

public class MagicIceProjectileEntity extends ProjectileEntity {
    public MagicIceProjectileEntity(EntityType<MagicIceProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        this.getWorld().playSound(null, blockPos,
                this.getWorld().getBlockState(blockHitResult.getBlockPos()).getSoundGroup().getHitSound(),
                SoundCategory.BLOCKS, 1.0f, 1.0f);

//        int radius = 3;
//
//        for (int dx = -radius; dx <= radius; dx++) {
//            for (int dy = -radius; dy <= radius; dy++) {
//                for (int dz = -radius; dz <= radius; dz++) {
//                    BlockPos nearbyPos = blockPos.add(dx, dy, dz);
//                    BlockState nearbyState = this.getWorld().getBlockState(nearbyPos);
//
//                    if (nearbyState.getBlock() == Blocks.WATER) {
//                        this.getWorld().setBlockState(nearbyPos, Blocks.ICE.getDefaultState());
//                    }
//                    else if (nearbyState == Blocks.LAVA.getDefaultState().with(LEVEL, 0)) {
//                        this.getWorld().setBlockState(nearbyPos, Blocks.MAGMA_BLOCK.getDefaultState());
//                    }
//                }
//            }
//        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            ZeldaServerPackets.sendMagicIceParticlePacket((List<ServerPlayerEntity>) this.getWorld().getPlayers(), this.getX(),
                    this.getEyeY(), this.getZ());
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!entityHitResult.getEntity().equals(this.getOwner())) {
            entity.damage(entity.getDamageSources().freeze(), 3.0F);
            entity.setFireTicks(0);
            if (entity instanceof LivingEntity livingEntity) {
                StunStatusEffect statusEffect = (StunStatusEffect) ZeldaStatusEffects.Stun_Status_Effect;
                statusEffect.giveOverlay(StunStatusEffect.OverlayType.ICE);
                livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, 20, 0));
            }
        }
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient() && this.age % 2 == 0) {
            this.getWorld().addParticle(ZeldaParticles.Magic_Ice_Particle_Bullet, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            createIceParticles();
        }

        super.tick();

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                bl = true;
            } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
                }

                bl = true;
            }
        }

        if (this.touchingWater || this.isInLava()) {
            int radius = 3;
            BlockPos entityPos = this.getBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = entityPos.add(x, 0, z);
                    BlockPos surfacePos = findSurface(targetPos);
                    if (surfacePos != null) {
                        if (this.getWorld().getBlockState(surfacePos).getBlock() == Blocks.WATER) {
                            this.getWorld().setBlockState(surfacePos, Blocks.ICE.getDefaultState());
                        } else if (this.getWorld().getBlockState(surfacePos).getBlock() == Blocks.LAVA) {
                            this.getWorld().setBlockState(surfacePos, Blocks.SMOOTH_BASALT.getDefaultState());
                        }
                    }
                }
            }
            this.discard();
        }

        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onCollision(hitResult);
        }

        this.checkBlockCollision();

        Vec3d vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.setPosition(d, e, f);

        this.setPitch(0);
        this.setYaw(0);

        if (this.age >= 15) {
            this.discard();
        }
    }

    private BlockPos findSurface(BlockPos pos) {
        for (int y = 0; y <= 2; y++) {
            BlockPos targetPos = pos.up(y);
            if (this.getWorld().getBlockState(targetPos).getBlock() == Blocks.WATER ||
                    this.getWorld().getBlockState(targetPos).getBlock() == Blocks.LAVA) {
                return targetPos;
            }
        }
        return null;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            createIceParticles();
        }
        super.handleStatus(status);

    }

    private void createIceParticles() {
        ParticleEffect particleEffect = ParticleTypes.SNOWFLAKE;

        for(int i = 0; i < 8; ++i) {
            this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25));
        }
    }
}
