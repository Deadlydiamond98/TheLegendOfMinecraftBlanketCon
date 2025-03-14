package net.deadlydiamond98.items.manaitems.wearable;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.deadlydiamond98.entities.fairy.PlayerFairyCompanion;
import net.deadlydiamond98.util.NBTUtil;
import net.deadlydiamond98.util.sounds.ZeldaSounds;
import net.deadlydiamond98.util.interfaces.mixin.ZeldaPlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class FairyBell extends TrinketItem {
    private static final List<String> colors = List.of("blue", "yellow", "green", "pink", "red", "purple", "navi", "tatl", "tael");
    public FairyBell(Settings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);

        if (entity instanceof PlayerEntity user) {
            ((ZeldaPlayerData) user).setFairyFriend(true);
            PlayerFairyCompanion playerFairyCompanion = new PlayerFairyCompanion(user.getWorld(), user);
            user.getWorld().spawnEntity(playerFairyCompanion);
            user.enableManaRegen(true, 40, 2);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if (entity instanceof PlayerEntity user) {
            ((ZeldaPlayerData) user).setFairyFriend(false);
            user.enableManaRegen(false, 40, 2);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        NbtCompound nbt = NBTUtil.getOrCreateNBT(stack);

        if (!world.isClient) {
            String currentColor = nbt.getString("Color");
            int nextColorIndex = (colors.indexOf(currentColor) + 1) % colors.size();
            String nextColor = colors.get(nextColorIndex);
            nbt.putString("Color", nextColor);
            NBTUtil.updateNBT(nbt, stack);

            switch (nextColor) {
                case "navi" ->
                    // Maybe change pitch for Navi later b/c navi sounds too deep at times
                        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.NaviHello, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (user.getRandom().nextBetween(-2, 2) * 0.05f));
                case "tatl", "tael" ->
                        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.TatlBell, SoundCategory.PLAYERS, 1.0f,
                                1.0f + (user.getRandom().nextBetween(-2, 2) * 0.05f));
                default ->
                        user.getWorld().playSound(null, user.getBlockPos(), ZeldaSounds.FairyIn, SoundCategory.PLAYERS, 1.0f, 0.6f + (0.2f * nextColorIndex));
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound tag = NBTUtil.getOrCreateNBT(stack);
        if (tag != null && tag.contains("Color")) {
            return (Text.translatable("item.zeldacraft.fairy_bell_with_color", tag.get("Color").asString().toUpperCase()));
        }

        return (Text.translatable("item.zeldacraft.fairy_bell"));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.zeldacraft.fairy_bell.tooltipa").formatted(Formatting.GREEN));
    }
}
