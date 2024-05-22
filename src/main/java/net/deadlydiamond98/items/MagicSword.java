package net.deadlydiamond98.items;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MagicSword extends SwordItem {

    private boolean soundPlay;
    public MagicSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
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

    public void setSoundPlay(boolean soundPlay) {
        this.soundPlay = soundPlay;
    }
}
