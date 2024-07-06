package net.deadlydiamond98.items.custom;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.monsters.FairyEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FairyBottle extends Item {
    private static final List<String> colors = List.of("rgb", "blue", "yellow", "green", "pink", "red", "purple");

    private final String color;
    public FairyBottle(Settings settings, String color) {
        super(settings);
        if (colors.contains(color)) {
            this.color = color;
            NbtCompound nbt = new NbtCompound();
            nbt.putString("fairycolor", this.color);
            this.getDefaultStack().setNbt(nbt);
        }
        else {
            this.color = colors.get(6);
            NbtCompound nbt = new NbtCompound();
            nbt.putString("fairycolor", "rgb");
            this.getDefaultStack().setNbt(nbt);
        }
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        FairyEntity fairy = new FairyEntity(ZeldaEntities.Fairy_Entity, world);
        fairy.setColor(this.color);
        world.spawnEntity(fairy);

        context.getStack().decrement(1);
        context.getPlayer().giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
        return ActionResult.SUCCESS;
    }

    public String getColor() {
        return color;
    }

    public List<String> getColorList() {
        return colors;
    }
}
