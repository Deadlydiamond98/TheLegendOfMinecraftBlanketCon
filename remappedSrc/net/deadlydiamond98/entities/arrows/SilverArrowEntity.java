package net.deadlydiamond98.entities.arrows;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SilverArrowEntity extends PersistentProjectileEntity {
    private int duration = 200;
    public SilverArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SilverArrowEntity(World world, LivingEntity owner) {
        super(ZeldaEntities.Silver_Arrow, owner, world);
    }

    public SilverArrowEntity(World world, double x, double y, double z) {
        super(ZeldaEntities.Silver_Arrow, x, y, z, world);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (target instanceof Monster) {
            StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.WEAKNESS, this.duration, 0);
            target.addStatusEffect(statusEffectInstance, this.getEffectCause());
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof Monster) {
            this.setDamage(this.getDamage() + 2);
        }
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ZeldaItems.Silver_Arrow);
    }
    @Override

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Duration")) {
            this.duration = nbt.getInt("Duration");
        }

    }
    @Override

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Duration", this.duration);
    }
}
