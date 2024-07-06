package net.deadlydiamond98.items.custom.manaItems;

import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ManaHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezingClock extends Item implements MagicItem {
    public FreezingClock(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (ManaHandler.CanRemoveManaFromPlayer(user, 100)) {
            Box box = new Box(user.getBlockPos()).expand(5);
            List<Entity> entities = world.getEntitiesByClass(Entity.class, box, entity -> !(entity instanceof PlayerEntity));
            for (Entity entity : entities) {
                ZeldaSeverTickEvent.addEntityToFrozen(entity, 100);
            }
            ManaHandler.removeManaFromPlayer(user, 100);
            user.getItemCooldownManager().set(this, 200);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NotEnoughMana, SoundCategory.PLAYERS, 1.0f, 1.0f);
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.frozen_clock.tooltipa").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.frozen_clock.tooltipb").formatted(Formatting.GREEN));
    }

    @Override
    public int getManaCost() {
        return 100;
    }
}
