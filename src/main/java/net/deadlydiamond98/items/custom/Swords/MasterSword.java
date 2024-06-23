package net.deadlydiamond98.items.custom.Swords;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MasterSword extends SwordItem {

    private boolean soundPlay;
    public MasterSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        soundPlay = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!((PlayerEntity) entity).getItemCooldownManager().isCoolingDown(stack.getItem()) && soundPlay) {
            soundPlay = false;
            ((PlayerEntity) entity).playSound(ZeldaSounds.SwordRecharge, SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.master_sword.tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.mana_sword.tooltip").formatted(Formatting.DARK_GREEN));
    }

    public void setSoundPlay(boolean soundPlay) {
        this.soundPlay = soundPlay;
    }
}
