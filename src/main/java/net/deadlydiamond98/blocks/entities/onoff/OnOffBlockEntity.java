package net.deadlydiamond98.blocks.entities.onoff;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.redstoneish.onoff.OnOffBlock;
import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.deadlydiamond98.blocks.redstoneish.onoff.OnOffBlock.TRIGGERED;

public class OnOffBlockEntity extends BlockEntity {

    private boolean on;
    private String id;

    public OnOffBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.ON_OFF_BLOCK, pos, state);
        this.on = true;
        this.id = "unassigned";
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, OnOffBlockEntity entity) {
        if (!world.isClient()) {
            if (entity.on != ZeldaWorldDataManager.getOnOff((ServerWorld) world, entity.id)) {
                entity.on = ZeldaWorldDataManager.getOnOff((ServerWorld) world, entity.id);

                OnOffBlock onOffBlock = ((OnOffBlock) blockState.getBlock());
                boolean isOnDefault = onOffBlock.getDefaultState().get(TRIGGERED);

                if (isOnDefault) {
                    onOffBlock.setOnOffState(world, pos, blockState, entity.on);
                }
                else {
                    onOffBlock.setOnOffState(world, pos, blockState, !entity.on);
                }
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("switchId", this.id);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("switchId")) {
            setId(nbt.getString("switchId"));
        }
    }
}
