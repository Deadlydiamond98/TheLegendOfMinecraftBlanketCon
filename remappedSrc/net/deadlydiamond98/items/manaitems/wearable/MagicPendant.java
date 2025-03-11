package net.deadlydiamond98.items.manaitems.wearable;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicPendant extends TrinketItem {
    private final String tooltipa;
    private final String tooltipb;
    private final boolean two;
    public MagicPendant(Settings settings, String tooltipa, boolean two, String tooltipb) {
        super(settings);
        this.tooltipa = tooltipa;
        this.tooltipb = tooltipb;
        this.two = two;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipType context) {
        tooltip.add(Text.translatable(this.tooltipa).formatted(Formatting.GREEN));
        if (two) {
            tooltip.add(Text.translatable(this.tooltipb).formatted(Formatting.DARK_GREEN));
        }
    }
}
