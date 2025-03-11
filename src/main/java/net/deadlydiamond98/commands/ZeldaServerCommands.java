package net.deadlydiamond98.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.util.ZeldaConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class ZeldaServerCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            weatherCommand(dispatcher, registryAccess, environment);
        });
    }
    private static void weatherCommand(CommandDispatcher<ServerCommandSource> dispatcher,
                                       CommandRegistryAccess registryAccess,
                                       CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("weather")
                .then(CommandManager.literal("meteor")
                        .executes(context -> {
                            ServerCommandSource source = context.getSource();
                            source.getWorld().setWeather(6000, 0, false, false);
                            if (ZeldaConfig.doMeteorShowerWeatherEvent) {
                                source.sendFeedback(() -> Text.literal("Set the weather to Meteor Shower")
                                        .formatted(Formatting.AQUA), true);
                            } else {
                                source.sendFeedback(() -> Text.literal("Weather Event is disabled")
                                        .formatted(Formatting.RED), true);
                            }
                            ZeldaSeverTickEvent.meteorShower.start(source.getWorld());
                            return 1;
                        })
                )
        );
    }
}
