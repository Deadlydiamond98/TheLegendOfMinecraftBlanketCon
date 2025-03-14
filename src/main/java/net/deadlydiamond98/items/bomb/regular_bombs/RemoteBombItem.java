package net.deadlydiamond98.items.bomb.regular_bombs;

import net.deadlydiamond98.entities.bombs.AbstractBombEntity;
import net.deadlydiamond98.entities.bombs.RemoteBombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RemoteBombItem extends AbstractBombItem {

    public RemoteBombItem(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractBombEntity createBombEntity(World world, PlayerEntity user, Hand hand) {
        return new RemoteBombEntity(world, user.getX(), user.getEyeY(), user.getZ(), user);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.zeldacraft.remote_bomb.tooltip").formatted(Formatting.GREEN));
    }
}
