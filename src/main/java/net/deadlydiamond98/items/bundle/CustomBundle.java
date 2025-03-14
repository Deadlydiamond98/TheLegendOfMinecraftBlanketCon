package net.deadlydiamond98.items.bundle;

import net.deadlydiamond98.util.CustomBundleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomBundle extends Item {

    // TODO: REWRITE PARTS OF THIS!!!!

    private static final String ITEMS_KEY = "Items";
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);
    public final int maxStorage;
    public final List<TagKey<Item>> itemTags;

    public CustomBundle(Item.Settings settings, int maxStorage, List<TagKey<Item>> itemTags) {
        super(settings);
        this.maxStorage = maxStorage;
        this.itemTags = List.copyOf(itemTags);
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        if (user.isSneaking()) {
//
//            ItemStack stack = user.getStackInHand(hand);
//
//            CustomBundleUtil.cycleStack(stack);
//
//            Optional<ItemStack> item = CustomBundleUtil.getFirstItem(stack);
//
//            if (item.isPresent()) {
//                playInsertSound(user);
//                user.sendMessage(CustomBundleUtil.getFirstItem(stack).get().getName(), true);
//                return TypedActionResult.success(user.getStackInHand(hand));
//            }
//        }
//        return super.use(world, user, hand);
//    }
//
//    @Override
//    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
//        if (clickType != ClickType.RIGHT) {
//            return false;
//        }
//
//        ItemStack itemStack = slot.getStack();
//        if (itemStack.isEmpty()) {
//            this.playRemoveOneSound(player);
//            removeFirstStack(stack).ifPresent((removedStack) -> CustomBundleUtil.addToBundle(stack, slot.insertStack(removedStack)));
//
//        } else if (CustomBundleUtil.canInsertItem(stack, itemStack.getItem()) && itemStack.getItem().canBeNested()) {
//
//            int maxItemsToAdd = (this.maxStorage - getBundleOccupancy(stack));
//            int itemsAdded = CustomBundleUtil.addToBundle(stack, slot.takeStackRange(itemStack.getCount(), maxItemsToAdd, player));
//
//            if (itemsAdded > 0) {
//                this.playInsertSound(player);
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
//        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
//            if (otherStack.isEmpty()) {
//                removeFirstStack(stack).ifPresent((itemStack) -> {
//                    this.playRemoveOneSound(player);
//                    cursorStackReference.set(itemStack);
//                });
//
//            } else if (CustomBundleUtil.canInsertItem(stack, otherStack.getItem()) && otherStack.getItem().canBeNested()) {
//
//                int itemsAdded = CustomBundleUtil.addToBundle(stack, otherStack);
//
//                if (itemsAdded > 0) {
//
//                    this.playInsertSound(player);
//                    otherStack.decrement(itemsAdded);
//
//                }
//            }
//
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isItemBarVisible(ItemStack stack) {
//        return getBundleOccupancy(stack) > 0;
//    }
//
//    @Override
//    public int getItemBarStep(ItemStack stack) {
//        return Math.min(1 + 12 * getBundleOccupancy(stack) / this.maxStorage, 13);
//    }
//
//    @Override
//    public int getItemBarColor(ItemStack stack) {
//        return ITEM_BAR_COLOR;
//    }

//    public static int getBundleOccupancy(ItemStack stack) {
//        NbtCompound nbtCompound = stack.getNbt();
//
//        if (nbtCompound == null) {
//            return 0;
//        }
//
//        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//        int occupancy = 0;
//        for (int i = 0; i < nbtList.size(); i++) {
//            NbtCompound nbt = nbtList.getCompound(i);
//            BundleItemData bundleItemData = new BundleItemData(nbt);
//            occupancy += bundleItemData.getCount();
//        }
//        return occupancy;
//    }
//
//    protected Optional<ItemStack> removeFirstStack(ItemStack stack) {
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
//        if (itemStack.getMaxCount() >= bundleItemData.getCount()) {
//            nbtList.remove(0);
//        }
//        else {
//            bundleItemData.setCount(bundleItemData.getCount() - itemStack.getMaxCount());
//            nbtList.set(0, bundleItemData.toNbt());
//            itemStack.setCount(itemStack.getMaxCount());
//        }
//
//        if (nbtList.isEmpty()) {
//            stack.removeSubNbt(ITEMS_KEY);
//        } else {
//            nbtCompound.put(ITEMS_KEY, nbtList);
//        }
//
//        return Optional.of(itemStack);
//    }
//
//    private static Stream<ItemStack> getBundledStacks(ItemStack stack) {
//        NbtCompound nbtCompound = stack.getNbt();
//        if (nbtCompound == null) {
//            return Stream.empty();
//        }
//
//        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
//        return nbtList.stream()
//                .filter(NbtCompound.class::isInstance)
//                .map(NbtCompound.class::cast)
//                .map(nbt -> {
//                    BundleItemData bundleItemData = new BundleItemData(nbt);
//                    ItemStack itemStack = new ItemStack(bundleItemData.getItem(), bundleItemData.getCount());
//                    if (nbt.contains("tag")) {
//                        itemStack.setNbt(nbt.getCompound("tag"));
//                    }
//                    return itemStack;
//                });
//    }
//
//    @Override
//    public Optional<TooltipData> getTooltipData(ItemStack stack) {
//        DefaultedList<ItemStack> bundledItems = DefaultedList.of();
//        getBundledStacks(stack).forEach(bundledItems::add);
//        return Optional.of(new BundleTooltipData(bundledItems, getBundleOccupancy(stack)));
//    }
//
//    @Override
//    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
//        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", getBundleOccupancy(stack), maxStorage).formatted(Formatting.GRAY));
//    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    public void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
}
