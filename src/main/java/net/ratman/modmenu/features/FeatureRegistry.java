package net.ratman.modmenu.features;

public class FeatureRegistry {
    // Combat Features (15)
    public static class CombatFeatureSet {
        public boolean killAura = false;
        public boolean aimAssist = false;
        public boolean triggerBot = false;
        public boolean autoClicker = false;
        public boolean velocity = false;
        public boolean reach = false;
        public boolean hitbox = false;
        public boolean criticals = false;
        public boolean autoTotem = false;
        public boolean autoWeapon = false;
        public boolean antiKnockback = false;
        public boolean autoArmor = false;
        public boolean fastPlace = false;
        public boolean fastBreak = false;
        public boolean timer = false;
    }

    // Movement Features (7)
    public static class MovementFeatureSet {
        public boolean fly = false;
        public boolean speed = false;
        public boolean longJump = false;
        public boolean step = false;
        public boolean noFall = false;
        public boolean jesus = false;
        public boolean noSlow = false;
    }

    // Visual Features (10)
    public static class VisualFeatureSet {
        public boolean esp = false;
        public boolean playerESP = false;
        public boolean tracers = false;
        public boolean chestESP = false;
        public boolean xray = false;
        public boolean freecam = false;
        public boolean fullbright = false;
        public boolean nameTagsOverride = false;
        public boolean targetHUD = false;
        public boolean hud = false;
    }

    // Utility Features (8)
    public static class UtilityFeatureSet {
        public boolean blink = false;
        public boolean inventoryMove = false;
        public boolean chestStealer = false;
        public boolean antiBot = false;
        public boolean clickGUI = false;
        public boolean disabler = false;
        public boolean phase = false;
    }

    private static FeatureRegistry instance;
    public CombatFeatureSet combat = new CombatFeatureSet();
    public MovementFeatureSet movement = new MovementFeatureSet();
    public VisualFeatureSet visual = new VisualFeatureSet();
    public UtilityFeatureSet utility = new UtilityFeatureSet();

    public static FeatureRegistry getInstance() {
        if (instance == null) instance = new FeatureRegistry();
        return instance;
    }

    public boolean isAnyFeatureEnabled() {
        return isAnyCombatEnabled() || isAnyMovementEnabled() || isAnyVisualEnabled() || isAnyUtilityEnabled();
    }

    private boolean isAnyCombatEnabled() {
        return combat.killAura || combat.aimAssist || combat.triggerBot || combat.autoClicker ||
               combat.velocity || combat.reach || combat.hitbox || combat.criticals ||
               combat.autoTotem || combat.autoWeapon || combat.antiKnockback || combat.autoArmor ||
               combat.fastPlace || combat.fastBreak || combat.timer ||
               AdvancedFeatures.getInstance().trajectoryPredictor || AdvancedFeatures.getInstance().hitPrediction ||
               AdvancedFeatures.getInstance().predictiveAim || AdvancedFeatures.getInstance().multiTarget;
    }

    private boolean isAnyMovementEnabled() {
        return movement.fly || movement.speed || movement.longJump || movement.step ||
               movement.noFall || movement.jesus || movement.noSlow;
    }

    private boolean isAnyVisualEnabled() {
        return visual.esp || visual.playerESP || visual.tracers || visual.chestESP ||
               visual.xray || visual.freecam || visual.fullbright || visual.nameTagsOverride ||
               visual.targetHUD || visual.hud;
    }

    private boolean isAnyUtilityEnabled() {
        return utility.blink || utility.inventoryMove || utility.chestStealer || utility.antiBot ||
               utility.clickGUI || utility.disabler || utility.phase;
    }
}
