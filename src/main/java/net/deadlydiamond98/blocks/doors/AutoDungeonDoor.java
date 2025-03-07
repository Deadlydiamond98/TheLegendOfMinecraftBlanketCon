package net.deadlydiamond98.blocks.doors;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.entities.doors.OpeningDungeonDoorEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AutoDungeonDoor extends DungeonDoor {

    public AutoDungeonDoor(Settings settings, DoorColor color) {
        super(settings, color);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void openCloseDoor(DungeonDoorParts part, World world, BlockPos pos, Direction direction, boolean newOpenState, boolean currentOpenState) {
    }

    @Override
    protected BlockEntity getBlockEntity(BlockPos pos, BlockState state) {
        return new OpeningDungeonDoorEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.OPENING_DUNGEON_DOOR, OpeningDungeonDoorEntity::tickAutoDungeonDoor);
    }
}
