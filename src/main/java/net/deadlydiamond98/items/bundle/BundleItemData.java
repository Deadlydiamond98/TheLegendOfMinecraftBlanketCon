package net.deadlydiamond98.items.bundle;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BundleItemData {

    private final String itemId;
    private final NbtCompound itemNbt;
    private int count;

    public BundleItemData(ItemStack stack, int count) {
        this.itemId = Registries.ITEM.getId(stack.getItem()).toString();
        this.itemNbt = stack.getNbt() != null ? stack.getNbt().copy() : new NbtCompound();
        this.count = count;
    }

    public BundleItemData(NbtCompound nbt) {
        this.itemId = nbt.getString("id");
        this.count = nbt.getInt("Count");
        this.itemNbt = nbt.contains("tag") ? nbt.getCompound("tag") : new NbtCompound();
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", this.itemId);
        nbt.putInt("Count", this.count);
        if (!this.itemNbt.isEmpty()) {
            nbt.put("tag", this.itemNbt);
        }
        return nbt;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getItem() {
        return Registries.ITEM.get(new Identifier(this.itemId));
    }
}