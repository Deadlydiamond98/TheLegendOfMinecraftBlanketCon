package net.deadlydiamond98.blocks.entities.doors;

import net.deadlydiamond98.blocks.doors.DungeonDoor;
import net.deadlydiamond98.blocks.doors.DungeonDoorParts;
import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DungeonDoorEntity extends BlockEntity {

    protected int openingPosition;
    private double prevopeningPosition;

    public DungeonDoorEntity(BlockPos pos, BlockState state) {
        this(ZeldaBlockEntities.DUNGEON_DOOR, pos, state);
    }

    public DungeonDoorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.openingPosition = 0;
        this.prevopeningPosition = 0;
    }

    public static void tickDungeonDoor(World world, BlockPos pos, BlockState blockState, DungeonDoorEntity entity) {
        entity.tick(world, pos, blockState);
    }

    protected void tick(World world, BlockPos pos, BlockState blockState) {
        if (blockState.get(DungeonDoor.OPEN)) {
            if (this.openingPosition < 3) {
                this.openingPosition++;
            }

        }
        else {
            if (this.openingPosition > 0) {
                this.openingPosition--;
            }
        }
    }

    public int getOpeningPosition() {
        return this.openingPosition;
    }

    public double getPrevopeningPosition() {
        return this.prevopeningPosition;
    }

    public void setPrevopeningPosition(double prevopeningPosition) {
        this.prevopeningPosition = prevopeningPosition;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}