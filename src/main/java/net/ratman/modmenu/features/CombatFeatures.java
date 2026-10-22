package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.entity.EntityPose;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityPose;
import net.minecraft.component.DataComponentTypes;

public class CombatFeatures {
    private static CombatFeatures instance;

    // 1. KillAura
    public boolean killAuraEnabled = false;
    public float killAuraRange = 5.0f;

    // 2. Velocity
    public boolean velocityEnabled = false;
    public float velocityReduction = 0.5f;

    // 3. Reach
    public boolean reachEnabled = false;
    public float reachDistance = 6.0f;

    // 4. Criticals
    public boolean criticalsEnabled = false;

    // 5. Strafe
    public boolean strafeEnabled = false;

    // 6. NoFall
    public boolean noFallEnabled = false;

    // 7. AutoBlock
    public boolean autoBlockEnabled = false;

    // 8. AutoHeal
    public boolean autoHealEnabled = false;
    public float healThreshold = 10.0f;

    // 9. Forcefield
    public boolean forcefieldEnabled = false;
    public float forcefieldRange = 10.0f;

    // 10. FastPlace
    public boolean fastPlaceEnabled = false;

    // 11. Phase
    public boolean phaseEnabled = false;

    // 12. Scaffold
    public boolean scaffoldEnabled = false;

    // 13. Swim
    public boolean swimEnabled = false;

    // 14. NameTags
    public boolean nameTagsEnabled = false;

    // 15. AntiKnockback
    public boolean antiKnockbackEnabled = false;

    public static CombatFeatures getInstance() {
        if (instance == null) instance = new CombatFeatures();
        return instance;
    }

    public void tick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        if (killAuraEnabled) tickKillAura(client);
        if (noFallEnabled) tickNoFall(client);
        if (autoBlockEnabled) tickAutoBlock(client);
        if (autoHealEnabled) tickAutoHeal(client);
        if (forcefieldEnabled) tickForcefield(client);
        if (fastPlaceEnabled) tickFastPlace(client);
        if (phaseEnabled) tickPhase(client);
        if (scaffoldEnabled) tickScaffold(client);
        if (swimEnabled) tickSwim(client);
    }

    private void tickKillAura(MinecraftClient client) {
        if (client.player == null) return;
        Entity target = getClosestEntity(client, killAuraRange);
        if (target instanceof LivingEntity && target != client.player) {
            client.interactionManager.attackEntity(client.player, target);
            client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
        }
    }

    private void tickNoFall(MinecraftClient client) {
        if (client.player != null && client.player.getPose() == EntityPose.GLIDING) {
            client.player.setVelocity(client.player.getVelocity().x, 0, client.player.getVelocity().z);
        }
    }

    private void tickAutoBlock(MinecraftClient client) {
        if (client.player != null) {
            client.options.useKey.setPressed(true);
        }
    }

    private void tickAutoHeal(MinecraftClient client) {
        if (client.player != null && client.player.getHealth() < healThreshold) {
            // Use healing item from hotbar
            if (client.player.getMainHandStack().contains(DataComponentTypes.FOOD)) {
                client.options.useKey.setPressed(true);
            }
        }
    }

    private void tickForcefield(MinecraftClient client) {
        if (client.player == null) return;
        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != client.player) {
                double dist = client.player.distanceTo(entity);
                if (dist < forcefieldRange) {
                    client.interactionManager.attackEntity(client.player, entity);
                }
            }
        }
    }

    private void tickFastPlace(MinecraftClient client) {
        if (client.player != null) {
            // Increase block placement speed by reducing delay
        }
    }

    private void tickPhase(MinecraftClient client) {
        // Phase through blocks (noclip variant)
        if (client.player != null) {
            client.player.noClip = true;
        }
    }

    private void tickScaffold(MinecraftClient client) {
        // Auto-place blocks below player while moving
        if (client.player != null && client.player.isOnGround()) {
            // Build platform beneath
        }
    }

    private void tickSwim(MinecraftClient client) {
        if (client.player != null && client.player.isInFluid()) {
            client.player.setVelocity(
                client.player.getVelocity().x,
                client.player.getVelocity().y + 0.1,
                client.player.getVelocity().z
            );
        }
    }

    private Entity getClosestEntity(MinecraftClient client, float range) {
        Entity closest = null;
        double closestDist = range;
        if (client.world == null) return null;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            double dist = client.player.distanceTo(entity);
            if (dist < closestDist) {
                closest = entity;
                closestDist = dist;
            }
        }
        return closest;
    }

    public boolean shouldApplyVelocity() {
        return velocityEnabled;
    }

    public boolean shouldApplyReach() {
        return reachEnabled;
    }

    public boolean shouldApplyCriticals() {
        return criticalsEnabled;
    }

    public boolean shouldApplyStrafe() {
        return strafeEnabled;
    }
}
