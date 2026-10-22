package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ESPFeature {
    private static ESPFeature instance;
    public boolean enabled = false;
    public boolean wallhack = true;
    public float range = 150.0f;
    public int boxColor = 0xFF00FF00;

    public static ESPFeature getInstance() {
        if (instance == null) instance = new ESPFeature();
        return instance;
    }

    public boolean shouldRenderESP(MinecraftClient client, Entity entity) {
        if (!enabled) return false;
        if (!(entity instanceof PlayerEntity) || entity == client.player) return false;
        if (!((PlayerEntity) entity).isAlive()) return false;

        double dist = client.player.distanceTo(entity);
        return dist <= range;
    }

    public int getBoxColor(Entity entity) {
        return boxColor;
    }
}
