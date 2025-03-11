package net.deadlydiamond98.spells.fire;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicFireProjectileEntity;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireShotSpell extends Spell {
    @Override
    public void doSpellAction(PlayerEntity user, World world) {
        MagicFireProjectileEntity magicFire = new MagicFireProjectileEntity(ZeldaEntities.Magic_Fire_Projectile, world);
        magicFire.setPos(user.getX(), user.getEyeY(), user.getZ());
        Vec3d vec3d = user.getRotationVec(1.0F);
        magicFire.setVelocity(vec3d.x * 2, vec3d.y * 2, vec3d.z * 2);
        magicFire.setYaw(user.getHeadYaw());
        magicFire.setOwner(user);
        world.spawnEntity(magicFire);

        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.FireMagic,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
//        user.getItemCooldownManager().set(this, 20);
    }

    @Override
    public int cost() {
        return 10;
    }
}
