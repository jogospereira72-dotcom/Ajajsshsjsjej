package net.ratman.modmenu.features;

public class FullBrightFeature {
    private static FullBrightFeature instance;
    public boolean enabled = false;
    public float brightness = 1.0f;

    public static FullBrightFeature getInstance() {
        if (instance == null) instance = new FullBrightFeature();
        return instance;
    }

    public float getGamma() {
        if (!enabled) return 1.0f;
        return 16.0f; // maximum brightness without shader
    }

    public void applyBrightness(net.minecraft.client.MinecraftClient client) {
        if (!enabled || client.options == null) return;
        client.options.getGamma().setValue(16.0);
    }
}
