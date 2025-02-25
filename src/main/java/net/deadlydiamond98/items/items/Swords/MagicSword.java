package net.deadlydiamond98.items.items.Swords;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.SwordBeamEntity;
import net.deadlydiamond98.util.interfaces.items.ISwingActionItem;
import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.ZeldaAdvancementCriterion;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicSword extends SwordItem implements MagicItemData, ISwingActionItem {

    private boolean soundPlay;
    public MagicSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        soundPlay = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity player) {
            if (!world.isClient()) {
                ZeldaAdvancementCriterion.idtga.trigger((ServerPlayerEntity) entity);
            }

            if (!((PlayerEntity) entity).getItemCooldownManager().isCoolingDown(stack.getItem()) && soundPlay) {
                soundPlay = false;
                ((PlayerEntity) entity).playSound(ZeldaSounds.SwordRecharge, SoundCategory.PLAYERS, 1, 1);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.magic_sword.tooltip").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("item.zeldacraft.mana_sword.tooltip").formatted(Formatting.DARK_GREEN));
    }

    public void setSoundPlay(boolean soundPlay) {
        this.soundPlay = soundPlay;
    }

    @Override
    public int getManaCost() {
        return 3;
    }

    @Override
    public void swingSword(World world, PlayerEntity player) {
        if (((player.getHealth() == player.getMaxHealth()) || player.canRemoveMana(3) || player.isCreative())
                && !(player.getItemCooldownManager().isCoolingDown(this))) {
            SwordBeamEntity projectile = new SwordBeamEntity(ZeldaEntities.Sword_Beam, world);
            projectile.setOwner(player);
            projectile.setPosition(
                    player.getX() + player.getHandPosOffset(this).x,
                    player.getY() + player.getEyeHeight(player.getPose()),
                    player.getZ() + player.getHandPosOffset(this).z);
            Vec3d vec3d = player.getRotationVec(1.0F);
            projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.75F, 0.1F);
            world.spawnEntity(projectile);

            player.getItemCooldownManager().set(this, 20);
            this.setSoundPlay(true);
            player.playSound(ZeldaSounds.SwordShoot, SoundCategory.PLAYERS, 1, 1);
            if (!(player.getHealth() == player.getMaxHealth())) {
                player.removeMana(3);
            }
        }
    }
}
