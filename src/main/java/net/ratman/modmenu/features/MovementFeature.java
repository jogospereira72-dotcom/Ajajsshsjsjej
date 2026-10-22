package net.ratman.modmenu.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;

public class MovementFeature {
    private static MovementFeature instance;
    public boolean flightEnabled = false;
    public boolean levitationEnabled = false;
    public boolean jetpackEnabled = false;
    public boolean speedEnabled = false;

    public float flightSpeed = 0.1f;
    public float jetpackPower = 0.15f;
    public float speedMultiplier = 1.5f;

    public static MovementFeature getInstance() {
        if (instance == null) instance = new MovementFeature();
        return instance;
    }

    public void tick(MinecraftClient client) {
        if (client.player == null) return;

        if (flightEnabled) {
            applyFlight(client);
        }
        if (levitationEnabled) {
            applyLevitation(client);
        }
        if (jetpackEnabled) {
            applyJetpack(client);
        }
        if (speedEnabled) {
            applySpeed(client);
        }
    }

    private void applyFlight(MinecraftClient client) {
        client.player.getAbilities().flying = true;
        client.player.getAbilities().allowFlying = true;
        client.player.getAbilities().setFlySpeed(flightSpeed);
    }

    private void applyLevitation(MinecraftClient client) {
        if (!client.player.hasStatusEffect(StatusEffects.LEVITATION)) {
            client.player.addStatusEffect(
                new net.minecraft.entity.effect.StatusEffectInstance(
                    StatusEffects.LEVITATION, 1, 1, false, false
                )
            );
        }
    }

    private void applyJetpack(MinecraftClient client) {
        if (client.options.jumpKey.isPressed()) {
            client.player.setVelocity(
                client.player.getVelocity().x,
                client.player.getVelocity().y + jetpackPower,
                client.player.getVelocity().z
            );
        }
    }

    private void applySpeed(MinecraftClient client) {
        net.minecraft.util.math.Vec3d velocity = client.player.getVelocity();
        client.player.setVelocity(
            velocity.x * speedMultiplier,
            velocity.y,
            velocity.z * speedMultiplier
        );
    }
}
