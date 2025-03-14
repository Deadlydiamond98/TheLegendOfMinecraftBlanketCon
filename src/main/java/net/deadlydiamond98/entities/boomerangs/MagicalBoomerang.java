package net.deadlydiamond98.entities.boomerangs;

import net.deadlydiamond98.util.EnchantmentUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MagicalBoomerang extends BaseBoomerangProjectile {

    public MagicalBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }
    public MagicalBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world, PlayerEntity player, ItemStack boomerangItem, Hand hand) {
        super(entityType, world, player, boomerangItem, 20, 8, 0.6f, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            Box expandedBox = this.getBoundingBox().expand(0.5);
            EntityType<?> entityType = EntityType.get("healpgood:health").orElse(null);
            List<Entity> entities = this.getWorld().getOtherEntities(this, expandedBox, entity ->
                    (entity instanceof ItemEntity) || (entity instanceof ExperienceOrbEntity) || (entityType != null && entity.getType() == entityType));
            for (Entity entity : entities) {
                if (entity.getBoundingBox().intersects(expandedBox)) {
                    entity.startRiding(this, true);
                }
            }

            if (this.ticksInAir > this.airtime && !this.hasLoyalty) {
                Vec3d ownerPos = new Vec3d(this.getOwner().getX() + this.getOwner().getHandPosOffset(this.getBoomerangItem().getItem()).x * 0.5,
                        this.getOwner().getY() + this.getOwner().getEyeHeight(this.getOwner().getPose()) - 0.5,
                        this.getOwner().getZ() + this.getOwner().getHandPosOffset(this.getBoomerangItem().getItem()).z * 0.5);
                Vec3d directionToOwner = ownerPos.subtract(this.getPos()).normalize();

                Vec3d currentVelocity = this.getVelocity();
                float loyatyLevel = (EnchantmentUtil.getLevel(getWorld(), getBoomerangItem(), Enchantments.LOYALTY) * 0.5f) + 1;
                Vec3d newVelocity = directionToOwner.multiply(this.speed * loyatyLevel);
                Vec3d interpolatedVelocity = currentVelocity.lerp(newVelocity, 0.3);

                this.setVelocity(interpolatedVelocity);
            }
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

        int color = Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
        float red = ((color >> 16) & 0xFF) / 255.0f;
        float green = ((color >> 8) & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        return new float[]{red, green, blue};
    }
}
