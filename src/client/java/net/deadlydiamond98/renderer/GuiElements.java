package net.deadlydiamond98.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.magiclib.MagicLib;
import net.deadlydiamond98.util.ZeldaTags;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public class GuiElements implements HudRenderCallback {

    private static final Identifier Bag_Icons = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/bag_gui_icons.png");
    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static float displayedArrows = 0.0f;
    private static float displayedBombs = 0.0f;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MatrixStack matrices = drawContext.getMatrices();

        if (client.player == null || client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE ||
                client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            return;
        }

        matrices.push();

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

//        client.player.getHandItems().forEach(stack -> {
//            if (stack.getItem() instanceof RangedWeaponItem) {
//                renderArrows(width, height, drawContext, tickDelta, matrices);
//            }
//            if (stack.isIn(ZeldaTags.Items.Bombs_Bags)) {
//                renderBombs(width, height, drawContext, tickDelta, matrices);
//            }
//        });

        matrices.pop();
    }

    private void renderBombs(int width, int height, DrawContext drawContext, float tickDelta, MatrixStack matrices) {
        int bomb_x = (width / 2) - 320;
        int bomb_y = height - 42;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Bag_Icons);
        drawContext.drawTexture(Bag_Icons, bomb_x, bomb_y, 0, 0, 13, 13, 39, 13);

        matrices.push();

        int currentBombs = client.player.getMana();
        int maxBombs = client.player.getMaxMana();

        // lerp so it has a nice smooth looking transition
        displayedBombs = MathHelper.lerp(tickDelta * 0.25f, displayedBombs, currentBombs);

        if (displayedBombs > maxBombs) {
            displayedBombs = maxBombs;
        }

        displayText(bomb_x, bomb_y, displayedBombs, drawContext, matrices);

        matrices.pop();
    }

    private void renderArrows(int width, int height, DrawContext drawContext, float tickDelta, MatrixStack matrices) {
        int arrow_x = (width / 2) - 320;
        int arrow_y = height - 42;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Bag_Icons);
        drawContext.drawTexture(Bag_Icons, arrow_x, arrow_y, 13, 0, 13, 13, 39, 13);

        matrices.push();

        int currentArrows = client.player.getMana();
        int maxArrows = client.player.getMaxMana();

        // lerp so it has a nice smooth looking transition
        displayedArrows = MathHelper.lerp(tickDelta * 0.25f, displayedArrows, currentArrows);

        if (displayedArrows > maxArrows) {
            displayedArrows = maxArrows;
        }

        displayText(arrow_x, arrow_y, displayedArrows, drawContext, matrices);

        matrices.pop();
    }

    private void displayText(int x, int y, float displayed, DrawContext drawContext, MatrixStack matrices) {
        String displayZeros = "";

        if (Math.round(displayed) < 10) {
            displayZeros = "00";
        } else if (Math.round(displayed) < 100) {
            displayZeros = "0";

        }

        Text filledAmountText =
                Text.literal(displayZeros + Math.round(displayed)).setStyle(Style.EMPTY.withFont(MagicLib.ZELDA_FONT));
        matrices.translate(x, y, 0);
        matrices.scale(0.75f, 0.75f, 0.75f);

        drawContext.drawText(client.textRenderer, filledAmountText, 0, 0, 0xFFFFFFFF, false);
    }
}
