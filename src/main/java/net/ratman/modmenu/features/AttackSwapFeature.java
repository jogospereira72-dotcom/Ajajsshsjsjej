package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;

public class AttackSwapFeature {
    private static AttackSwapFeature instance;
    public boolean enabled = false;
    public int weaponSlot = 0;
    public int blockSlot = 8;

    public static AttackSwapFeature getInstance() {
        if (instance == null) instance = new AttackSwapFeature();
        return instance;
    }

    public void onAttack(MinecraftClient client, Entity target) {
        if (!enabled || client.player == null) return;

        if (isSword(client.player.getInventory().getStack(weaponSlot))) {
            client.player.getInventory().selectedSlot = weaponSlot;
        }
    }

    public void onPlaceBlock(MinecraftClient client) {
        if (!enabled || client.player == null) return;
        client.player.getInventory().selectedSlot = blockSlot;
    }

    private boolean isSword(net.minecraft.item.ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    public void swapToSlot(int slot) {
        if (slot >= 0 && slot <= 8) {
            MinecraftClient.getInstance().player.getInventory().selectedSlot = slot;
        }
    }
}
