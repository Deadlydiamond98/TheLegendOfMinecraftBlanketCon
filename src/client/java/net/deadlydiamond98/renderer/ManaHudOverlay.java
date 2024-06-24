package net.deadlydiamond98.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.util.ManaPlayerData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public class ManaHudOverlay implements HudRenderCallback {

    private static final Identifier Filled_Mana = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/mana_full.png");
    private static final Identifier Empty_Mana = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/mana_empty.png");
    private static float displayedMana = 0.0f;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        if (client == null || client.player == null || client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE
                || client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
            return;
        }

        int mana_x = (width / 2) + 100;
        int mana_y = height - 50;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Empty_Mana);
        drawContext.drawTexture(Empty_Mana, mana_x, mana_y, 0, 0, 16, 42, 16, 42);

        ManaPlayerData userM = ((ManaPlayerData) client.player);
        int currentMana = userM.getMana();
        int maxMana = userM.getMaxMana();

        displayedMana = MathHelper.lerp(tickDelta * 0.25f, displayedMana, currentMana);

        int filledHeight = (int) ((displayedMana / (float) maxMana) * 32);
        RenderSystem.setShaderTexture(0, Filled_Mana);
        drawContext.drawTexture(Filled_Mana, mana_x + 4, mana_y + 5 + (32 - filledHeight), 0, 32 - filledHeight, 8, filledHeight, 8, 32);
    }
}
