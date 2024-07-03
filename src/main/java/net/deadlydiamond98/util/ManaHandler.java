package net.deadlydiamond98.util;

import net.deadlydiamond98.sounds.ZeldaSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.TypedActionResult;

public class ManaHandler {
    public static void addManaToPlayer(PlayerEntity user, int amountToGive) {
        ManaPlayerData userM = ((ManaPlayerData) user);

        if (amountToGive + userM.getMana() <= userM.getMaxMana()) {
            userM.setMana(userM.getMana() + amountToGive);
        }
        else if (userM.getMana() < userM.getMaxMana() && amountToGive + userM.getMana() > userM.getMaxMana()) {
            userM.setMana(userM.getMaxMana());
        }
    }
    public static void removeManaFromPlayer(PlayerEntity user, int amountToRemove) {
        ManaPlayerData userM = ((ManaPlayerData) user);

        if (userM.getMana() - amountToRemove >= 0) {
            userM.setMana(userM.getMana() - amountToRemove);
        }
        else if (userM.getMana() > 0 && userM.getMana() - amountToRemove < 0) {
            userM.setMana(0);
        }
    }

    public static boolean CanAddManaToPlayer(PlayerEntity user, int amountToGive) {
        ManaPlayerData userM = ((ManaPlayerData) user);
        if (amountToGive + userM.getMana() <= userM.getMaxMana()) {
            return true;
        }
        else if (userM.getMana() < userM.getMaxMana() && amountToGive + userM.getMana() > userM.getMaxMana()) {
            return true;
        }
        return false;
    }

    public static boolean CanRemoveManaFromPlayer(PlayerEntity user, int amountToRemove) {
        ManaPlayerData userM = ((ManaPlayerData) user);

        if (userM.getMana() - amountToRemove >= 0) {
            return true;
        }
        return false;
    }

}
