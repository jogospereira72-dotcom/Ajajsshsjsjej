package net.ratman.modmenu.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.HitResult;

public class CombatUtils {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    // 1. Calculate distance to entity
    public static double getDistance(Entity entity) {
        if (CLIENT.player == null) return Double.MAX_VALUE;
        return CLIENT.player.distanceTo(entity);
    }

    // 2. Get entity bounding box
    public static Box getEntityBox(Entity entity) {
        return entity.getBoundingBox();
    }

    // 3. Check if entity is visible (not behind walls)
    public static boolean isEntityVisible(Entity entity) {
        if (CLIENT.player == null) return false;
        Vec3d playerEyes = CLIENT.player.getEyePos();
        Vec3d entityEyes = new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ()).add(0, entity.getHeight() / 2, 0);
        return CLIENT.world.raycast(new RaycastContext(playerEyes, entityEyes, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, CLIENT.player)).getType() == HitResult.Type.MISS
            || getDistance(entity) < 2.0;
    }

    // 4. Get angle to entity (yaw)
    public static float getYawToEntity(Entity entity) {
        if (CLIENT.player == null) return 0;
        Vec3d diff = new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ()).subtract(new net.minecraft.util.math.Vec3d(CLIENT.player.getX(), CLIENT.player.getY(), CLIENT.player.getZ()));
        return (float) Math.atan2(diff.z, diff.x) * 180 / (float) Math.PI - 90;
    }

    // 5. Get angle to entity (pitch)
    public static float getPitchToEntity(Entity entity) {
        if (CLIENT.player == null) return 0;
        Vec3d diff = entity.getEyePos().subtract(CLIENT.player.getEyePos());
        double distance = diff.length();
        return (float) (-Math.asin(diff.y / distance) * 180 / Math.PI);
    }

    // 6. Linear interpolation for smooth aiming
    public static float lerp(float start, float end, float factor) {
        return start + (end - start) * factor;
    }

    // 7. Normalize angle to -180 to 180 range
    public static float normalizeAngle(float angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    // 8. Check if entity is player
    public static boolean isPlayer(Entity entity) {
        return entity instanceof PlayerEntity && entity != CLIENT.player;
    }

    // 9. Check if entity is alive
    public static boolean isEntityAlive(Entity entity) {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity) entity).isAlive();
        }
        return entity.isAlive();
    }

    // 10. Get entity color based on state (friend/enemy/neutral)
    public static int getEntityColor(Entity entity) {
        if (!isEntityAlive(entity)) return 0xFF808080; // gray for dead
        if (isPlayer(entity)) return 0xFF00FF00;        // green for players
        return 0xFFFFFF00;                              // yellow for mobs
    }

    // 11. Calculate predicted position (lead targeting)
    public static Vec3d getPredictedPosition(Entity entity, float ticks) {
        Vec3d currentPos = new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ());
        Vec3d velocity = entity.getVelocity();
        return currentPos.add(velocity.multiply(ticks / 20.0));
    }

    // 12. Get entity head position
    public static Vec3d getEntityHeadPos(Entity entity) {
        return entity.getEyePos();
    }

    // 13. Get entity center position
    public static Vec3d getEntityCenterPos(Entity entity) {
        return new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ()).add(0, entity.getHeight() / 2, 0);
    }

    // 14. Get entity feet position
    public static Vec3d getEntityFeetPos(Entity entity) {
        return new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ());
    }

    // 15. Check if player is looking at entity
    public static boolean isLookingAtEntity(Entity entity, float tolerance) {
        if (CLIENT.player == null) return false;
        float currentYaw = CLIENT.player.getYaw();
        float yawToEntity = getYawToEntity(entity);
        float diffYaw = Math.abs(normalizeAngle(currentYaw - yawToEntity));
        return diffYaw < tolerance;
    }

    // 16. Get all nearby entities
    public static java.util.List<Entity> getNearbyEntities(double range) {
        java.util.List<Entity> entities = new java.util.ArrayList<>();
        if (CLIENT.world == null || CLIENT.player == null) return entities;

        for (Entity entity : CLIENT.world.getEntities()) {
            if (entity == CLIENT.player) continue;
            if (getDistance(entity) <= range) {
                entities.add(entity);
            }
        }
        return entities;
    }

    // 17. Get nearest enemy
    public static Entity getNearestEnemy(double range) {
        Entity nearest = null;
        double nearestDist = range;

        for (Entity entity : getNearbyEntities(range)) {
            if (isPlayer(entity) && isEntityAlive(entity)) {
                double dist = getDistance(entity);
                if (dist < nearestDist) {
                    nearest = entity;
                    nearestDist = dist;
                }
            }
        }
        return nearest;
    }

    // 18. Snap to entity
    public static void snapToEntity(Entity entity) {
        if (CLIENT.player == null) return;
        float yaw = getYawToEntity(entity);
        float pitch = getPitchToEntity(entity);
        CLIENT.player.setYaw(yaw);
        CLIENT.player.setPitch(Math.max(-90, Math.min(90, pitch)));
    }

    // 19. Get brightness factor (fullbright calculation)
    public static float getFullBrightFactor() {
        if (CLIENT.world == null) return 1.0f;
        return 1.0f; // return value to multiply light level by
    }

    // 20. Calculate damage to entity (utility for future damage features)
    public static float calculateDamage(LivingEntity attacker, LivingEntity target) {
        if (attacker == null || target == null) return 0;
        float baseDamage = 5.0f; // default sword damage
        float health = target.getHealth();
        return Math.min(baseDamage, health); // can't overkill
    }
}
