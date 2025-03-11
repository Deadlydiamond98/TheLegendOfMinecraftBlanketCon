package net.deadlydiamond98.blocks.dungeon;

import net.deadlydiamond98.blocks.ZeldaBlocks;
import net.deadlydiamond98.blocks.redstoneish.pushblock.UnJumpablePushBlock;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Function;

public class DungeonciteBlockPallet {

    public final Block brick;
    public final Block mossyBrick;
    public final Block crackedBrick;
    public final Block secretBrick;

    public final Block tile;
    public final Block tileTTL;
    public final Block tileTTR;
    public final Block tileTBL;
    public final Block tileTBR;
    public final Block tileBomb;

    public final Block reinforced;
    public final Block pushable;

    public final Block pedestal;
    public final Block pillar;


    public DungeonciteBlockPallet(String color, String advancementID) {
        // Bricks
        this.brick = registerDungeonciteBlock(color + "_dungeoncite_bricks",
                settings -> new Dungeoncite(settings, advancementID));
        this.mossyBrick = registerDungeonciteBlock("mossy_" + color + "_dungeoncite_bricks",
                settings -> new Dungeoncite(settings, advancementID));
        this.crackedBrick = registerDungeonciteBlock("cracked_" + color + "_dungeoncite_bricks",
                settings -> new Dungeoncite(settings, advancementID));
        this.secretBrick = registerDungeonciteBlock("secret_cracked_" + color + "_dungeoncite_bricks",
                settings -> new SecretDungeoncite(settings, advancementID));

        // Tiles
        this.tile = registerDungeonciteBlock(color + "_dungeoncite_tile",
                settings -> new DungeonciteTile(settings, advancementID));
        this.tileTTL = registerDungeonciteBlock(color + "_dungeoncite_tile_ttl",
                settings -> new DungeonciteTile(settings, advancementID));
        this.tileTTR = registerDungeonciteBlock(color + "_dungeoncite_tile_ttr",
                settings -> new DungeonciteTile(settings, advancementID));
        this.tileTBL = registerDungeonciteBlock(color + "_dungeoncite_tile_tbl",
                settings -> new DungeonciteTile(settings, advancementID));
        this.tileTBR = registerDungeonciteBlock(color + "_dungeoncite_tile_tbr",
                settings -> new DungeonciteTile(settings, advancementID));
        this.tileBomb = registerDungeonciteBlock(color + "_dungeoncite_tile_bomb",
                settings -> new DungeonciteTile(settings, advancementID));

        // Reinforced Dungeoncite
        this.reinforced = registerDungeonciteBlock("reinforced_" + color + "_dungeoncite",
                settings -> new UnjumpableDungeoncite(settings, advancementID));
        this.pushable = registerDungeonciteBlock(color + "_dungeoncite_push_block",
                settings -> new UnJumpableDungeoncitePushBlock(settings, advancementID));

        // Pillar
        this.pedestal = registerDungeonciteBlock(color + "_dungeoncite_pedestal",
                settings -> new Dungeoncite(settings, advancementID));
        this.pillar = registerDungeonciteBlock(color + "_dungeoncite_pillar",
                settings -> new DungeoncitePillar(settings, advancementID));
    }

    public void addDungeonciteToCreative(ItemGroup.Entries entry) {
        entry.add(this.brick);

        entry.add(this.crackedBrick);
        entry.add(this.mossyBrick);
        entry.add(this.secretBrick);

        entry.add(this.tile);
        entry.add(this.tileTTL);
        entry.add(this.tileTTR);
        entry.add(this.tileTBL);
        entry.add(this.tileTBR);
        entry.add(this.tileBomb);

        entry.add(this.reinforced);
        entry.add(this.pushable);

        entry.add(this.pedestal);
        entry.add(this.pillar);
    }

    private static Block registerDungeonciteBlock(String blockName, Function<FabricBlockSettings, Block> constructor) {
        return ZeldaBlocks.registerBlock(blockName, constructor.apply(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS)
                .strength(1, 1200)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()));
    }

    public class Dungeoncite extends Block {

        private final String advancementID;

        public Dungeoncite(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }

    public class DungeoncitePillar extends PillarBlock {

        private final String advancementID;

        public DungeoncitePillar(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }

    public class DungeonciteTile extends GlazedTerracottaBlock {

        private final String advancementID;

        public DungeonciteTile(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }

    public class SecretDungeoncite extends SecretStone {

        private final String advancementID;

        public SecretDungeoncite(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }

    public class UnjumpableDungeoncite extends UnJumpableBlock {

        private final String advancementID;

        public UnjumpableDungeoncite(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }

    public class UnJumpableDungeoncitePushBlock extends UnJumpablePushBlock {

        private final String advancementID;

        public UnJumpableDungeoncitePushBlock(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            if (!((ZeldaPlayerData) player).hasAdvancement(this.advancementID)) {
                return -1;
            }
            return super.calcBlockBreakingDelta(state, player, world, pos);
        }
    }
}
