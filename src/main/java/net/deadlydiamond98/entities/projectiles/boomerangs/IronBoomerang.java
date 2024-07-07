package net.deadlydiamond98.entities.projectiles.boomerangs;

import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IronBoomerang extends BaseBoomerangProjectile {
    private boolean activated;

    public IronBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world) {
        super(entityType, world);
    }
    public IronBoomerang(EntityType<? extends BaseBoomerangProjectile> entityType, World world, PlayerEntity player, ItemStack boomerangItem, Hand hand) {
        super(entityType, world, player, boomerangItem, 15, 6, 0.6f, hand);
        this.activated = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!activated) {
            checkForActivatableBlocks();
        }
    }

    private void checkForActivatableBlocks() {
        BlockPos pos = this.getBlockPos();
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            if (this.getWorld().getBlockState(blockPos).getBlock() instanceof ButtonBlock buttonBlock) {
                buttonBlock.powerOn(this.getWorld().getBlockState(blockPos), this.getWorld(), blockPos);
                this.activated = true;
                this.returnBack();
                this.getWorld().playSound(null, blockPos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.0f);
                break;
            }
            if (this.getWorld().getBlockState(blockPos).getBlock() instanceof LeverBlock leverBlock) {
                leverBlock.togglePower(this.getWorld().getBlockState(blockPos), this.getWorld(), blockPos);
                this.activated = true;
                this.returnBack();
                this.getWorld().playSound(null, blockPos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0f, 1.0f);
                break;
            }
        }
    }
}
