package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.blocks.redstoneish.onoff.ColorSwitchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CrystalSwitchBlockEntity extends BlockEntity {

    private final List<BlockPos> connectedBlocks = new ArrayList<>();

    private boolean on;

    private int ticks;

    public CrystalSwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.CRYSTAL_SWITCH, pos, state);
        this.ticks = 0;
        this.on = true;
    }

    public void addConnectedBlock(BlockPos pos) {
        this.connectedBlocks.add(pos);
        BlockState state = this.world.getBlockState(pos);

        if (state.getBlock() instanceof ColorSwitchBlock.OnBlock onBlock) {
            onBlock.setOnOffState(this.world, pos, state, this.on);
        }
        else if (state.getBlock() instanceof ColorSwitchBlock.OffBlock offBlock) {
            offBlock.setOnOffState(this.world, pos, state, !this.on);
        }

        markDirty();
    }


    public void toggle() {
        if (this.world != null) {
            this.on = !this.on;
            for (BlockPos pos : this.connectedBlocks) {

                BlockState state = this.world.getBlockState(pos);

                if (state.getBlock() instanceof ColorSwitchBlock.OnBlock onBlock) {
                    onBlock.setOnOffState(this.world, pos, state, this.on);
                }
                else if (state.getBlock() instanceof ColorSwitchBlock.OffBlock offBlock) {
                    offBlock.setOnOffState(this.world, pos, state, !this.on);
                }
            }
        }
    }

    public List<BlockPos> getConnectedBlocks() {
        return this.connectedBlocks;
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, CrystalSwitchBlockEntity entity) {
        entity.ticks++;
    }

    public int getTicks() {
        return this.ticks;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList list = new NbtList();
        for (BlockPos pos : connectedBlocks) {
            list.add(NbtHelper.fromBlockPos(pos));
        }
        nbt.put("ConnectedBlocks", list);
        nbt.putBoolean("OnOffState", this.on);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        connectedBlocks.clear();
        NbtList list = nbt.getList("ConnectedBlocks", NbtElement.COMPOUND_TYPE);
        for (NbtElement element : list) {
            connectedBlocks.add(NbtHelper.toBlockPos((NbtCompound) element));
        }
        this.on = nbt.getBoolean("OnOffState");
        super.readNbt(nbt);
    }
}
