package net.deadlydiamond98.items.bundle;

import net.deadlydiamond98.util.CustomBundleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class BombBag extends CustomBundle {

    public BombBag(Settings settings, int maxStorage, List<TagKey<Item>> itemTags) {
        super(settings, maxStorage, itemTags);
    }


//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        if (!user.isSneaking()) {
//            Optional<ItemStack> bombStack = CustomBundleUtil.getFirstItem(user.getStackInHand(hand));
//
//            if (bombStack.isPresent()) {
//
//                Optional<ItemStack> bomb = CustomBundleUtil.removeOneItem(user.getStackInHand(hand), bombStack.get().getItem());
//
//                if (bomb.isPresent()) {
//
//                    user.getItemCooldownManager().set(this, 40);
//                    return bomb.get().use(world, user, hand);
//                }
//
//                return TypedActionResult.fail(user.getStackInHand(hand));
//            }
//        }
//        return super.use(world, user, hand);
//    }
}