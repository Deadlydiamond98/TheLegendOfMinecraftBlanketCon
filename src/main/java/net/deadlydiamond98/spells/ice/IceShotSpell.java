package net.deadlydiamond98.spells.ice;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicIceProjectileEntity;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceShotSpell extends Spell {
    @Override
    public void doSpellAction(PlayerEntity user, World world) {
        MagicIceProjectileEntity magicIce = new MagicIceProjectileEntity(ZeldaEntities.Magic_Ice_Projectile, world);
        magicIce.setPos(user.getX(), user.getEyeY(), user.getZ());
        Vec3d vec3d = user.getRotationVec(1.0F);
        magicIce.setVelocity(vec3d.x * 2, vec3d.y * 2, vec3d.z * 2);
        magicIce.setYaw(user.getHeadYaw());
        magicIce.setOwner(user);
        world.spawnEntity(magicIce);

        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.IceMagic,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
//        user.getItemCooldownManager().set(this, 20);
    }

    @Override
    public int cost() {
        return 20;
    }
}
