package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DekuNutEntity extends ThrownItemEntity {
    public DekuNutEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DekuNutEntity(World world, PlayerEntity user) {
        super(ZeldaEntities.Deku_Nut_Entity, user, world);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getWorld().isClient) {
            stun();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            stun();
        }

    }
    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.Deku_Nut;
    }

    private void stun() {
        if (!this.getWorld().isClient()) {
            int radius = 2;
            Box box = new Box(this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                    this.getX() + radius, this.getY() + radius, this.getZ() + radius);

            List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, box, entity -> true);

            for (LivingEntity entity : entities) {
                entity.addStatusEffect(new StatusEffectInstance(ZeldaStatusEffects.Stun_Status_Effect, 50, 0));
            }
            this.getWorld().getPlayers().forEach(player -> {
                ZeldaServerPackets.sendParticlePacket((ServerPlayerEntity) player, this.getX(),
                        this.getY() - 0.5, this.getZ(), 2);
            });
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 2.0f, 2.0f);
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 2.0f, 1.0f);
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 2.0f, 0.5f);
            this.discard();
        }
    }
}
