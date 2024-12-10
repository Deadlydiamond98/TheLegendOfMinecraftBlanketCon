package net.deadlydiamond98.items.custom.manaitems;

import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.entities.projectiles.MagicFireProjectileEntity;
import net.deadlydiamond98.magiclib.items.consumers.MagicItem;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.spells.Spell;
import net.deadlydiamond98.spells.fire.FireShotSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireRod extends MagicItem {

    public final Spell fireSpell = new FireShotSpell();

    public FireRod(Settings settings) {
        super(settings, 10);
    }

    @Override
    protected void doManaAction(PlayerEntity user, World world) {
        super.doManaAction(user, world);

        this.fireSpell.doSpellAction(user, world);
    }

    @Override
    protected void doNoManaEvent(PlayerEntity user, World world) {
        super.doNoManaEvent(user, world);
        this.fireSpell.notEnoughManaAction(user, world);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.canRemoveMana(this.getManaCost())) {
            attacker.removeMana(this.getManaCost());
            target.setOnFireFor(4);
        }
        return super.postHit(stack, target, attacker);
    }
}
