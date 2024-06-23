package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.custom.Swords.CrackedBat;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseBallEntity extends ThrownItemEntity {
    public BaseBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BaseBallEntity(World world, PlayerEntity user) {
        super(ZeldaEntities.Baseball_Entity, user, world);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (this.getVelocity().length() > 0.3F && !this.getWorld().isClient) {
            entity.damage(this.getDamageSources().thrown(this, this.getOwner()), (float) (2.0F * getVelocity().length()));
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
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity sourceEntity = source.getAttacker();
        if (sourceEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) sourceEntity;
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() instanceof CrackedBat) {
                Vec3d lookVec = player.getRotationVec(1.0f);
                double launchPower = 0.6 * sourceEntity.distanceTo(this);
                double upwardBoost = 0.5;
                this.setVelocity(lookVec.x * launchPower, lookVec.y * launchPower + upwardBoost, lookVec.z * launchPower);
                return true;
            }
        }
        return super.damage(source, amount);
    }
    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Baseball;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }
}
