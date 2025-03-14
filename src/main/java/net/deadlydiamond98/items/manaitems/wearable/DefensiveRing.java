package net.deadlydiamond98.items.manaitems.wearable;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class DefensiveRing extends TrinketItem {
    private double protectionPercentage;
    public DefensiveRing(Settings settings, double protectionPercentage) {
        super(settings);
        this.protectionPercentage = protectionPercentage;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
    }

//    @Override
//    public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
//        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
//
//        modifiers.put(RegistryAttribute.GENERIC_ARMOR, new EntityAttributeModifier(slotIdentifier, this.protectionPercentage, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));
//        return modifiers;
//    }
}
