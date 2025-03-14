package net.deadlydiamond98.blocks.entities.onoff;

import net.deadlydiamond98.blocks.entities.ZeldaBlockEntities;
import net.deadlydiamond98.blocks.redstoneish.onoff.CrystalSwitch;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalSwitchBlockEntity extends BlockEntity {

    private boolean on;
    private String id;

    private int ticks;

    public CrystalSwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.CRYSTAL_SWITCH, pos, state);
        this.ticks = 0;
        this.on = true;
        this.id = "global";
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, CrystalSwitchBlockEntity entity) {
        if (!world.isClient()) {
            if (entity.on != ZeldaWorldDataManager.getOnOff((ServerWorld) world, entity.id)) {
                entity.on = ZeldaWorldDataManager.getOnOff((ServerWorld) world, entity.id);

                CrystalSwitch onOffBlock = ((CrystalSwitch) blockState.getBlock());
                onOffBlock.setOnOffState(world, pos, blockState, entity.on);
            }
        }
        entity.ticks++;
    }

    public int getTicks() {
        return this.ticks;
    }

    public boolean getTriggerState() {
        return this.on;
    }

    public String getID() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putString("switchId", this.id);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("switchId")) {
            setId(nbt.getString("switchId"));
        }
    }

}
