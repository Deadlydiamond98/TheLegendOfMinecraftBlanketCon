package net.deadlydiamond98.items.custom.manaitems;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicIceProjectileEntity;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceRod extends MagicItem {
    public IceRod(Settings settings) {
        super(settings, 20);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);

        MagicIceProjectileEntity magicIce = new MagicIceProjectileEntity(ZeldaEntities.Magic_Ice_Projectile, world);
        magicIce.setPos(user.getX(), user.getEyeY(), user.getZ());
        Vec3d vec3d = user.getRotationVec(1.0F);
        magicIce.setVelocity(vec3d.x * 2, vec3d.y * 2, vec3d.z * 2);
        magicIce.setYaw(user.getHeadYaw());
        magicIce.setOwner(user);
        world.spawnEntity(magicIce);

        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.IceMagic,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
        user.getItemCooldownManager().set(this, 20);
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        world.playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.canRemoveMana(this.getManaCost())) {
            attacker.removeMana(this.getManaCost());
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 0));
        }
        return super.postHit(stack, target, attacker);
    }
}
