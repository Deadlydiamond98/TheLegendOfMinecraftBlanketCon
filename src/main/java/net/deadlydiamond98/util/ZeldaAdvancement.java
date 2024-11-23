package net.deadlydiamond98.util;

import com.google.gson.JsonObject;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ZeldaAdvancement extends AbstractCriterion<ZeldaAdvancement.Conditions> {

    private final Identifier id;
    private final int soundtype;
    private final boolean sound;

    public ZeldaAdvancement(Identifier id, int soundtype) {
        this.id = id;
        this.soundtype = soundtype;
        this.sound = true;
    }

    public ZeldaAdvancement(Identifier id) {
        this.id = id;
        this.soundtype = 1;
        this.sound = false;
    }

    public void trigger(ServerPlayerEntity player) {
        Advancement advancement = player.getServer().getAdvancementLoader().get(this.getId());
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (progress.isDone()) {
                return;
            }
        }
        this.trigger(player, (conditions) -> true);

        if (this.sound) {
            ZeldaServerPackets.sendSoundPacket(player, this.soundtype);
        }
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(playerPredicate);
    }

    public class Conditions extends AbstractCriterionConditions {
        public Conditions(LootContextPredicate player) {
            super(id, player);
        }
    }
}