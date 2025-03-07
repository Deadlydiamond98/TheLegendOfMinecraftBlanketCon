package net.deadlydiamond98.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LootSkullBlockEntity extends LootPotBlockEntity {
    public static final String NOTE_BLOCK_SOUND_KEY = "note_block_sound";
    @Nullable
    private Identifier noteBlockSound;

    public LootSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.LOOT_SKULL, pos, state);
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        if (this.noteBlockSound != null) {
            nbt.putString("note_block_sound", this.noteBlockSound.toString());
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.contains("note_block_sound", 8)) {
            this.noteBlockSound = Identifier.tryParse(nbt.getString("note_block_sound"));
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}