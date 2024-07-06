package net.deadlydiamond98.events;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ShootingStar;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.networking.ZeldaServerPackets;
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
import net.minecraft.text.Text;
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
                            if (player.getRandom().nextDouble() < 0.005 && ((OtherPlayerData)player).canSpawnStar() && world.getTimeOfDay() % 35 == 0) {
                                double x = player.getX() + player.getRandom().nextBetween(-50, 50);
                                double z = player.getZ() + player.getRandom().nextBetween(-50, 50);

                                ShootingStar star = new ShootingStar(ZeldaEntities.Shooting_Star, player.getWorld());
                                star.setPosition(x, 50, z);
                                star.setYaw(player.getRandom().nextBetween(0, 360));
                                star.setGlowing(true);
                                world.spawnEntity(star);
                                world.getPlayers().forEach(playerForSound -> {
                                    double xPos = playerForSound.getX() - star.getX();
                                    double zPos = playerForSound.getZ() - star.getZ();

                                    if (Math.abs(xPos) < 50 && Math.abs(zPos) < 50) {
                                        ZeldaServerPackets.sendShootingStarSound(playerForSound);
                                        playerForSound.sendMessage(Text.literal("Star landed at: " + star.getPos()));
                                        ZeldaCraft.LOGGER.info("Star landed at: " + star.getPos());
                                    }
                                });
                                ((OtherPlayerData) player).setTriedStarSpawn(false);
                            }
                        });
                    }
                    else {
                        world.getPlayers().forEach(player -> ((OtherPlayerData) player).setTriedStarSpawn(true));
                    }

                    if (world.getTimeOfDay() == 23000) {
                        ZeldaCraft.LOGGER.info("A Night has passed");
                    }
                }
            }
        });
    }
}
