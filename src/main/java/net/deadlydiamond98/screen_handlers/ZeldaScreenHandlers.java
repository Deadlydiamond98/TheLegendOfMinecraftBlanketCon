package net.deadlydiamond98.screen_handlers;

import net.deadlydiamond98.ZeldaCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ZeldaScreenHandlers {
    public static ScreenHandlerType<MagicWorkbenchScreenHandler> MAGIC_WORKBENCH_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
//        MAGIC_WORKBENCH_SCREEN_HANDLER = Registry.register(
//                Registries.SCREEN_HANDLER,
//                new Identifier(ZeldaCraft.MOD_ID, "magic_workbench_screen_handler"),
//                new ScreenHandlerType<>(MagicWorkbenchScreenHandler::new)
//        );
    }
}
