package net.ratman.modmenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.text.Text;
import net.ratman.modmenu.features.*;

public class ModMenuScreenAdvanced extends Screen {
    private int menuX, menuY, menuWidth = 250, menuHeight = 500;
    private int currentTab = 0; // 0=Combat, 1=Movement, 2=Bypass

    // Combat toggles
    private boolean aimbotEnabled = false;
    private boolean espEnabled = false;
    private boolean killAuraEnabled = false;
    private boolean reachEnabled = false;
    private boolean velocityEnabled = false;

    // Movement toggles
    private boolean flightEnabled = false;
    private boolean levitationEnabled = false;
    private boolean jetpackEnabled = false;
    private boolean speedEnabled = false;
    private boolean swimEnabled = false;

    // Bypass toggles
    private boolean bypassEnabled = false;
    private boolean positionSpoofing = false;
    private boolean packetFiltering = false;

    public ModMenuScreenAdvanced() {
        super(Text.literal("Advanced Mod Menu"));
        this.menuX = 20;
        this.menuY = 20;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderDarkening(context);

        // Panel background
        context.fill(menuX, menuY, menuX + menuWidth, menuY + menuHeight, 0xAA1a1a1a);
        // Border
        context.fill(menuX, menuY, menuX + menuWidth, menuY + 2, 0xFF00FF00);
        context.fill(menuX, menuY, menuX + 2, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX + menuWidth - 2, menuY, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX, menuY + menuHeight - 2, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);

        // Title
        context.drawTextWithShadow(this.textRenderer, "ADVANCED MENU", menuX + 10, menuY + 8, 0x00FF00);

        // Tab buttons
        int tabY = menuY + 25;
        int tabX = menuX + 10;
        int tabW = 70;
        int tabH = 18;

        drawButton(context, tabX, tabY, tabW, tabH, "Combat", currentTab == 0 ? 0xFF00FF00 : 0xFF666666);
        tabX += 75;
        drawButton(context, tabX, tabY, tabW, tabH, "Movement", currentTab == 1 ? 0xFF00FF00 : 0xFF666666);
        tabX += 75;
        drawButton(context, tabX, tabY, tabW, tabH, "Bypass", currentTab == 2 ? 0xFF00FF00 : 0xFF666666);

        // Content area
        int contentY = menuY + 50;
        int contentX = menuX + 10;
        int contentW = menuWidth - 20;

        if (currentTab == 0) {
            renderCombatTab(context, contentX, contentY, contentW);
        } else if (currentTab == 1) {
            renderMovementTab(context, contentX, contentY, contentW);
        } else if (currentTab == 2) {
            renderBypassTab(context, contentX, contentY, contentW);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderCombatTab(DrawContext context, int x, int y, int w) {
        int btnH = 18;
        int btnY = y;

        drawButton(context, x, btnY, w, btnH, "Aimbot: " + (aimbotEnabled ? "ON" : "OFF"),
                   aimbotEnabled ? 0xFF00FF00 : 0xFF8800);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "ESP: " + (espEnabled ? "ON" : "OFF"),
                   espEnabled ? 0xFF00FF00 : 0xFF0088);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "KillAura: " + (killAuraEnabled ? "ON" : "OFF"),
                   killAuraEnabled ? 0xFF00FF00 : 0xFFFF0000);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Reach: " + (reachEnabled ? "ON" : "OFF"),
                   reachEnabled ? 0xFF00FF00 : 0xFFFFAA00);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Velocity: " + (velocityEnabled ? "ON" : "OFF"),
                   velocityEnabled ? 0xFF00FF00 : 0xFF00AAFF);
    }

    private void renderMovementTab(DrawContext context, int x, int y, int w) {
        int btnH = 18;
        int btnY = y;

        drawButton(context, x, btnY, w, btnH, "Flight: " + (flightEnabled ? "ON" : "OFF"),
                   flightEnabled ? 0xFF00FF00 : 0xFF8800);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Levitation: " + (levitationEnabled ? "ON" : "OFF"),
                   levitationEnabled ? 0xFF00FF00 : 0xFFFF00FF);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Jetpack: " + (jetpackEnabled ? "ON" : "OFF"),
                   jetpackEnabled ? 0xFF00FF00 : 0xFF0088FF);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Speed: " + (speedEnabled ? "ON" : "OFF"),
                   speedEnabled ? 0xFF00FF00 : 0xFFFFFF00);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Swim: " + (swimEnabled ? "ON" : "OFF"),
                   swimEnabled ? 0xFF00FF00 : 0xFF00AAAA);
    }

    private void renderBypassTab(DrawContext context, int x, int y, int w) {
        int btnH = 18;
        int btnY = y;

        drawButton(context, x, btnY, w, btnH, "Bypass: " + (bypassEnabled ? "ON" : "OFF"),
                   bypassEnabled ? 0xFF00FF00 : 0xFF8800);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Position Spoof: " + (positionSpoofing ? "ON" : "OFF"),
                   positionSpoofing ? 0xFF00FF00 : 0xFFFFAA00);
        btnY += 20;
        drawButton(context, x, btnY, w, btnH, "Packet Filter: " + (packetFiltering ? "ON" : "OFF"),
                   packetFiltering ? 0xFF00FF00 : 0xFF00AAFF);

        context.drawTextWithShadow(this.textRenderer, "Anti-detection features", x, btnY + 25, 0xFFCCCCCC);
    }

    private void drawButton(DrawContext context, int x, int y, int w, int h, String text, int color) {
        context.fill(x, y, x + w, y + h, color);
        context.drawCenteredTextWithShadow(this.textRenderer, text, x + w / 2, y + h / 2 - 4, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubleClick) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();
        if (button == 0) {
            int tabY = menuY + 25;
            int tabX = menuX + 10;
            int tabW = 70;
            int tabH = 18;

            // Tab selection
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 0;
                return true;
            }
            tabX += 75;
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 1;
                return true;
            }
            tabX += 75;
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 2;
                return true;
            }

            // Feature toggles
            int contentY = menuY + 50;
            int contentX = menuX + 10;
            int contentW = menuWidth - 20;
            int btnH = 18;
            int btnY = contentY;

            if (currentTab == 0) {
                // Combat toggles
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    aimbotEnabled = !aimbotEnabled;
                    AimbotFeature.getInstance().enabled = aimbotEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    espEnabled = !espEnabled;
                    ESPFeature.getInstance().enabled = espEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    killAuraEnabled = !killAuraEnabled;
                    CombatFeatures.getInstance().killAuraEnabled = killAuraEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    reachEnabled = !reachEnabled;
                    CombatFeatures.getInstance().reachEnabled = reachEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    velocityEnabled = !velocityEnabled;
                    CombatFeatures.getInstance().velocityEnabled = velocityEnabled;
                    return true;
                }
            } else if (currentTab == 1) {
                // Movement toggles
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    flightEnabled = !flightEnabled;
                    MovementFeature.getInstance().flightEnabled = flightEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    levitationEnabled = !levitationEnabled;
                    MovementFeature.getInstance().levitationEnabled = levitationEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    jetpackEnabled = !jetpackEnabled;
                    MovementFeature.getInstance().jetpackEnabled = jetpackEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    speedEnabled = !speedEnabled;
                    MovementFeature.getInstance().speedEnabled = speedEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    swimEnabled = !swimEnabled;
                    MovementFeature.getInstance().jetpackEnabled = swimEnabled;
                    return true;
                }
            } else if (currentTab == 2) {
                // Bypass toggles
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    bypassEnabled = !bypassEnabled;
                    ServerBypassFeature.getInstance().enabled = bypassEnabled;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    positionSpoofing = !positionSpoofing;
                    ServerBypassFeature.getInstance().positionSpoofing = positionSpoofing;
                    return true;
                }
                btnY += 20;
                if (mouseX > contentX && mouseX < contentX + contentW && mouseY > btnY && mouseY < btnY + btnH) {
                    packetFiltering = !packetFiltering;
                    ServerBypassFeature.getInstance().packetFiltering = packetFiltering;
                    return true;
                }
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
