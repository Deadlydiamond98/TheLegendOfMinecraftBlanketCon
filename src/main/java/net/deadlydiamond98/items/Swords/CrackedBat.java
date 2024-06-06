package net.deadlydiamond98.items.Swords;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

import java.util.List;

public class CrackedBat extends SwordItem {
    public CrackedBat(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(ItemTags.LOGS);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.postHit(stack, target, attacker);
        if (Math.floor(Math.random() * (4 - 1 + 1) + 1) == 1) {
            target.damage(target.getDamageSources().magic(), 10.0F);
            target.takeKnockback(0.75, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            ZeldaServerPackets.sendSmaaashParticlePacket((ServerPlayerEntity) attacker, target.getX(),
                    target.getEyeY(), target.getZ());
            attacker.getWorld().playSound(null, attacker.getBlockPos(),
                    ZeldaSounds.Smaaash_Sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return result;
    }
}
