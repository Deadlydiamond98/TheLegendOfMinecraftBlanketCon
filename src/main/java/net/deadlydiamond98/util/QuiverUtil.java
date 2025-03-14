package net.deadlydiamond98.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.deadlydiamond98.items.bundle.CustomBundle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.Optional;

public class QuiverUtil {

    public static ItemStack findQuiver(PlayerEntity player) {

        Optional<ItemStack> quiverTrinket = findQuiverTrinket(player);

        if (quiverTrinket.isPresent()) {
            return quiverTrinket.get();
        }

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isIn(ZeldaTags.Items.Quivers)) {
                return stack;
            }
        }
        return null;
    }

    public static Optional<ItemStack> findQuiverTrinket(PlayerEntity player) {
        Optional<TrinketComponent> bl = TrinketsApi.getTrinketComponent(player);

        if (bl.isPresent()) {
            TrinketComponent trinket = bl.get();

            for (Pair<SlotReference, ItemStack> entry : trinket.getAllEquipped()) {
                ItemStack stack = entry.getRight();
                if (stack.isIn(ZeldaTags.Items.Quivers)) {
                    return Optional.of(stack);
                }
            }
        }

        return Optional.empty();
    }

    public static boolean useQuiver(PlayerEntity user) {
        Optional<ItemStack> quiverStack = QuiverUtil.findQuiverTrinket(user);

        if (user.isSneaking() && quiverStack.isPresent() && quiverStack.get().getItem() instanceof CustomBundle bundle) {

//            CustomBundleUtil.cycleStack(quiverStack.get());
//
//            Optional<ItemStack> item = CustomBundleUtil.getFirstItem(quiverStack.get());
//
//            if (item.isPresent()) {
//                bundle.playInsertSound(user);
//                user.sendMessage(item.get().getName(), true);
//                return true;
//            }
        }
        return false;
    }
}
