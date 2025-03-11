package net.deadlydiamond98.items.manaitems;

import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.spells.Spell;
import net.deadlydiamond98.spells.ice.IceShotSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IceRod extends MagicItem {

    public final Spell iceSpell = new IceShotSpell();

    public IceRod(Settings settings) {
        super(settings, 20);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);

        this.iceSpell.doSpellAction(user, world);
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        this.iceSpell.notEnoughManaAction(user, world);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.canRemoveMana(this.getManaCost())) {
            attacker.removeMana(this.getManaCost());
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5, 0));
        }
        return super.postHit(stack, target, attacker);
    }
}
