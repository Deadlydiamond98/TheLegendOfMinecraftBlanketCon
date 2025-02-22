package net.deadlydiamond98.items.custom.custombundle;

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
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomBundle extends Item {

    // TODO: REWRITE PARTS OF THIS!!!!

    private static final String ITEMS_KEY = "Items";
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
    public final int maxStorage;
    private final List<TagKey> itemTags;

    public CustomBundle(Item.Settings settings, int maxStorage, List<TagKey> itemTags) {
        super(settings);
        this.maxStorage = maxStorage;
        this.itemTags = List.copyOf(itemTags);
    }
    
    private boolean canInsertItem(Item item) {
        for (int i = 0; i < this.itemTags.size(); i++) {
            if (item.getDefaultStack().isIn(itemTags.get(i))) {
                return true;
            }
        }
        return false;
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
            } else if (canInsertItem(itemStack.getItem()) && itemStack.getItem().canBeNested()) {
                int maxItemsToAdd = (maxStorage - getBundleOccupancy(stack));
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
            } else if (canInsertItem(otherStack.getItem()) && otherStack.getItem().canBeNested()) {
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

    public int addToBundle(ItemStack bundle, ItemStack stack) {
        if (!stack.isEmpty() && canInsertItem(stack.getItem()) && stack.getItem().canBeNested()) {
            NbtCompound nbtCompound = bundle.getOrCreateNbt();
            if (!nbtCompound.contains(ITEMS_KEY)) {
                nbtCompound.put(ITEMS_KEY, new NbtList());
            }

            int currentOccupancy = getBundleOccupancy(bundle);
            int itemsToAdd = Math.min(stack.getCount(), (maxStorage - currentOccupancy));

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

    protected static int getBundleOccupancy(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return 0;
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        int occupancy = 0;
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound nbt = nbtList.getCompound(i);
            BundledItemData bundledItemData = new BundledItemData(nbt);
            occupancy += bundledItemData.getCount();
        }
        return occupancy;
    }

    protected Optional<ItemStack> removeFirstStack(ItemStack stack) {
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

        if (itemStack.getMaxCount() >= bundledItemData.getCount()) {
            nbtList.remove(0);
        }
        else {
            bundledItemData.setCount(bundledItemData.getCount() - itemStack.getMaxCount());
            nbtList.set(0, bundledItemData.toNbt());
            itemStack.setCount(itemStack.getMaxCount());
        }

        if (nbtList.isEmpty()) {
            stack.removeSubNbt(ITEMS_KEY);
        } else {
            nbtCompound.put(ITEMS_KEY, nbtList);
        }

        return Optional.of(itemStack);
    }

    public Optional<ItemStack> cycleStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        }

        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
        if (nbtList.size() <= 1) {
            return Optional.empty();
        }

        NbtCompound firstItemNbt = nbtList.getCompound(0);
        nbtList.remove(0);

        nbtList.add(firstItemNbt);
        nbtCompound.put(ITEMS_KEY, nbtList);

        BundledItemData bundledItemData = new BundledItemData(firstItemNbt);
        ItemStack movedItemStack = new ItemStack(bundledItemData.getItem(), bundledItemData.getCount());
        if (firstItemNbt.contains("tag")) {
            movedItemStack.setNbt(firstItemNbt.getCompound("tag"));
        }
        return Optional.of(movedItemStack);
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

    public Optional<ItemStack> getFirstItem(ItemStack stack) {
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

        return Optional.of(itemStack);
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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            this.cycleStack(user.getStackInHand(hand));
            playInsertSound(user);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }
}
