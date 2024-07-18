package net.deadlydiamond98.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
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
            //weatherCommand(dispatcher, registryAccess, environment);
        });
    }

    // this is just a debug command for testing the zelda font
//    private static void weatherCommand(CommandDispatcher<ServerCommandSource> dispatcher,
//                                       CommandRegistryAccess registryAccess,
//                                       CommandManager.RegistrationEnvironment environment) {
//        dispatcher.register(literal("weather")
//                .then(literal("meteor_shower")
//                        .executes(context -> {
//                            ServerCommandSource source = (ServerCommandSource) context.getSource();
//                            source.sendFeedback(() -> Text.literal("Setting weather to Mystic Fog...").formatted(Formatting.AQUA), true);
//                            ZeldaSeverTickEvent.meteorShower.start(source.getWorld());
//                            return 1;
//                        })
//                )
//        );
//    }
}
