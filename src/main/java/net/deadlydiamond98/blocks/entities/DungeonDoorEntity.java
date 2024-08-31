package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.blocks.DungeonDoor;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.deadlydiamond98.blocks.DungeonDoor.FACING;
import static net.deadlydiamond98.blocks.DungeonDoor.OPEN;

public class DungeonDoorEntity extends BlockEntity {
    private int openingPosition;
    private double prevopeningPosition;
    private int rotation;
    public DungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.DUNGEON_DOOR, pos, state);
        this.openingPosition = 0;
        this.prevopeningPosition = 0;
        this.rotation = 1;
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, DungeonDoorEntity dungeonDoorEntity) {
        if (!world.isClient()) {
            if (blockState.get(OPEN)) {
                if (dungeonDoorEntity.openingPosition < 3) {
                    dungeonDoorEntity.openingPosition++;
                }

            }
            else {
                if (dungeonDoorEntity.openingPosition > 0) {
                    dungeonDoorEntity.openingPosition--;
                }
            }

            switch (blockState.get(FACING)) {
                case SOUTH -> dungeonDoorEntity.rotation = 0;
                case WEST -> dungeonDoorEntity.rotation = 90;
                case NORTH -> dungeonDoorEntity.rotation = 180;
                case EAST-> dungeonDoorEntity.rotation = 270;
            }

            world.getPlayers().forEach(player -> {
                ZeldaServerPackets.sendDoorOpeningAnimationPacket((ServerPlayerEntity) player, pos, dungeonDoorEntity.getOpeningPosition(), dungeonDoorEntity.rotation);
            });
        }
    }

    public void setOpeningPosition(int openingPosition) {
        this.openingPosition = openingPosition;
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

    public float getRotation() {
        return this.rotation;
    }
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
