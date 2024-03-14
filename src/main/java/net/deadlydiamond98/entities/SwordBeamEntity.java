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
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SwordBeamEntity extends ProjectileEntity {
    public SwordBeamEntity(EntityType<SwordBeamEntity> entityType, World world) {
        super(entityType, world);

        if(this.getOwner() != null) {
            this.prevYaw = this.getOwner().prevYaw;
            if(this.getOwner() instanceof PlayerEntity player) {
                this.prevYaw = player.prevHeadYaw;
            }

            this.prevPitch = this.getOwner().prevPitch;
            this.setPitch(this.getOwner().prevPitch);
            this.setYaw(this.getOwner().prevYaw);
        }
    }


    @Override
    protected void onBlockCollision(BlockState state) {
        this.discard();
        super.onBlockCollision(state);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().sendMessage(Text.literal("testing touch"));
        super.onEntityHit(entityHitResult);
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
        this.updateRotation();
        this.setVelocity(vec3d.multiply(0.9900000095367432));
        this.setPosition(d, e, f);
        this.age++;
        if (this.age >= 6000) {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker() {
    }
}
