package net.deadlydiamond98.entities;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.List;

public class SwordBeamEntity extends ProjectileEntity {
    public SwordBeamEntity(EntityType<SwordBeamEntity> entityType, World world) {
        super(entityType, world);
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
        entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().magic(), 2.0F);
    }

    @Override
    public void tick() {
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
