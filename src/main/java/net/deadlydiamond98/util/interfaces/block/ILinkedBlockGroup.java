package net.deadlydiamond98.util.interfaces.block;

import net.deadlydiamond98.util.NBTUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface ILinkedBlockGroup {
    default void addGroupTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound nbt = NBTUtil.getOrCreateNBT(stack);

        String id = "global";

        if (nbt.contains("switchId")) {
            id = nbt.getString("switchId");
        }

        NBTUtil.updateNBT(nbt, stack);

        tooltip.add(Text.translatable("item.zeldacraft.switch_block.tooltip", id).formatted(Formatting.GREEN));
    }
}
