package net.deadlydiamond98.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NBTUtil {

    public static NbtCompound getOrCreateNBT(ItemStack stack) {

        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (customData == null) {
            return new NbtCompound();
        }

        return customData.getNbt();
    }

    public static void updateNBT(NbtCompound nbt, ItemStack stack) {
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }
}
