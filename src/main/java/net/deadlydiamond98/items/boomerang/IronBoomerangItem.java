package net.deadlydiamond98.items.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.boomerangs.IronBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IronBoomerangItem extends BaseBoomerangItem {
    private static final int DEFAULT_COLOR = 0x00bcff;

    public IronBoomerangItem(Settings settings) {
        super(settings, DEFAULT_COLOR);
    }

    @Override
    protected IronBoomerang createBoomerangEntity(World world, PlayerEntity user, ItemStack itemStack, Hand hand) {
        return new IronBoomerang(ZeldaEntities.Iron_Boomerang, world, user, itemStack.copy(), hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.zeldacraft.iron_boomerang.tooltip").formatted(Formatting.GREEN));
    }
}
