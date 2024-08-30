package net.deadlydiamond98.items.custom.custombundle;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.entities.bombs.BombEntity;
import net.deadlydiamond98.entities.bombs.BombchuEntity;
import net.deadlydiamond98.items.custom.bomb.regular_bombs.AbstractBombItem;
import net.deadlydiamond98.items.custom.bomb.regular_bombs.BombItem;
import net.deadlydiamond98.items.custom.bomb.BombchuItem;
import net.deadlydiamond98.items.ZeldaItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class BombBag extends CustomBundle {

    public BombBag(Settings settings, int maxStorage, List<TagKey> itemTags) {
        super(settings, maxStorage, itemTags);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking()) {
            Optional<ItemStack> bombStack = this.getFirstItem(user.getStackInHand(hand));
            if (bombStack.isPresent()) {
                Optional<ItemStack> bomb = this.removeOneItem(user.getStackInHand(hand), bombStack.get().getItem());
                if (bomb.isPresent()) {
                    user.getItemCooldownManager().set(this, 40);
                    return bomb.get().use(world, user, hand);
                }
                return TypedActionResult.fail(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }
}