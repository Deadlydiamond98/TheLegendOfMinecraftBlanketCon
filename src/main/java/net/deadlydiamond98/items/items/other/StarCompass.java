package net.deadlydiamond98.items.items.other;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StarCompass extends CompassItem {
    public StarCompass(Settings settings) {
        super(settings);
    }

//    public void giveStarLocation(World world, BlockPos blockPos, PlayerEntity player, ItemStack itemStack) {
//
//        world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
//
//        boolean bl = !player.getAbilities().creativeMode && itemStack.getCount() == 1;
//        if (bl) {
//            this.writeNbt(world.getRegistryKey(), blockPos, itemStack.getOrCreateNbt());
//        } else {
//            ItemStack itemStack2 = new ItemStack(Items.COMPASS, 1);
//            NbtCompound nbtCompound = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
//            itemStack2.setNbt(nbtCompound);
//            if (!player.getAbilities().creativeMode) {
//                itemStack.decrement(1);
//            }
//
//            this.writeNbt(world.getRegistryKey(), blockPos, nbtCompound);
//            if (!player.getInventory().insertStack(itemStack2)) {
//                player.dropItem(itemStack2, false);
//            }
//        }
//    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasEnchantments();
    }
}
