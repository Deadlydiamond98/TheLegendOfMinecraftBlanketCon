package net.deadlydiamond98.screen;

import net.deadlydiamond98.ZeldaCraft;
import net.deadlydiamond98.networking.ZeldaClientPackets;
import net.deadlydiamond98.screen_handlers.MagicWorkbenchScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class MagicWorkbenchScreen extends HandledScreen<MagicWorkbenchScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(ZeldaCraft.MOD_ID, "textures/gui/magic_workbench.png");

    private boolean narrow;

    private float displayedMana;

    private TextFieldWidget switchIdField;

    public MagicWorkbenchScreen(MagicWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.x = (width - backgroundWidth) / 2;
        this.titleX = 29;
        this.displayedMana = 0;

        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;

        this.switchIdField = new TextFieldWidget(this.textRenderer, i + 101, j + 27, 58, 16, Text.literal("ID"));
        this.switchIdField.setMaxLength(20);
        this.switchIdField.setVisible(false);
        this.switchIdField.setChangedListener(this::onSwitchIdChanged);
        this.addDrawableChild(this.switchIdField);
    }

    public void handledScreenTick() {
        super.handledScreenTick();
        this.switchIdField.tick();

        this.switchIdField.setVisible(this.handler.shouldShowTextBox());
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);

        if (this.switchIdField.isVisible()) {
            this.switchIdField.render(context, mouseX, mouseY, delta);
            ZeldaClientPackets.updateMagicWorkbench(this.switchIdField.getText());
        }
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        renderManaBar(context, delta, mouseX, mouseY, i, j);
    }

    private void onSwitchIdChanged(String newText) {
        this.handler.setSwitchId(newText);
    }

    private void renderManaBar(DrawContext context, float delta, int mouseX, int mouseY, int i, int j) {
        int currentMana = client.player.getMana();
        int maxMana = client.player.getMaxMana();

        this.displayedMana = MathHelper.lerp(delta * 0.25f, displayedMana, currentMana);

        if (this.displayedMana > maxMana) {
            this.displayedMana = maxMana;
        }

        int filledHeight = (int) ((this.displayedMana / (float) maxMana) * 28) + 1;

        context.drawTexture(TEXTURE,
                i + 16, j + 29 + (28 - filledHeight),
                182, 28 - filledHeight,
                6, filledHeight,
                256, 256
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.player.closeHandledScreen();
        }

        return this.switchIdField.keyPressed(keyCode, scanCode, modifiers) || this.switchIdField.isActive() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {

    }

    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.narrow || super.mouseClicked(mouseX, mouseY, button);
    }

    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
    }

    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
    }
}