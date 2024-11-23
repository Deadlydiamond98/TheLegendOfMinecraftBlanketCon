package net.deadlydiamond98.util;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.util.Identifier;

public class ZeldaAdvancementCriterion {
    public static ZeldaAdvancement idtga;
    public static ZeldaAdvancement miasi;
    public static ZeldaAdvancement eh;
    public static ZeldaAdvancement inv;
    public static ZeldaAdvancement maw;
    public static ZeldaAdvancement bicb;
    public static ZeldaAdvancement ps;

    public static void registerAdvancements() {
        idtga = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "idtga"), 2)); // Magic Sword Get
        miasi = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "miasi"), 2)); // Cut Loot Grass
        eh = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "eh"), 2)); // Harvest Bomb
        inv = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "inv"), 2)); // Eat Magic Food
        maw = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "maw"), 3)); // Find Star
        bicb = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "bicb"), 2)); // Boomerang Throw
        ps = Criteria.register(new ZeldaAdvancement(new Identifier(ZeldaCraft.MOD_ID, "ps"))); // Blow up cracked bricks
    }
}
