package net.deadlydiamond98.items.custom.boomerang;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.boomerangs.BaseBoomerangProjectile;
import net.deadlydiamond98.entities.projectiles.boomerangs.IronBoomerang;
import net.deadlydiamond98.entities.projectiles.boomerangs.MagicalBoomerang;
import net.deadlydiamond98.entities.projectiles.boomerangs.WoodBoomerang;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicBoomerangItem extends BaseBoomerangItem {
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
        if (ManaHandler.CanRemoveManaFromPlayer(user, 1)) {
            ManaHandler.removeManaFromPlayer(user, 1);
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
}
