package net.deadlydiamond98.mixin.client;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public abstract class SplashMixin {

    // this mixin just adds new splash screen texts for fun

    @Shadow @Final private List<String> splashTexts;

    @Unique
    private final List<String> newSplashText = List.of(
            "It's dangerous to go alone, take this!",
            "Hey! Listen!",
            "I am Error",
            "Well excuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuse me Princess!",
            "You've met with a terrible fate, haven't you?",
            "We don't have lamp oil or rope, but we most certainly have bombs!",
            "Watch out for falling stars!",
            "This is Zelda I N S P I R E D... plz don't sue :<",
            "OOOOOOOOOOOOOOOOOOOHHHHHHHHHHHHHH",
            "If all else fails, use fire.",
            "Gee, it sure is BORING around here...",
            "");


    @Inject(method = "apply", at = @At("TAIL"))
    private void apply(List<String> list, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        this.splashTexts.addAll(newSplashText);
    }
}
