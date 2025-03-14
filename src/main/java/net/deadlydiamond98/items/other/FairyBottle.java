package net.deadlydiamond98.items.other;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.fairy.FairyEntity;
import net.deadlydiamond98.util.NBTUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class FairyBottle extends Item {
    public FairyBottle(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        NbtCompound nbt = NBTUtil.getOrCreateNBT(context.getStack());

        if (nbt.contains("fairycolor")) {
            String fairyColor = nbt.getString("fairycolor");

            FairyEntity fairy = new FairyEntity(ZeldaEntities.Fairy_Entity, world);
            fairy.setColor(fairyColor);
            fairy.setPosition(context.getBlockPos().toCenterPos().offset(context.getSide(), 1));
            world.spawnEntity(fairy);
        }
        world.playSound(null, context.getBlockPos(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);

        if (!context.getPlayer().isCreative()) {
            context.getStack().decrement(1);
            context.getPlayer().giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
        }
        return ActionResult.SUCCESS;
    }
}
