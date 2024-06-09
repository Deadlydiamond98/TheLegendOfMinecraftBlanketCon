package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.entities.LootPotBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZeldaBlockEntities {
    public static BlockEntityType<LootPotBlockEntity> LOOT_POT;

    public static void registerBlockEntities() {
        Block[] lootPotBlocks = new Block[]{
                ZeldaBlocks.Plain_Pot
        };

        LOOT_POT = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ZeldaCraft.MOD_ID, "loot_pot"),
                FabricBlockEntityTypeBuilder.create(LootPotBlockEntity::new,
                        lootPotBlocks).build(null));
    }
}
