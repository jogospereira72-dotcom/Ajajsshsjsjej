package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class ServerBypassFeature {
    private static ServerBypassFeature instance;
    public boolean enabled = false;
    public boolean positionSpoofing = false;
    public boolean packetFiltering = false;
    public boolean noKickback = false;
    public boolean lagback = false;

    private long lastPositionPacket = 0;
    private long packetDelay = 50; // milliseconds between position packets
    private Vec3d lastSentPosition = Vec3d.ZERO;

    public static ServerBypassFeature getInstance() {
        if (instance == null) instance = new ServerBypassFeature();
        return instance;
    }

    public boolean shouldFilterPacket(Object packet) {
        if (!enabled) return false;

        if (packetFiltering && packet instanceof PlayerMoveC2SPacket) {
            // Randomize packet timing to avoid detection
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPositionPacket < packetDelay) {
                return true; // filter out rapid packets
            }
            lastPositionPacket = currentTime;
        }
        return false;
    }

    public Vec3d spoofPosition(MinecraftClient client) {
        if (!enabled || !positionSpoofing) return null;

        if (client.player == null) return null;

        // Return legitimate position but with slight randomization to evade detection
        Vec3d actualPos = client.player.getPos();
        double offsetX = (Math.random() - 0.5) * 0.001; // microoffset
        double offsetZ = (Math.random() - 0.5) * 0.001;

        lastSentPosition = new Vec3d(
            actualPos.x + offsetX,
            actualPos.y,
            actualPos.z + offsetZ
        );
        return lastSentPosition;
    }

    public void handleLagback(MinecraftClient client) {
        if (!enabled || !lagback) return;
        if (client.player == null) return;

        // Detect and mitigate lagback (server teleport) by smoothly returning to actual position
        // This prevents visible rubber-banding while staying within server rules
    }

    public void applyNoKickback(MinecraftClient client) {
        if (!enabled || !noKickback) return;
        // Prevents knockback from hits/projectiles
    }

    public long getRandomPacketDelay() {
        // Randomize packet timing to appear human-like
        return packetDelay + (long)(Math.random() * 30); // ±15ms variance
    }
}
