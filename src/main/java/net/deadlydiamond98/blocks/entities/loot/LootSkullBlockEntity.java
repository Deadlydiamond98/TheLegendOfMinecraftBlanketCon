package net.deadlydiamond98.blocks.entities.loot;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
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

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}