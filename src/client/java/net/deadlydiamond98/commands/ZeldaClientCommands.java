package net.deadlydiamond98.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.deadlydiamond98.ZeldaCraft;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ZeldaClientCommands {

    public static void register() {
        fontDebugCommand();

    }

    private static void fontDebugCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("zeldafont")
                    .then(ClientCommandManager.argument("message", StringArgumentType.greedyString())
                            .executes(context -> {
                                String message = StringArgumentType.getString(context, "message");

                                MutableText text = Text.literal(message).styled(style -> style.withFont(ZeldaCraft.ZELDA_FONT));

                                MinecraftClient.getInstance().player.sendMessage(text, false);
                                return 1;
                            }))
            );
        });
    }
}
