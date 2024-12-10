package net.deadlydiamond98.items.custom.Swords;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MasterSwordBeamEntity;
import net.deadlydiamond98.magiclib.items.MagicItemData;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MasterSword extends SwordItem implements MagicItemData, SwingActionItem {

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

    @Override
    public int getManaCost() {
        return 3;
    }

    @Override
    public void swingSword(World world, PlayerEntity player) {
        if (((player.getHealth() == player.getMaxHealth()) || player.canRemoveMana(3) || player.isCreative())
                && !(player.getItemCooldownManager().isCoolingDown(this))) {
            MasterSwordBeamEntity master_projectile = new MasterSwordBeamEntity(ZeldaEntities.Master_Sword_Beam, world);
            master_projectile.setOwner(player);
            master_projectile.setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()) -0.5, player.getZ());
            Vec3d vec3d = player.getRotationVec(1.0F);
            master_projectile.setVelocity(vec3d.x, vec3d.y, vec3d.z, 0.9F, 0.1F);
            world.spawnEntity(master_projectile);

            player.getItemCooldownManager().set(this, 20);
            this.setSoundPlay(true);
            player.playSound(ZeldaSounds.SwordShoot, SoundCategory.PLAYERS, 1, 1);
            if (!(player.getHealth() == player.getMaxHealth())) {
                player.removeMana(3);
            }
        }
    }
}
