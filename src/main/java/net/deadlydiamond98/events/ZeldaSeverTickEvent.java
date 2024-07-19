package net.deadlydiamond98.events;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.entities.ShootingStar;
import net.deadlydiamond98.entities.ZeldaEntities;
import net.deadlydiamond98.events.weather.MeteorShower;
import net.deadlydiamond98.networking.ZeldaServerPackets;
import net.deadlydiamond98.statuseffects.StunStatusEffect;
import net.deadlydiamond98.statuseffects.ZeldaStatusEffects;
import net.deadlydiamond98.util.OtherPlayerData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class ZeldaSeverTickEvent {

    public static final MeteorShower meteorShower = new MeteorShower();
    private static final Map<UUID, Integer> frozenEntities = new HashMap<>();
    private static final Map<UUID, Vec3d> velocity = new HashMap<>();

    public static void registerTickEvent() {
        onEndServerTick();
    }

    private static void onEndServerTick() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            shootingStarsAtNight(server);
            meteorShowerEvent(server);
            timeBoxSlowing(server);
        });
    }

    private static void meteorShowerEvent(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            if (world.getRegistryKey() == World.OVERWORLD) {
                meteorShower.updateWeather(world);
            }
        }
    }

    private static void shootingStarsAtNight(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            if (world.getRegistryKey() == World.OVERWORLD) {
                if (world.getTimeOfDay() >= 13000 && world.getTimeOfDay() <= 23000) {
                    world.getPlayers().forEach(player -> {
                        if (player.getRandom().nextDouble() < meteorShower.getStarChance() && ((OtherPlayerData)player).canSpawnStar()
                                && world.getTimeOfDay() % 30 == 0) {
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
                                    if (!meteorShower.isMeteorShowerActive()) {
                                        ZeldaServerPackets.sendShootingStarSound(playerForSound);
                                    }
                                    ZeldaCraft.LOGGER.info("Star landed at: " + star.getPos());
                                }
                            });
                            if (!meteorShower.isMeteorShowerActive()) {
                                ((OtherPlayerData) player).setTriedStarSpawn(false);
                            }
                        }
                    });
                }
                else {
                    world.getPlayers().forEach(player -> ((OtherPlayerData) player).setTriedStarSpawn(true));
                }
//
//                    if (world.getTimeOfDay() == 23000) {
//                        ZeldaCraft.LOGGER.info("A Night has passed");
//                    }
            }
        }
    }

    public static void addEntityToFrozen(Entity entity, int time) {
        if (entity instanceof LivingEntity livingEntity) {
            StunStatusEffect stunStatusEffect = (StunStatusEffect) ZeldaStatusEffects.Stun_Status_Effect;
            stunStatusEffect.giveOverlay(StunStatusEffect.OverlayType.CLOCK);
            livingEntity.addStatusEffect(new StatusEffectInstance(stunStatusEffect, 10, 0, false, false));
        }
        entity.setYaw(entity.getYaw());
        entity.setPitch(entity.getPitch());
        UUID entityId = entity.getUuid();
        frozenEntities.putIfAbsent(entityId, time);
        velocity.putIfAbsent(entityId, entity.getVelocity());
    }


    private static void timeBoxSlowing(MinecraftServer server) {
        for (Map.Entry<UUID, Integer> entry : new HashMap<>(frozenEntities).entrySet()) {
            server.getWorlds().forEach(serverWorld -> {
                UUID entityId = entry.getKey();
                int ticksLeft = entry.getValue();
                Vec3d entityVelocity = velocity.get(entityId);

                Entity entity = serverWorld.getEntity(entityId);

                if (entity != null) {
                    if (ticksLeft <= 0) {
                        frozenEntities.remove(entityId);
                        if (entity instanceof LivingEntity livingEntity) {
                            livingEntity.removeStatusEffect(ZeldaStatusEffects.Stun_Status_Effect);
                        }
                        entity.setNoGravity(false);
                        entity.setVelocity(entityVelocity);
                    } else {
                        if (entity instanceof LivingEntity livingEntity) {
                            StunStatusEffect stunStatusEffect = (StunStatusEffect) ZeldaStatusEffects.Stun_Status_Effect;
                            stunStatusEffect.giveOverlay(StunStatusEffect.OverlayType.CLOCK);
                            livingEntity.addStatusEffect(new StatusEffectInstance(stunStatusEffect, 10, 0, false, false));
                        }
                        entity.setYaw(entity.prevYaw);
                        entity.setPitch(entity.prevPitch);
                        entity.setVelocity(0, 0, 0);
                        entity.setPosition(entity.prevX, entity.prevY, entity.prevZ);
                        entity.velocityDirty = true;
                        entity.setNoGravity(true);
                        frozenEntities.put(entityId, ticksLeft - 1);
                    }
                }
            });
        }
    }
}
