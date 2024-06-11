package net.deadlydiamond98.entities.projectiles;

import net.deadlydiamond98.blocks.BombFlower;
import net.deadlydiamond98.blocks.SecretStone;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.entities.BombEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.items.Swords.CrackedBat;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static net.deadlydiamond98.blocks.BombFlower.AGE;

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
        int radius = 3;
        Box box = new Box(this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                this.getX() + radius, this.getY() + radius, this.getZ() + radius);

        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, box, entity -> true);

        for (LivingEntity entity : entities) {
            entity.addStatusEffect(new StatusEffectInstance(ZeldaStatusEffects.Stun_Status_Effect, 100, 4));
        }

        this.discard();
    }
}
