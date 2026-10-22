package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class AimbotFeature {
    private static AimbotFeature instance;
    public boolean enabled = false;
    public float smoothness = 0.3f;
    public float range = 50.0f;

    public static AimbotFeature getInstance() {
        if (instance == null) instance = new AimbotFeature();
        return instance;
    }

    public void tick(MinecraftClient client) {
        if (!enabled || client.player == null || client.world == null) return;

        LivingEntity target = findNearestTarget(client);
        if (target != null) {
            aimAtTarget(client, target);
        }
    }

    private LivingEntity findNearestTarget(MinecraftClient client) {
        LivingEntity closest = null;
        double closestDist = range;

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof PlayerEntity) || entity == client.player) continue;
            if (!((LivingEntity) entity).isAlive()) continue;

            double dist = client.player.distanceTo(entity);
            if (dist < closestDist) {
                closest = (LivingEntity) entity;
                closestDist = dist;
            }
        }
        return closest;
    }

    private void aimAtTarget(MinecraftClient client, LivingEntity target) {
        Vec3d playerEyes = client.player.getEyePos();
        Vec3d targetEyes = target.getEyePos();
        Vec3d diff = targetEyes.subtract(playerEyes);

        double distance = diff.length();
        double yaw = Math.atan2(diff.z, diff.x) * 180 / Math.PI - 90;
        double pitch = -Math.asin(diff.y / distance) * 180 / Math.PI;

        float currentYaw = client.player.getYaw();
        float currentPitch = client.player.getPitch();

        float targetYaw = (float) yaw;
        float targetPitch = (float) pitch;

        float newYaw = lerp(currentYaw, targetYaw, smoothness);
        float newPitch = lerp(currentPitch, targetPitch, smoothness);

        client.player.setYaw(newYaw);
        client.player.setPitch(Math.max(-90, Math.min(90, newPitch)));
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
