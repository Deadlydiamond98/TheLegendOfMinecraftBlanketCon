package net.deadlydiamond98.items.items.manaitems;

import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaLivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class PacciCane extends Item implements MagicItemData {
    private int magicCost;
    public PacciCane(Settings settings) {
        super(settings);
        this.magicCost = 1;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if (user.canRemoveMana(this.magicCost)) {
            user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.StarPickedUp,
                    SoundCategory.PLAYERS, 1.0f, 2.0f);
            ((ZeldaLivingEntityData) entity).setflipped(!((ZeldaLivingEntityData) entity).flipped());
            user.removeMana(this.magicCost);
            return ActionResult.SUCCESS;
        }

        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
        return ActionResult.FAIL;
    }

    @Override
    public int getManaCost() {
        return this.magicCost;
    }
}
