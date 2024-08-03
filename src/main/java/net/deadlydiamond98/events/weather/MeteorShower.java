package net.deadlydiamond98.events.weather;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.particle.ZeldaParticles;
import net.deadlydiamond98.world.ZeldaWorldDataManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

public class MeteorShower {
    private boolean isMeteorShower = false;
    private double starChance = 0.005;

    private final String[] startTexts = new String[] {
            "weather.zeldacraft.meteor_shower.starta",
            "weather.zeldacraft.meteor_shower.startb",
            "weather.zeldacraft.meteor_shower.startc"};

    private final String[] stopTexts = new String[] {
            "weather.zeldacraft.meteor_shower.stopa",
            "weather.zeldacraft.meteor_shower.stopb",
            "weather.zeldacraft.meteor_shower.stopc"};

    public void updateWeather(ServerWorld world) {
        isMeteorShower = ZeldaWorldDataManager.getMeteorShower(world);
        if (shouldStart(world) && world.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)) {
            start(world);
        }
        else if (isMeteorShower && shouldStop(world)) {
            stop(world, true);
        }

        if (isMeteorShowerActive()) {
            world.getPlayers().forEach(player -> {
                double offsetX = (world.random.nextDouble() - 0.5) * 50;
                double offsetZ = (world.random.nextDouble() - 0.5) * 50;
                double spawnY = player.getY() + world.random.nextBetween(10, 25);

                world.spawnParticles(ZeldaParticles.Meteor_Shower_Rain_Particle, player.getX() + offsetX, spawnY, player.getZ() + offsetZ,
                        1, 0, 0, 0, 0.2);

                if (world.getRandom().nextInt(100) >= 95) {
                    player.addMana(1);
                }
            });
        }
    }

    public boolean shouldStart(ServerWorld world) {
        return world.getTimeOfDay() == 13000 && weatherClear(world)
                && !isMeteorShowerActive() && world.getRandom().nextInt(100) >= 97;
    }

    private boolean weatherClear(ServerWorld world) {
        return !world.isRaining() && !world.isThundering();
    }

    private boolean shouldStop(ServerWorld world) {
        return (world.getTimeOfDay() < 13000 || world.getTimeOfDay() > 23000) || !weatherClear(world);
    }

    public void start(ServerWorld world) {
        isMeteorShower = true;
        ZeldaWorldDataManager.setMeteorShower(world, true);
        starChance = 0.1;
        ZeldaCraft.LOGGER.info("Started Meteor Shower");
        world.getPlayers().forEach(player -> {
            player.sendMessage(Text.translatable(startTexts[world.getRandom().nextInt(3)])
                    .formatted(Formatting.GOLD).formatted(Formatting.BOLD), false);
        });
    }

    public void stop(ServerWorld world, boolean text) {
        isMeteorShower = false;
        ZeldaWorldDataManager.setMeteorShower(world, false);
        starChance = 0.005;
        ZeldaCraft.LOGGER.info("Stopped Meteor Shower");
        if (text) {
            world.getPlayers().forEach(player -> {
                player.sendMessage(Text.translatable(stopTexts[world.getRandom().nextInt(3)])
                        .formatted(Formatting.GOLD).formatted(Formatting.BOLD), false);
            });
        }
    }

    public boolean isMeteorShowerActive() {
        return isMeteorShower;
    }

    public double getStarChance() {
        return starChance;
    }

}
