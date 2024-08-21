package net.deadlydiamond98.items.custom.Swords;

import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
            target.damage(target.getDamageSources().magic(), 11.0F);
            target.takeKnockback(1.0, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            ZeldaServerPackets.sendSmaaashParticlePacket((ServerPlayerEntity) attacker, target.getX(),
                    target.getEyeY(), target.getZ());
            attacker.getWorld().playSound(null, attacker.getBlockPos(),
                    ZeldaSounds.Smaaash_Sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return result;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.zeldacraft.cracked_bat.tooltip").formatted(Formatting.GREEN));
    }
}
