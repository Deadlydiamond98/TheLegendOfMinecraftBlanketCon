package net.deadlydiamond98.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.util.ColorAndAlphaInterpolator;
import net.deadlydiamond98.util.ManaPlayerData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public class ManaHudOverlay implements HudRenderCallback {

    private static final ColorAndAlphaInterpolator colorInterpolator = new ColorAndAlphaInterpolator( 0xFF00FF5C, 0xFFFFFFFF,
            0.0f, 1.0f, 5.0f);
    private static final Identifier Filled_Mana = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/mana_full.png");
    private static final Identifier Filled_Mana_Second = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/mana_full_second.png");
    private static final Identifier Empty_Mana = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/mana_empty.png");


    private static float displayedMana = 0.0f;
    private static boolean transitionMinMax = false;
    private static float currentAlpha = 1.0f;
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        MatrixStack matrices = drawContext.getMatrices();
        TextRenderer textRenderer = client.textRenderer;


        // if not in survival or adventure, don't render the magic bar
        if (client.player == null || client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE ||
                client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            return;
            // note to self, add config to disable bar too
        }

        matrices.push();

        matrices.translate(0, 0, 0); // Placeholder for when I implement Configs

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        int mana_x = (width / 2) + 100;
        int mana_y = height - 45;

        // render the empty texture
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Empty_Mana);
        drawContext.drawTexture(Empty_Mana, mana_x, mana_y, 0, 0, 16, 42, 16, 42);

        // get values for determining where the level will be at
        ManaPlayerData userM = ((ManaPlayerData) client.player);
        int currentMana = userM.getMana();
        int maxMana = userM.getMaxMana();

        // lerp so it has a nice smooth looking transition
        displayedMana = MathHelper.lerp(tickDelta * 0.25f, displayedMana, currentMana);
        int filledHeight = (int) ((displayedMana / (float) maxMana) * 33);


        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // update alpha when at max
        currentAlpha = colorInterpolator.updateAndGetAlpha(tickDelta);

        // reg mana bar
        RenderSystem.setShaderTexture(0, Filled_Mana);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, currentAlpha);
        drawContext.drawTexture(Filled_Mana, mana_x + 4, mana_y + 4 + (33 - filledHeight), 0, 33 - filledHeight, 8, filledHeight, 8, 33);

        // max mana bar
        RenderSystem.setShaderTexture(0, Filled_Mana_Second);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f - currentAlpha);
        drawContext.drawTexture(Filled_Mana_Second, mana_x + 4, mana_y + 4 + (33 - filledHeight), 0, 33 - filledHeight, 8, filledHeight, 8, 33);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();

        matrices.push();

        String displayZeros = "";

        // zeros to display so numbers are always 3 digits
        if (Math.round(displayedMana) < 10) {
            displayZeros = "00";
        } else if (Math.round(displayedMana) < 100) {
            displayZeros = "0";

        }

        // enable transitions if max / not max
        if (currentMana == maxMana && !transitionMinMax) {
            colorInterpolator.resetTransition(colorInterpolator.updateAndGetColor(tickDelta), 0xFF00FF5C, currentAlpha, 0.0f, 40.0f);
            transitionMinMax = true;
        } else if (currentMana != maxMana && transitionMinMax) {
            colorInterpolator.resetTransition(colorInterpolator.updateAndGetColor(tickDelta), 0xFFFFFFFF, currentAlpha, 1.0f, 40.0f);
            transitionMinMax = false;
        }

        // number text
        Text filledAmountText =
                Text.literal(displayZeros + Math.round(displayedMana) + " / " + maxMana).setStyle(Style.EMPTY.withFont(ZeldaCraft.ZELDA_FONT));
        matrices.translate(mana_x + 16, mana_y + 35, 0);
        matrices.scale(0.75f, 0.75f, 0.75f);

        // change color of text
        int currentColor = colorInterpolator.updateAndGetColor(tickDelta);

        drawContext.drawText(textRenderer, filledAmountText, 0, 0, currentColor, false);

        matrices.pop();
        matrices.pop();
    }
}
