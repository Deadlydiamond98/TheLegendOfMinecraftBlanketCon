package net.deadlydiamond98.items.items.custombundle;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BundledItemData {

    //Purpose of this is to allow more items to be stored in my custom bundles, original bundle dislikes numbers >127

    private final String itemId;
    private final NbtCompound itemNbt;
    private int count;

    public BundledItemData(ItemStack stack, int count) {
        this.itemId = Registries.ITEM.getId(stack.getItem()).toString();
        this.itemNbt = stack.getNbt() != null ? stack.getNbt().copy() : new NbtCompound();
        this.count = count;
    }

    public BundledItemData(NbtCompound nbt) {
        this.itemId = nbt.getString("id");
        this.count = nbt.getInt("Count");
        this.itemNbt = nbt.contains("tag") ? nbt.getCompound("tag") : new NbtCompound();
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", itemId);
        nbt.putInt("Count", count);
        if (!itemNbt.isEmpty()) {
            nbt.put("tag", itemNbt);
        }
        return nbt;
    }

    public String getItemId() {
        return itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getItem() {
        return Registries.ITEM.get(new Identifier(itemId));
    }

    public NbtCompound getItemNbt() {
        return itemNbt;
    }
}