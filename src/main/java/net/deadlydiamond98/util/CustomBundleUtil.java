package net.deadlydiamond98.util;

import net.deadlydiamond98.items.bundle.BundleItemData;
import net.deadlydiamond98.items.bundle.CustomBundle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.TagKey;

import java.util.Optional;

public class CustomBundleUtil {

//    public static final String ITEMS_KEY = "Items";
//
//
//    public static boolean canInsertItem(ItemStack bundleStack, Item item) {
//
//        Item bundleItem = bundleStack.getItem();
//
//        if (bundleItem instanceof CustomBundle bundle) {
//            for (TagKey<Item> itemTag : bundle.itemTags) {
//                if (item.getDefaultStack().isIn(itemTag)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static int addToBundle(ItemStack bundleStack, ItemStack stack) {
//
//        Item item = bundleStack.getItem();
//
//        if (item instanceof CustomBundle bundle) {
//
//            if (!stack.isEmpty() && canInsertItem(bundleStack, stack.getItem()) && stack.getItem().canBeNested()) {
//
//                NbtCompound nbtCompound = bundleStack.getOrCreateNbt();
//
//                if (!nbtCompound.contains(ITEMS_KEY)) {
//                    nbtCompound.put(ITEMS_KEY, new NbtList());
//                }
//
//                int currentOccupancy = CustomBundle.getBundleOccupancy(bundleStack);
//                int itemsToAdd = Math.min(stack.getCount(), (bundle.maxStorage - currentOccupancy));
//
//                NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//                Optional<Integer> existingItemIndex = findBundledItemDataIndex(nbtList, stack);
//
//                if (existingItemIndex.isPresent()) {
//
//                    int index = existingItemIndex.get();
//
//                    BundleItemData bundleItemData = new BundleItemData(nbtList.getCompound(index));
//
//                    int newCount = bundleItemData.getCount() + itemsToAdd;
//                    bundleItemData.setCount(newCount);
//
//                    nbtList.set(index, bundleItemData.toNbt());
//
//                } else {
//                    BundleItemData newItem = new BundleItemData(stack, itemsToAdd);
//                    nbtList.add(newItem.toNbt());
//                }
//
//                nbtCompound.put(ITEMS_KEY, nbtList);
//
//                return itemsToAdd;
//            }
//        }
//        return 0;
//    }
//
//    public static Optional<Integer> findBundledItemDataIndex(NbtList items, ItemStack stack) {
//        for (int i = 0; i < items.size(); i++) {
//
//            NbtCompound nbt = items.getCompound(i);
//
//            BundleItemData bundleItemData = new BundleItemData(nbt);
//
//            ItemStack bundledStack = new ItemStack(bundleItemData.getItem());
//
//            if (bundleItemData.getItem().equals(stack.getItem()) && ItemStack.canCombine(bundledStack, stack)) {
//                return Optional.of(i);
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    public static Optional<ItemStack> cycleStack(ItemStack stack) {
//
//        NbtCompound nbtCompound = stack.getOrCreateNbt();
//        if (!nbtCompound.contains(ITEMS_KEY)) {
//            return Optional.empty();
//        }
//
//        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//        if (nbtList.size() <= 1) {
//            return Optional.empty();
//        }
//
//        NbtCompound firstItemNbt = nbtList.getCompound(0);
//        nbtList.remove(0);
//
//        nbtList.add(firstItemNbt);
//        nbtCompound.put(ITEMS_KEY, nbtList);
//
//        BundleItemData bundleItemData = new BundleItemData(firstItemNbt);
//        ItemStack movedItemStack = new ItemStack(bundleItemData.getItem(), bundleItemData.getCount());
//        if (firstItemNbt.contains("tag")) {
//            movedItemStack.setNbt(firstItemNbt.getCompound("tag"));
//        }
//        return Optional.of(movedItemStack);
//    }
//
//    public static Optional<ItemStack> removeOneItem(ItemStack stack, Item item) {
//        NbtCompound nbtCompound = stack.getOrCreateNbt();
//        if (!nbtCompound.contains(ITEMS_KEY)) {
//            return Optional.empty();
//        }
//
//        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//        for (int i = 0; i < nbtList.size(); i++) {
//            NbtCompound nbt = nbtList.getCompound(i);
//            BundleItemData bundleItemData = new BundleItemData(nbt);
//            if (bundleItemData.getItem().equals(item)) {
//                int newCount = bundleItemData.getCount() - 1;
//                if (newCount > 0) {
//                    bundleItemData.setCount(newCount);
//                    nbtList.set(i, bundleItemData.toNbt());
//                } else {
//                    nbtList.remove(i);
//                }
//
//                if (nbtList.isEmpty()) {
//                    stack.removeSubNbt(ITEMS_KEY);
//                } else {
//                    nbtCompound.put(ITEMS_KEY, nbtList);
//                }
//
//                ItemStack result = new ItemStack(item, 1);
//                if (nbt.contains("tag")) {
//                    result.setNbt(nbt.getCompound("tag"));
//                }
//                return Optional.of(result);
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    public static Optional<ItemStack> getFirstItem(ItemStack stack) {
//        NbtCompound nbtCompound = stack.getOrCreateNbt();
//        if (!nbtCompound.contains(ITEMS_KEY)) {
//            return Optional.empty();
//        }
//
//        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//        if (nbtList.isEmpty()) {
//            return Optional.empty();
//        }
//
//        NbtCompound firstItemNbt = nbtList.getCompound(0);
//        BundleItemData bundleItemData = new BundleItemData(firstItemNbt);
//        ItemStack itemStack = new ItemStack(bundleItemData.getItem(), bundleItemData.getCount());
//        if (firstItemNbt.contains("tag")) {
//            itemStack.setNbt(firstItemNbt.getCompound("tag"));
//        }
//
//        return Optional.of(itemStack);
//    }
//
//    public static int getFilledLevel(ItemStack stack) {
//        Item item = stack.getItem();
//
//        if (item instanceof CustomBundle bundle) {
//            return bundle.getItemBarStep(bundle.getDefaultStack());
//        }
//
//        return 13;
//    }
//
//    public static boolean isFull(ItemStack stack) {
//        return getFilledLevel(stack) >= 13;
//    }
}
