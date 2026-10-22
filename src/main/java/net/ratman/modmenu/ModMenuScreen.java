package net.ratman.modmenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Click;
import net.minecraft.text.Text;
import net.ratman.modmenu.features.AimbotFeature;
import net.ratman.modmenu.features.AttackSwapFeature;
import net.ratman.modmenu.features.ESPFeature;
import net.ratman.modmenu.features.FullBrightFeature;
import net.ratman.modmenu.features.XRayFeature;

public class ModMenuScreen extends Screen {
    private int menuX, menuY, menuWidth = 200, menuHeight = 450;
    private boolean dragging = false;
    private int dragOffsetX = 0, dragOffsetY = 0;

    private boolean aimbotEnabled = false;
    private boolean espEnabled = false;
    private boolean attackSwapEnabled = false;
    private boolean xrayEnabled = false;
    private boolean fullbrightEnabled = false;

    public ModMenuScreen() {
        super(Text.literal("Mod Menu"));
        this.menuX = 20;
        this.menuY = 20;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderDarkening(context);

        // semi-transparent background panel
        context.fill(menuX, menuY, menuX + menuWidth, menuY + menuHeight, 0xAA1a1a1a);
        // border
        context.fill(menuX, menuY, menuX + menuWidth, menuY + 2, 0xFF00FF00);
        context.fill(menuX, menuY, menuX + 2, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX + menuWidth - 2, menuY, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX, menuY + menuHeight - 2, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);

        // title
        context.drawTextWithShadow(this.textRenderer, "MOD MENU", menuX + 10, menuY + 8, 0x00FF00);

        // buttons
        int btnY = menuY + 30;
        int btnX = menuX + 10;
        int btnW = 180;
        int btnH = 20;

        // Aimbot toggle
        drawButton(context, btnX, btnY, btnW, btnH, "Aimbot: " + (aimbotEnabled ? "ON" : "OFF"), aimbotEnabled ? 0xFF00FF00 : 0xFF8800);
        btnY += 25;

        // ESP toggle
        drawButton(context, btnX, btnY, btnW, btnH, "ESP: " + (espEnabled ? "ON" : "OFF"), espEnabled ? 0xFF00FF00 : 0xFF0088);
        btnY += 25;

        // Attack Swap toggle
        drawButton(context, btnX, btnY, btnW, btnH, "Attack Swap: " + (attackSwapEnabled ? "ON" : "OFF"), attackSwapEnabled ? 0xFF00FF00 : 0xFF0088FF);
        btnY += 25;

        // X-Ray toggle
        drawButton(context, btnX, btnY, btnW, btnH, "X-Ray: " + (xrayEnabled ? "ON" : "OFF"), xrayEnabled ? 0xFF00FF00 : 0xFFFFAA00);
        btnY += 25;

        // Full Bright toggle
        drawButton(context, btnX, btnY, btnW, btnH, "FullBright: " + (fullbrightEnabled ? "ON" : "OFF"), fullbrightEnabled ? 0xFF00FF00 : 0xFFFFFF00);
        btnY += 25;

        // Settings button
        drawButton(context, btnX, btnY, btnW, btnH, "Settings", 0xFFFF00);
        btnY += 25;

        // Close button
        drawButton(context, btnX, btnY, btnW, btnH, "Close (M)", 0xFF444444);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawButton(DrawContext context, int x, int y, int w, int h, String text, int color) {
        context.fill(x, y, x + w, y + h, color);
        context.drawCenteredTextWithShadow(this.textRenderer, text, x + w / 2, y + h / 2 - 4, 0xFFFFFF);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return true;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubleClick) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();
        if (button == 0) {
            int btnY = menuY + 30;
            int btnX = menuX + 10;
            int btnW = 180;
            int btnH = 20;

            // Aimbot click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                aimbotEnabled = !aimbotEnabled;
                AimbotFeature.getInstance().enabled = aimbotEnabled;
                return true;
            }
            btnY += 25;

            // ESP click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                espEnabled = !espEnabled;
                ESPFeature.getInstance().enabled = espEnabled;
                return true;
            }
            btnY += 25;

            // Attack Swap click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                attackSwapEnabled = !attackSwapEnabled;
                AttackSwapFeature.getInstance().enabled = attackSwapEnabled;
                return true;
            }
            btnY += 25;

            // X-Ray click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                xrayEnabled = !xrayEnabled;
                XRayFeature.getInstance().enabled = xrayEnabled;
                return true;
            }
            btnY += 25;

            // FullBright click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                fullbrightEnabled = !fullbrightEnabled;
                FullBrightFeature.getInstance().enabled = fullbrightEnabled;
                FullBrightFeature.getInstance().applyBrightness(this.client);
                return true;
            }
            btnY += 25;

            // Settings click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                return true;
            }
            btnY += 25;

            // Close click
            if (mouseX > btnX && mouseX < btnX + btnW && mouseY > btnY && mouseY < btnY + btnH) {
                this.close();
                return true;
            }
        }
        return super.mouseClicked(click, doubleClick);
    }

    @Override
    public void close() {
        this.client.setScreen(null);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
