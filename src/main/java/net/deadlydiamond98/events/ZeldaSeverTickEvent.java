package net.deadlydiamond98.events;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ShootingStar;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.sounds.ZeldaSounds;
import net.deadlydiamond98.util.OtherPlayerData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZeldaSeverTickEvent {
    public static void registerTickEvent() {
        onEndServerTick();
    }

    private static void onEndServerTick() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                if (world.getRegistryKey() == World.OVERWORLD) {
                    if (world.getTimeOfDay() >= 13000 && world.getTimeOfDay() <= 23000) {
                        world.getPlayers().forEach(player -> {
                            if (player.getRandom().nextDouble() < 0.005 && ((OtherPlayerData)player).canSpawnStar() && world.getTimeOfDay() % 20 == 0) {
                                double x = player.getX() + (player.getRandom().nextDouble() - 0.5) * 25;
                                double y = player.getY() + 50;
                                double z = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 25;

                                if (world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).isAir()) {
                                    ShootingStar star = new ShootingStar(ZeldaEntities.Shooting_Star, player.getWorld());
                                    star.setPosition(x, y, z);
                                    star.setYaw(player.getRandom().nextBetween(0, 360));
                                    world.spawnEntity(star);
                                    world.playSound(null, new BlockPos(star.getBlockPos().getX(), (int) (player.getY() + 10), star.getBlockPos().getZ()),
                                            ZeldaSounds.ShootingStarFalling, SoundCategory.MASTER, 1.0f, 1.0f);
                                    ((OtherPlayerData) player).setTriedStarSpawn(false);
                                }
                            }
                        });
                    }
                    else {
                        world.getPlayers().forEach(player -> ((OtherPlayerData) player).setTriedStarSpawn(true));
                    }
                }
            }
        });
    }
}
