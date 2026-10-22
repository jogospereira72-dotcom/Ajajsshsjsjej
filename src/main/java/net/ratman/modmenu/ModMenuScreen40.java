package net.ratman.modmenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.text.Text;
import net.ratman.modmenu.features.FeatureRegistry;
import net.ratman.modmenu.features.FeatureExecutor;

public class ModMenuScreen40 extends Screen {
    private int menuX, menuY, menuWidth = 280, menuHeight = 550;
    private int currentTab = 0; // 0=Combat, 1=Movement, 2=Visual, 3=Utility
    private FeatureRegistry registry = FeatureRegistry.getInstance();

    public ModMenuScreen40() {
        super(Text.literal("Combat Suite — 40 Features"));
        this.menuX = 15;
        this.menuY = 15;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderDarkening(context);

        // Panel
        context.fill(menuX, menuY, menuX + menuWidth, menuY + menuHeight, 0xAA1a1a1a);
        // Border
        context.fill(menuX, menuY, menuX + menuWidth, menuY + 2, 0xFF00FF00);
        context.fill(menuX, menuY, menuX + 2, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX + menuWidth - 2, menuY, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);
        context.fill(menuX, menuY + menuHeight - 2, menuX + menuWidth, menuY + menuHeight, 0xFF00FF00);

        // Title
        context.drawTextWithShadow(this.textRenderer, "40 FEATURES", menuX + 10, menuY + 8, 0x00FF00);

        // Tabs
        int tabY = menuY + 25;
        int tabX = menuX + 10;
        int tabW = 65;
        int tabH = 16;

        drawTabButton(context, tabX, tabY, tabW, tabH, "Combat", 0, mouseX, mouseY);
        tabX += 70;
        drawTabButton(context, tabX, tabY, tabW, tabH, "Move", 1, mouseX, mouseY);
        tabX += 70;
        drawTabButton(context, tabX, tabY, tabW, tabH, "Visual", 2, mouseX, mouseY);
        tabX += 70;
        drawTabButton(context, tabX, tabY, tabW, tabH, "Util", 3, mouseX, mouseY);

        // Content
        int contentY = menuY + 48;
        int contentX = menuX + 10;
        int contentW = menuWidth - 20;

        if (currentTab == 0) renderCombat(context, contentX, contentY, contentW);
        else if (currentTab == 1) renderMovement(context, contentX, contentY, contentW);
        else if (currentTab == 2) renderVisual(context, contentX, contentY, contentW);
        else if (currentTab == 3) renderUtility(context, contentX, contentY, contentW);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawTabButton(DrawContext context, int x, int y, int w, int h, String text, int tabIndex, int mouseX, int mouseY) {
        boolean hover = mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
        int color = (currentTab == tabIndex) ? 0xFF00FF00 : (hover ? 0xFF888888 : 0xFF444444);
        context.fill(x, y, x + w, y + h, color);
        context.drawCenteredTextWithShadow(this.textRenderer, text, x + w / 2, y + h / 2 - 3, 0xFFFFFF);
    }

    private void renderCombat(DrawContext context, int x, int y, int w) {
        String[] names = {"KillAura", "AimAssist", "TriggerBot", "AutoClicker", "Velocity",
                         "Reach", "Hitbox", "Criticals", "AutoTotem", "AutoWeapon",
                         "AntiKB", "AutoArmor", "FastPlace", "FastBreak", "Timer"};
        boolean[] states = {registry.combat.killAura, registry.combat.aimAssist, registry.combat.triggerBot,
                           registry.combat.autoClicker, registry.combat.velocity, registry.combat.reach,
                           registry.combat.hitbox, registry.combat.criticals, registry.combat.autoTotem,
                           registry.combat.autoWeapon, registry.combat.antiKnockback, registry.combat.autoArmor,
                           registry.combat.fastPlace, registry.combat.fastBreak, registry.combat.timer};
        renderFeatureList(context, x, y, w, names, states);
    }

    private void renderMovement(DrawContext context, int x, int y, int w) {
        String[] names = {"Fly", "Speed", "LongJump", "Step", "NoFall", "Jesus", "NoSlow"};
        boolean[] states = {registry.movement.fly, registry.movement.speed, registry.movement.longJump,
                           registry.movement.step, registry.movement.noFall, registry.movement.jesus,
                           registry.movement.noSlow};
        renderFeatureList(context, x, y, w, names, states);
    }

    private void renderVisual(DrawContext context, int x, int y, int w) {
        String[] names = {"ESP", "PlayerESP", "Tracers", "ChestESP", "XRay", "Freecam",
                         "Fullbright", "NameTags", "TargetHUD", "HUD"};
        boolean[] states = {registry.visual.esp, registry.visual.playerESP, registry.visual.tracers,
                           registry.visual.chestESP, registry.visual.xray, registry.visual.freecam,
                           registry.visual.fullbright, registry.visual.nameTagsOverride, registry.visual.targetHUD,
                           registry.visual.hud};
        renderFeatureList(context, x, y, w, names, states);
    }

    private void renderUtility(DrawContext context, int x, int y, int w) {
        String[] names = {"Blink", "InventoryMove", "ChestStealer", "AntiBot", "ClickGUI", "Disabler", "Phase"};
        boolean[] states = {registry.utility.blink, registry.utility.inventoryMove, registry.utility.chestStealer,
                           registry.utility.antiBot, registry.utility.clickGUI, registry.utility.disabler,
                           registry.utility.phase};
        renderFeatureList(context, x, y, w, names, states);
    }

    private void renderFeatureList(DrawContext context, int x, int y, int w, String[] names, boolean[] states) {
        int btnH = 14;
        for (int i = 0; i < names.length; i++) {
            int color = states[i] ? 0xFF00FF00 : 0xFF8800;
            context.fill(x, y, x + w, y + btnH, color);
            context.drawTextWithShadow(this.textRenderer, (states[i] ? "✓ " : "  ") + names[i],
                                      x + 5, y + 2, 0xFFFFFF);
            y += 16;
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubleClick) {
        double mouseX = click.x();
        double mouseY = click.y();
        int button = click.button();
        if (button == 0) {
            // Tab selection
            int tabY = menuY + 25;
            int tabX = menuX + 10;
            int tabW = 65;
            int tabH = 16;

            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 0;
                return true;
            }
            tabX += 70;
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 1;
                return true;
            }
            tabX += 70;
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 2;
                return true;
            }
            tabX += 70;
            if (mouseX > tabX && mouseX < tabX + tabW && mouseY > tabY && mouseY < tabY + tabH) {
                currentTab = 3;
                return true;
            }

            // Feature clicks
            int contentY = menuY + 48;
            int contentX = menuX + 10;
            int contentW = menuWidth - 20;
            int btnH = 14;

            if (currentTab == 0) {
                if (clickFeature(mouseX, mouseY, contentX, contentY, contentW, btnH, 0)) { registry.combat.killAura = !registry.combat.killAura; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 16, contentW, btnH, 1)) { registry.combat.aimAssist = !registry.combat.aimAssist; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 32, contentW, btnH, 2)) { registry.combat.triggerBot = !registry.combat.triggerBot; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 48, contentW, btnH, 3)) { registry.combat.autoClicker = !registry.combat.autoClicker; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 64, contentW, btnH, 4)) { registry.combat.velocity = !registry.combat.velocity; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 80, contentW, btnH, 5)) { registry.combat.reach = !registry.combat.reach; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 96, contentW, btnH, 6)) { registry.combat.hitbox = !registry.combat.hitbox; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 112, contentW, btnH, 7)) { registry.combat.criticals = !registry.combat.criticals; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 128, contentW, btnH, 8)) { registry.combat.autoTotem = !registry.combat.autoTotem; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 144, contentW, btnH, 9)) { registry.combat.autoWeapon = !registry.combat.autoWeapon; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 160, contentW, btnH, 10)) { registry.combat.antiKnockback = !registry.combat.antiKnockback; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 176, contentW, btnH, 11)) { registry.combat.autoArmor = !registry.combat.autoArmor; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 192, contentW, btnH, 12)) { registry.combat.fastPlace = !registry.combat.fastPlace; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 208, contentW, btnH, 13)) { registry.combat.fastBreak = !registry.combat.fastBreak; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 224, contentW, btnH, 14)) { registry.combat.timer = !registry.combat.timer; return true; }
            } else if (currentTab == 1) {
                if (clickFeature(mouseX, mouseY, contentX, contentY, contentW, btnH, 0)) { registry.movement.fly = !registry.movement.fly; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 16, contentW, btnH, 1)) { registry.movement.speed = !registry.movement.speed; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 32, contentW, btnH, 2)) { registry.movement.longJump = !registry.movement.longJump; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 48, contentW, btnH, 3)) { registry.movement.step = !registry.movement.step; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 64, contentW, btnH, 4)) { registry.movement.noFall = !registry.movement.noFall; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 80, contentW, btnH, 5)) { registry.movement.jesus = !registry.movement.jesus; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 96, contentW, btnH, 6)) { registry.movement.noSlow = !registry.movement.noSlow; return true; }
            } else if (currentTab == 2) {
                if (clickFeature(mouseX, mouseY, contentX, contentY, contentW, btnH, 0)) { registry.visual.esp = !registry.visual.esp; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 16, contentW, btnH, 1)) { registry.visual.playerESP = !registry.visual.playerESP; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 32, contentW, btnH, 2)) { registry.visual.tracers = !registry.visual.tracers; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 48, contentW, btnH, 3)) { registry.visual.chestESP = !registry.visual.chestESP; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 64, contentW, btnH, 4)) { registry.visual.xray = !registry.visual.xray; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 80, contentW, btnH, 5)) { registry.visual.freecam = !registry.visual.freecam; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 96, contentW, btnH, 6)) { registry.visual.fullbright = !registry.visual.fullbright; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 112, contentW, btnH, 7)) { registry.visual.nameTagsOverride = !registry.visual.nameTagsOverride; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 128, contentW, btnH, 8)) { registry.visual.targetHUD = !registry.visual.targetHUD; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 144, contentW, btnH, 9)) { registry.visual.hud = !registry.visual.hud; return true; }
            } else if (currentTab == 3) {
                if (clickFeature(mouseX, mouseY, contentX, contentY, contentW, btnH, 0)) { registry.utility.blink = !registry.utility.blink; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 16, contentW, btnH, 1)) { registry.utility.inventoryMove = !registry.utility.inventoryMove; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 32, contentW, btnH, 2)) { registry.utility.chestStealer = !registry.utility.chestStealer; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 48, contentW, btnH, 3)) { registry.utility.antiBot = !registry.utility.antiBot; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 64, contentW, btnH, 4)) { registry.utility.clickGUI = !registry.utility.clickGUI; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 80, contentW, btnH, 5)) { registry.utility.disabler = !registry.utility.disabler; return true; }
                if (clickFeature(mouseX, mouseY, contentX, contentY + 96, contentW, btnH, 6)) { registry.utility.phase = !registry.utility.phase; return true; }
            }
        }
        return super.mouseClicked(click, doubleClick);
    }

    private boolean clickFeature(double mouseX, double mouseY, int x, int y, int w, int h, int index) {
        return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
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
