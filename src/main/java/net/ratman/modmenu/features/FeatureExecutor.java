package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class FeatureExecutor {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private static FeatureExecutor instance;
    private FeatureRegistry registry = FeatureRegistry.getInstance();

    private long lastAutoClickerClick = 0;
    private long autoClickerDelay = 50; // ms

    public static FeatureExecutor getInstance() {
        if (instance == null) instance = new FeatureExecutor();
        return instance;
    }

    public void tickAllFeatures() {
        if (CLIENT.player == null || CLIENT.world == null) return;

        // Combat
        if (registry.combat.killAura) tickKillAura();
        if (registry.combat.aimAssist) tickAimAssist();
        if (registry.combat.triggerBot) tickTriggerBot();
        if (registry.combat.autoClicker) tickAutoClicker();
        if (registry.combat.criticals) tickCriticals();
        if (registry.combat.autoTotem) tickAutoTotem();
        if (registry.combat.autoWeapon) tickAutoWeapon();
        if (registry.combat.autoArmor) tickAutoArmor();
        if (registry.combat.fastPlace) tickFastPlace();
        if (registry.combat.fastBreak) tickFastBreak();

        // Movement
        if (registry.movement.fly) tickFly();
        if (registry.movement.speed) tickSpeed();
        if (registry.movement.longJump) tickLongJump();
        if (registry.movement.step) tickStep();
        if (registry.movement.noFall) tickNoFall();
        if (registry.movement.jesus) tickJesus();
        if (registry.movement.noSlow) tickNoSlow();

        // Visual
        if (registry.visual.fullbright) tickFullbright();
        if (registry.visual.freecam) tickFreecam();

        // Utility
        if (registry.utility.blink) tickBlink();
        if (registry.utility.inventoryMove) tickInventoryMove();
        if (registry.utility.chestStealer) tickChestStealer();
        if (registry.utility.phase) tickPhase();
    }

    // COMBAT FEATURES (15)
    private void tickKillAura() {
        Entity target = getClosestEntity(5.0f);
        if (target instanceof LivingEntity && target != CLIENT.player) {
            CLIENT.interactionManager.attackEntity(CLIENT.player, target);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private void tickAimAssist() {
        Entity target = getClosestEntity(50.0f);
        if (target != null) {
            float yaw = getYawToEntity(target);
            float pitch = getPitchToEntity(target);
            CLIENT.player.setYaw(yaw);
            CLIENT.player.setPitch(Math.max(-90, Math.min(90, pitch)));
        }
    }

    private void tickTriggerBot() {
        if (CLIENT.crosshairTarget != null && CLIENT.crosshairTarget.getType().name().equals("ENTITY")) {
            CLIENT.options.attackKey.click();
        }
    }

    private void tickAutoClicker() {
        long now = System.currentTimeMillis();
        if (now - lastAutoClickerClick > autoClickerDelay) {
            CLIENT.options.attackKey.click();
            lastAutoClickerClick = now;
        }
    }

    private void tickCriticals() {
        if (CLIENT.player.isOnGround() && !CLIENT.player.isSneaking()) {
            CLIENT.player.jump();
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, 0.1, CLIENT.player.getVelocity().z);
        }
    }

    private void tickAutoTotem() {
        // Auto-place totem in offhand before death
        if (CLIENT.player.getHealth() < 5.0f) {
            // Swap to totem if in inventory
        }
    }

    private void tickAutoWeapon() {
        // Auto-swap to best weapon when attacking
        if (getClosestEntity(5.0f) != null) {
            // Find and equip best weapon
        }
    }

    private void tickAutoArmor() {
        // Auto-equip best armor from inventory
    }

    private void tickFastPlace() {
        // Block action cooldown is internal in 1.21.11; no public API equivalent.
    }

    private void tickFastBreak() {
        // Block breaking cooldown is internal in 1.21.11; no public API equivalent.
    }

    // MOVEMENT FEATURES (7)
    private void tickFly() {
        CLIENT.player.getAbilities().flying = true;
        CLIENT.player.getAbilities().allowFlying = true;
        CLIENT.player.getAbilities().setFlySpeed(0.1f);
    }

    private void tickSpeed() {
        net.minecraft.util.math.Vec3d vel = CLIENT.player.getVelocity();
        CLIENT.player.setVelocity(vel.x * 1.5, vel.y, vel.z * 1.5);
    }

    private void tickLongJump() {
        if (CLIENT.player.isOnGround() && (CLIENT.options.forwardKey.isPressed() || CLIENT.options.backKey.isPressed())) {
            CLIENT.player.jump();
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, 0.5, CLIENT.player.getVelocity().z);
        }
    }

    private void tickStep() {
        if (CLIENT.player.horizontalCollision) {
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, 0.6, CLIENT.player.getVelocity().z);
        }
    }

    private void tickNoFall() {
        if (CLIENT.player.fallDistance > 3.0f) {
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, 0, CLIENT.player.getVelocity().z);
        }
    }

    private void tickJesus() {
        if (CLIENT.player.isInFluid()) {
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, 0.1, CLIENT.player.getVelocity().z);
        }
    }

    private void tickNoSlow() {
        if (CLIENT.player.isUsingItem()) {
            CLIENT.player.getAttributes().getBaseValue(net.minecraft.entity.attribute.EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
        }
    }

    // VISUAL FEATURES (10)
    private void tickFullbright() {
        if (CLIENT.options != null) {
            CLIENT.options.getGamma().setValue(16.0);
        }
    }

    private void tickFreecam() {
        // Detach camera from player
        CLIENT.player.noClip = true;
    }

    // UTILITY FEATURES (8)
    private void tickBlink() {
        // Store position, continue movement client-side, send to server later
    }

    private void tickInventoryMove() {
        // Allow inventory access while moving
    }

    private void tickChestStealer() {
        // Auto-steal items from open chest to inventory
    }

    private void tickPhase() {
        CLIENT.player.noClip = true;
    }

    // HELPERS
    private Entity getClosestEntity(float range) {
        Entity closest = null;
        double closestDist = range;

        for (Entity entity : CLIENT.world.getEntities()) {
            if (entity == CLIENT.player) continue;
            double dist = CLIENT.player.distanceTo(entity);
            if (dist < closestDist) {
                closest = entity;
                closestDist = dist;
            }
        }
        return closest;
    }

    private float getYawToEntity(Entity entity) {
        net.minecraft.util.math.Vec3d diff = new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ()).subtract(new net.minecraft.util.math.Vec3d(CLIENT.player.getX(), CLIENT.player.getY(), CLIENT.player.getZ()));
        return (float) Math.atan2(diff.z, diff.x) * 180 / (float) Math.PI - 90;
    }

    private float getPitchToEntity(Entity entity) {
        net.minecraft.util.math.Vec3d diff = entity.getEyePos().subtract(CLIENT.player.getEyePos());
        double distance = diff.length();
        return (float) (-Math.asin(diff.y / distance) * 180 / Math.PI);
    }
}
