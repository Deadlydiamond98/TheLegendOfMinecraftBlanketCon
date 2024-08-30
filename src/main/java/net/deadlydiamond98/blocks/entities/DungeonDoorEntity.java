package net.deadlydiamond98.blocks.entities;

import net.deadlydiamond98.blocks.DungeonDoor;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.deadlydiamond98.blocks.DungeonDoor.OPEN;

public class DungeonDoorEntity extends BlockEntity {
    private int openingPosition;
    private double prevopeningPosition;
    public DungeonDoorEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.DUNGEON_DOOR, pos, state);
        this.openingPosition = 0;
        this.prevopeningPosition = 0;
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

            world.getPlayers().forEach(player -> {
                ZeldaServerPackets.sendDoorOpeningAnimationPacket((ServerPlayerEntity) player, pos, dungeonDoorEntity.getOpeningPosition());
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
}
