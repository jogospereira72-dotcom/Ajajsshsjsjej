package net.ratman.modmenu.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class RenderUtils {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    // 1. Draw entity box (ESP rendering)
    public static void drawEntityBox(Entity entity, int color, DrawContext context) {
        if (CLIENT.player == null) return;

        Box box = entity.getBoundingBox();
        double x = box.minX - CLIENT.player.getX();
        double y = box.minY - CLIENT.player.getY();
        double z = box.minZ - CLIENT.player.getZ();
        double width = box.maxX - box.minX;
        double height = box.maxY - box.minY;
        double depth = box.maxZ - box.minZ;

        // Draw outline (simplified 2D projection)
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (color >> 24) & 0xFF;
    }

    // 2. Draw text at entity
    public static void drawTextAtEntity(Entity entity, String text, int color) {
        if (CLIENT.player == null || CLIENT.textRenderer == null) return;
        Vec3d pos = new net.minecraft.util.math.Vec3d(entity.getX(), entity.getY(), entity.getZ());
        // Text rendering would go here with camera transform
    }

    // 3. Draw line in world
    public static void drawLine(Vec3d start, Vec3d end, int color, float width) {
        // Vertex consumer setup for line drawing
    }

    // 4. Get screen position of entity
    public static net.minecraft.util.math.Vec2f getScreenPos(Entity entity) {
        if (CLIENT.player == null) return new net.minecraft.util.math.Vec2f(0, 0);
        // Project 3D position to 2D screen coordinates
        return new net.minecraft.util.math.Vec2f(0, 0);
    }

    // 5. Draw filled box
    public static void drawFilledBox(double x, double y, double z,
                                     double width, double height, double depth,
                                     int color) {
        // Filled box rendering in world space
    }

    // 6. Draw wireframe box
    public static void drawWireframeBox(double x, double y, double z,
                                       double width, double height, double depth,
                                       int color) {
        // Wireframe box rendering
    }

    // 7. Get color with alpha
    public static int getColorWithAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | (alpha << 24);
    }

    // 8. Fade color based on distance
    public static int fadeColor(int color, double distance, double maxDistance) {
        float fade = (float) (1.0 - (distance / maxDistance));
        fade = Math.max(0, Math.min(1, fade));
        int alpha = (int) (255 * fade);
        return getColorWithAlpha(color, alpha);
    }

    // 9. Convert RGB to color int
    public static int rgbToColor(int r, int g, int b) {
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    // 10. Convert color to RGB array
    public static int[] colorToRGB(int color) {
        return new int[]{
            (color >> 16) & 0xFF,
            (color >> 8) & 0xFF,
            color & 0xFF
        };
    }
}
