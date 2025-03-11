package net.deadlydiamond98.util.interfaces.block;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ILinkedBlockGroup {
    default void addGroupTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = stack.getOrCreateNbt();

        String id = "global";

        if (nbt.contains("switchId")) {
            id = nbt.getString("switchId");
        }

        tooltip.add(Text.translatable("item.zeldacraft.switch_block.tooltip", id).formatted(Formatting.GREEN));
    }
}
