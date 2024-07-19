package net.deadlydiamond98.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.deadlydiamond98.events.ZeldaSeverTickEvent;
import net.deadlydiamond98.events.weather.MeteorShower;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WeatherCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherCommand.class)
public class WeatherCommandMixin {

    @Inject(method = "executeClear", at = @At("HEAD"))
    private static void onWeatherClear(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
        ZeldaSeverTickEvent.meteorShower.stop(source.getWorld(), false);
    }

    @Inject(method = "executeRain", at = @At("HEAD"))
    private static void onWeatherRain(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
        ZeldaSeverTickEvent.meteorShower.stop(source.getWorld(), false);
    }

    @Inject(method = "executeThunder", at = @At("HEAD"))
    private static void onWeatherThunder(ServerCommandSource source, int duration, CallbackInfoReturnable<Integer> cir) {
        ZeldaSeverTickEvent.meteorShower.stop(source.getWorld(), false);
    }
}
