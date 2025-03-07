package net.deadlydiamond98.util.interfaces.block;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.doors.DungeonDoorParts;
import net.deadlydiamond98.items.ZeldaItems;
import net.deadlydiamond98.items.items.other.LockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface Lockable {
    enum LockType implements StringIdentifiable {
        UNLOCKED("unlocked", null, null, () -> ZeldaBlocks.Dungeon_Door),
        COPPER("copper", () -> ZeldaItems.Copper_Lock, () -> ZeldaItems.Copper_Key, () -> Blocks.COPPER_BLOCK),
        IRON("iron", () -> ZeldaItems.Iron_Lock, () -> ZeldaItems.Iron_Key, () -> Blocks.IRON_BLOCK),
        GOLD("gold", () -> ZeldaItems.Gold_Lock, () -> ZeldaItems.Gold_Key, () -> Blocks.GOLD_BLOCK),
        BOSS("boss", () -> ZeldaItems.Boss_Lock, () -> ZeldaItems.Boss_Key, () -> Blocks.GOLD_BLOCK);

        @Nullable
        private final Supplier<Item> lockSupplier;
        @Nullable
        private final Supplier<Item> keySupplier;
        @Nullable
        private final Supplier<Block> blockParticles;

        private final String name;

        LockType(String name, Supplier<Item> lockSupplier, Supplier<Item> keySupplier, Supplier<Block> blockParticles) {
            this.name = name;
            this.lockSupplier = lockSupplier;
            this.keySupplier = keySupplier;
            this.blockParticles = blockParticles;
        }

        public @Nullable Item getLock() {
            return this.lockSupplier != null ? this.lockSupplier.get() : null;
        }

        public @Nullable Item getKey() {
            return this.keySupplier != null ? this.keySupplier.get() : null;
        }

        public Block getBlock() {
            return this.blockParticles != null ? this.blockParticles.get() : null;
        }

        public boolean isUnlocked() {
            return this == UNLOCKED;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    default boolean isKeyItem(ItemStack stack, LockType lockType) {
        return stack.isOf(lockType.getKey());
    }

    default boolean isLockItem(ItemStack stack) {
        return stack.getItem() instanceof LockItem;
    }

    default LockType fromLock(ItemStack lock) {
        for (LockType type : LockType.values()) {
            if (lock.isOf(type.getLock())) {
                return type;
            }
        }
        return LockType.UNLOCKED;
    }

}
