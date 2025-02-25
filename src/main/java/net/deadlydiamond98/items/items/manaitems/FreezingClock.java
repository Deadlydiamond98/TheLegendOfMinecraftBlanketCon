package net.deadlydiamond98.items.items.manaitems;

import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezingClock extends MagicItem {
    public FreezingClock(Settings settings) {
        super(settings, 100);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);


        Box box = new Box(user.getBlockPos()).expand(5);
        List<Entity> entities = world.getEntitiesByClass(Entity.class, box, entity -> !(entity instanceof PlayerEntity));
        for (Entity entity : entities) {
            ZeldaSeverTickEvent.addEntityToFrozen(entity, 100);
        }
        user.getItemCooldownManager().set(this, 200);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.frozen_clock.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.frozen_clock.tooltipb").formatted(Formatting.GREEN));
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        world.playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana,
                SoundCategory.PLAYERS, 3.0f, 1.0f);
    }
}
