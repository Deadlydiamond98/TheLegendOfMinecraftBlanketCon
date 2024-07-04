package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.BeamEntity;
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

public class BeamRod extends Item implements MagicItem {
    public BeamRod(Settings settings) {
        super(settings);
    }

//    @Override
//    public ActionResult useOnBlock(ItemUsageContext context) {
//        PlayerEntity user = context.getPlayer();
//        if (ManaHandler.CanRemoveManaFromPlayer(user, 2)) {
//            BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
//            if (blockState.getBlock() instanceof CampfireBlock campfireBlock && !campfireBlock.isLitCampfire(blockState)) {
//                context.getWorld().setBlockState(context.getBlockPos(), blockState.with(LIT, true));
//                ManaHandler.removeManaFromPlayer(user, 2);
//                context.getWorld().playSound(null, context.getBlockPos(), ZeldaSounds.FireMagic,
//                        SoundCategory.PLAYERS, 3.0f, 1.0f);
//                return ActionResult.SUCCESS;
//            }
//        }
//        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
//        return super.useOnBlock(context);
//    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (ManaHandler.CanRemoveManaFromPlayer(user, 10)) {
            Vec3d vec3d = user.getRotationVec(1.0F);
            BeamEntity beam = new BeamEntity(world, user.getX(), user.getEyeY(), user.getZ(),
                    vec3d.x * 1, vec3d.y * 1, vec3d.z * 1, user);
            world.spawnEntity(beam);

            ManaHandler.removeManaFromPlayer(user, 10);
            user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.FireMagic,
                    SoundCategory.PLAYERS, 3.0f, 1.0f);
            user.getItemCooldownManager().set(this, 20);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return super.use(world, user, hand);
    }

    @Override
    public int getManaCost() {
        return 10;
    }
}
