package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicFireProjectileEntity;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
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

public class IceRod extends Item {
    public IceRod(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        if (ManaHandler.CanRemoveManaFromPlayer(user, 2)) {
            BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
            if (blockState.getBlock() instanceof CampfireBlock campfireBlock && !campfireBlock.isLitCampfire(blockState)) {
                context.getWorld().setBlockState(context.getBlockPos(), blockState.with(LIT, true));
                ManaHandler.removeManaFromPlayer(user, 2);
                context.getWorld().playSound(null, context.getBlockPos(), ZeldaSounds.IceMagic,
                        SoundCategory.PLAYERS, 3.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (ManaHandler.CanRemoveManaFromPlayer(user, 8)) {
            MagicFireProjectileEntity magicFire = new MagicFireProjectileEntity(ZeldaEntities.Magic_Fire_Projectile, world);
            magicFire.setPos(user.getX(), user.getEyeY(), user.getZ());
            Vec3d vec3d = user.getRotationVec(1.0F);
            magicFire.setVelocity(vec3d.x * 2, vec3d.y * 2, vec3d.z * 2);
            magicFire.setYaw(user.getHeadYaw());
            magicFire.setOwner(user);
            world.spawnEntity(magicFire);
            ManaHandler.removeManaFromPlayer(user, 8);
            user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.IceMagic,
                    SoundCategory.PLAYERS, 3.0f, 1.0f);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }
}
