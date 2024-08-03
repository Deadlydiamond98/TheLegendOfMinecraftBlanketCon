package net.deadlydiamond98.items.custom.shields;

import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.mirror_shield.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.mirror_shield.tooltipb").formatted(Formatting.GREEN));
    }

    @Override
    public int getManaCost() {
        return 10;
    }
}
