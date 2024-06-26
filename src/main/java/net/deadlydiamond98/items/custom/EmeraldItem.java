package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.mixin.PlayerEntityMixin;
import net.deadlydiamond98.util.OtherPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EmeraldItem extends Item {
    public EmeraldItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        OtherPlayerData userO = (OtherPlayerData) user;
        userO.setFairyState(!userO.isFairy());
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
