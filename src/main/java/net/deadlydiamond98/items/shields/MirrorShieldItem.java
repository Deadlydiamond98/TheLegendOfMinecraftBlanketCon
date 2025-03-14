package net.deadlydiamond98.items.shields;

import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MirrorShieldItem extends ShieldItem implements MagicItemData {
    public MirrorShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.zeldacraft.mirror_shield.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.mirror_shield.tooltipb").formatted(Formatting.GREEN));
    }

    @Override
    public int getManaCost() {
        return 10;
    }
}
