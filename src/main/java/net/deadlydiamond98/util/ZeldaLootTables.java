package net.deadlydiamond98.util;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.items.ZeldaItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ZeldaLootTables {

    private static final Identifier Sniffer
            = new Identifier("gameplay/sniffer_digging");

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (Sniffer.equals(id)) {
                addToLootPool(tableBuilder, Item.fromBlock(ZeldaBlocks.Bomb_Flower), 1, UniformLootNumberProvider.create(0.0f, 1.0f));
            }
        });
    }

    private static void addToLootPool(LootTable.Builder tableBuilder, Item item, int weight, UniformLootNumberProvider amountMinMax) {
        tableBuilder.modifyPools(poolBuilder -> poolBuilder.with(ItemEntry.builder(item)
                .weight(weight)
                .apply(SetCountLootFunction.builder(amountMinMax))
                .build()));
    }

    private static void addToLootTable(LootTable.Builder tableBuilder, Item item, float chance, UniformLootNumberProvider amountMinMax) {
        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .conditionally(RandomChanceLootCondition.builder(chance))
                .with(ItemEntry.builder(item))
                .apply(SetCountLootFunction.builder(amountMinMax).build());
        tableBuilder.pool(poolBuilder.build());
    }
}
