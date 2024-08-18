package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicFireProjectileEntity;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.block.CampfireBlock.LIT;

public class FireRod extends MagicItem {
    public FireRod(Settings settings) {
        super(settings, 10);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);


        MagicFireProjectileEntity magicFire = new MagicFireProjectileEntity(ZeldaEntities.Magic_Fire_Projectile, world);
        magicFire.setPos(user.getX(), user.getEyeY(), user.getZ());
        Vec3d vec3d = user.getRotationVec(1.0F);
        magicFire.setVelocity(vec3d.x * 2, vec3d.y * 2, vec3d.z * 2);
        magicFire.setYaw(user.getHeadYaw());
        magicFire.setOwner(user);
        world.spawnEntity(magicFire);

        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.FireMagic,
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
            target.setOnFireFor(4);
        }
        return super.postHit(stack, target, attacker);
    }
}
