package net.deadlydiamond98.items.custombundle;

import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomBundle extends Item {
    private static final String ITEMS_KEY = "Items";
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
    private final int maxStorage;
    private final List<Item> itemInsertable;

    public CustomBundle(Item.Settings settings, int maxStorage, List<Item> itemInsertable) {
        super(settings);
        this.maxStorage = maxStorage;
        this.itemInsertable = List.copyOf(itemInsertable);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeFirstStack(stack).ifPresent((removedStack) -> {
                    addToBundle(stack, slot.insertStack(removedStack));
                });
            } else if (itemInsertable.contains(itemStack.getItem()) && itemStack.getItem().canBeNested()) {
                int maxItemsToAdd = (maxStorage - getBundleOccupancy(stack)) / getItemOccupancy(itemStack);
                int itemsAdded = addToBundle(stack, slot.takeStackRange(itemStack.getCount(), maxItemsToAdd, player));
                if (itemsAdded > 0) {
                    this.playInsertSound(player);
                }
            }

            return true;
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                removeFirstStack(stack).ifPresent((itemStack) -> {
                    this.playRemoveOneSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else if (itemInsertable.contains(otherStack.getItem()) && otherStack.getItem().canBeNested()) {
                int itemsAdded = addToBundle(stack, otherStack);
                if (itemsAdded > 0) {
                    this.playInsertSound(player);
                    otherStack.decrement(itemsAdded);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getBundleOccupancy(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getBundleOccupancy(stack) / maxStorage, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    private int addToBundle(ItemStack bundle, ItemStack stack) {
        if (!stack.isEmpty() && itemInsertable.contains(stack.getItem()) && stack.getItem().canBeNested()) {
            NbtCompound nbtCompound = bundle.getOrCreateNbt();
            if (!nbtCompound.contains(ITEMS_KEY)) {
                nbtCompound.put(ITEMS_KEY, new NbtList());
            }

            int currentOccupancy = getBundleOccupancy(bundle);
            int itemOccupancy = getItemOccupancy(stack);
            int itemsToAdd = Math.min(stack.getCount(), (maxStorage - currentOccupancy) / itemOccupancy);

            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
            Optional<Integer> existingItemIndex = findBundledItemDataIndex(nbtList, stack);
            if (existingItemIndex.isPresent()) {
                int index = existingItemIndex.get();
                BundledItemData bundledItemData = new BundledItemData(nbtList.getCompound(index));
                int newCount = bundledItemData.getCount() + itemsToAdd;
                bundledItemData.setCount(newCount);
                nbtList.set(index, bundledItemData.toNbt());
            } else {
                BundledItemData newItem = new BundledItemData(stack, itemsToAdd);
                nbtList.add(newItem.toNbt());
            }

            nbtCompound.put(ITEMS_KEY, nbtList);
            return itemsToAdd;
        }
        return 0;
    }

    private Optional<Integer> findBundledItemDataIndex(NbtList items, ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            NbtCompound nbt = items.getCompound(i);
            BundledItemData bundledItemData = new BundledItemData(nbt);
            ItemStack bundledStack = new ItemStack(bundledItemData.getItem());
            if (bundledItemData.getItem().equals(stack.getItem()) && ItemStack.canCombine(bundledStack, stack)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private static int getItemOccupancy(ItemStack stack) {
        return 64 / stack.getMaxCount();
    }

    private static int getBundleOccupancy(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return 0;
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        int occupancy = 0;
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbt = nbtList.getCompound(i);
            BundledItemData bundledItemData = new BundledItemData(nbt);
            occupancy += getItemOccupancy(new ItemStack(bundledItemData.getItem())) * bundledItemData.getCount();
        }
        return occupancy;
    }

    private Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        if (nbtList.isEmpty()) {
            return Optional.empty();
        }

        NbtCompound firstItemNbt = nbtList.getCompound(0);
        BundledItemData bundledItemData = new BundledItemData(firstItemNbt);
        ItemStack itemStack = new ItemStack(bundledItemData.getItem(), bundledItemData.getCount());
        if (firstItemNbt.contains("tag")) {
            itemStack.setNbt(firstItemNbt.getCompound("tag"));
        }
        nbtList.remove(0);
        if (nbtList.isEmpty()) {
            stack.removeSubNbt(ITEMS_KEY);
        } else {
            nbtCompound.put(ITEMS_KEY, nbtList);
        }

        return Optional.of(itemStack);
    }

    public Optional<ItemStack> removeOneItem(ItemStack stack, Item item) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbt = nbtList.getCompound(i);
            BundledItemData bundledItemData = new BundledItemData(nbt);
            if (bundledItemData.getItem().equals(item)) {
                int newCount = bundledItemData.getCount() - 1;
                if (newCount > 0) {
                    bundledItemData.setCount(newCount);
                    nbtList.set(i, bundledItemData.toNbt());
                } else {
                    nbtList.remove(i);
                }

                if (nbtList.isEmpty()) {
                    stack.removeSubNbt(ITEMS_KEY);
                } else {
                    nbtCompound.put(ITEMS_KEY, nbtList);
                }

                ItemStack result = new ItemStack(item, 1);
                if (nbt.contains("tag")) {
                    result.setNbt(nbt.getCompound("tag"));
                }
                return Optional.of(result);
            }
        }

        return Optional.empty();
    }

    private static Stream<ItemStack> getBundledStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        return nbtList.stream()
                .filter(NbtCompound.class::isInstance)
                .map(NbtCompound.class::cast)
                .map(nbt -> {
                    BundledItemData bundledItemData = new BundledItemData(nbt);
                    ItemStack itemStack = new ItemStack(bundledItemData.getItem(), bundledItemData.getCount());
                    if (nbt.contains("tag")) {
                        itemStack.setNbt(nbt.getCompound("tag"));
                    }
                    return itemStack;
                });
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> bundledItems = DefaultedList.of();
        getBundledStacks(stack).forEach(bundledItems::add);
        return Optional.of(new BundleTooltipData(bundledItems, getBundleOccupancy(stack)));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", getBundleOccupancy(stack), maxStorage).formatted(Formatting.GRAY));
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemUsage.spawnItemContents(entity, getBundledStacks(entity.getStack()));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
}
