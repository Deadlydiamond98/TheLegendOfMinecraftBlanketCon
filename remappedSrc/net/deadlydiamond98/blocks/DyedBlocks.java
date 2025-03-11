package net.deadlydiamond98.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import org.apache.commons.lang3.ArrayUtils;

import java.util.function.Supplier;

public class DyedBlocks {

    public final Block red;
    public final Block orange;
    public final Block yellow;
    public final Block lime;
    public final Block green;
    public final Block light_blue;
    public final Block cyan;
    public final Block blue;
    public final Block purple;
    public final Block magenta;
    public final Block pink;
    public final Block white;
    public final Block gray;
    public final Block light_gray;
    public final Block black;
    public final Block brown;

    public DyedBlocks(String name, Supplier<Block> block) {
        this.red = registerColoredBlock("red_" + name, block.get());
        this.orange = registerColoredBlock("orange_" + name, block.get());
        this.yellow = registerColoredBlock("yellow_" + name, block.get());
        this.lime = registerColoredBlock("lime_" + name, block.get());
        this.green = registerColoredBlock("green_" + name, block.get());
        this.light_blue = registerColoredBlock("light_blue_" + name, block.get());
        this.cyan = registerColoredBlock("cyan_" + name, block.get());
        this.blue = registerColoredBlock("blue_" + name, block.get());
        this.purple = registerColoredBlock("purple_" + name, block.get());
        this.magenta = registerColoredBlock("magenta_" + name, block.get());
        this.pink = registerColoredBlock("pink_" + name, block.get());
        this.white = registerColoredBlock("white_" + name, block.get());
        this.gray = registerColoredBlock("gray_" + name, block.get());
        this.light_gray = registerColoredBlock("light_gray_" + name, block.get());
        this.black = registerColoredBlock("black_" + name, block.get());
        this.brown = registerColoredBlock("brown_" + name, block.get());
    }

    public void addDyedBlocksToCreative(ItemGroup.Entries entry) {
        entry.add(this.white);
        entry.add(this.light_gray);
        entry.add(this.gray);
        entry.add(this.black);
        entry.add(this.brown);
        entry.add(this.red);
        entry.add(this.orange);
        entry.add(this.yellow);
        entry.add(this.lime);
        entry.add(this.green);
        entry.add(this.cyan);
        entry.add(this.light_blue);
        entry.add(this.blue);
        entry.add(this.purple);
        entry.add(this.magenta);
        entry.add(this.pink);
    }

    public Block[] getAll() {
        return new Block[] {
                this.red,
                this.orange,
                this.yellow,
                this.lime,
                this.green,
                this.light_blue,
                this.cyan,
                this.blue,
                this.purple,
                this.magenta,
                this.pink,
                this.white,
                this.light_gray,
                this.gray,
                this.black,
                this.brown
        };
    }

    public Block[] getAll(Block... block) {
        return ArrayUtils.addAll(getAll(), block);
    }

    private static Block registerColoredBlock(String blockName, Block block) {
        return ZeldaBlocks.registerBlock(blockName, block);
    }
}
