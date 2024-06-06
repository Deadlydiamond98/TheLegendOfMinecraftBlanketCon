package net.deadlydiamond98.items.Swords;

import net.deadlydiamond98.entities.SwordBeamEntity;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
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
