package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.blocks.BombFlower;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static net.deadlydiamond98.blocks.BombFlower.AGE;
import static net.minecraft.block.CampfireBlock.LIT;

public class MagicFireProjectileEntity extends ProjectileEntity {
    public MagicFireProjectileEntity(EntityType<MagicFireProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        this.getWorld().playSound(null, blockPos,
                this.getWorld().getBlockState(blockHitResult.getBlockPos()).getSoundGroup().getHitSound(),
                SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (blockState.getBlock() instanceof CampfireBlock campfireBlock && !campfireBlock.isLitCampfire(blockState)) {
            this.getWorld().setBlockState(blockPos, blockState.with(LIT, true));
        }
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
        entity.setOnFireFor(4);
        entity.damage(entity.getDamageSources().magic(), 2.0F);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient() && this.age % 2 == 0) {
            this.getWorld().addParticle(ZeldaParticles.Magic_Fire_Particle, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            createFireParticles();
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

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            createFireParticles();
        }
        super.handleStatus(status);

    }

    private void createFireParticles() {
        ParticleEffect particleEffect = ParticleTypes.FLAME;

        for(int i = 0; i < 8; ++i) {
            this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25),
                    Math.floor(Math.random() * (0.25 + 0.25 + 1) - 0.25));
        }
    }
}
