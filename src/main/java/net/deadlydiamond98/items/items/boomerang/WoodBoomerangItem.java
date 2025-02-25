package net.deadlydiamond98.items.items.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.boomerangs.WoodBoomerang;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodBoomerangItem extends BaseBoomerangItem {
    private static final int DEFAULT_COLOR = 0x2eb617;

    public WoodBoomerangItem(Settings settings) {
        super(settings, DEFAULT_COLOR);
    }

    @Override
    protected WoodBoomerang createBoomerangEntity(World world, PlayerEntity user, ItemStack itemStack, Hand hand) {
        return new WoodBoomerang(ZeldaEntities.Wood_Boomerang, world, user, itemStack.copy(), hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.wooden_boomerang.tooltip").formatted(Formatting.GREEN));
    }
}
