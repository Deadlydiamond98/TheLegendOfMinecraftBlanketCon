package net.deadlydiamond98.items.items.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.boomerangs.MagicalBoomerang;
import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicBoomerangItem extends BaseBoomerangItem implements MagicItemData {
    private static final int DEFAULT_COLOR = 0xff4444;

    public MagicBoomerangItem(Settings settings) {
        super(settings, DEFAULT_COLOR);
    }

    @Override
    protected MagicalBoomerang createBoomerangEntity(World world, PlayerEntity user, ItemStack itemStack, Hand hand) {
        return new MagicalBoomerang(ZeldaEntities.Magic_Boomerang, world, user, itemStack.copy(), hand);
    }

    @Override
    protected void damageItem(ItemStack itemStack, PlayerEntity user, Hand hand) {
        if (user.canRemoveMana(3)) {
            user.removeMana(3);
        }
        else {
            super.damageItem(itemStack, user, hand);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.magic_boomerang.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.magic_boomerang.tooltipb").formatted(Formatting.DARK_GREEN));
    }

    @Override
    public int getManaCost() {
        return 3;
    }
}
