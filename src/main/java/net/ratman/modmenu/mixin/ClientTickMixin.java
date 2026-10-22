package net.ratman.modmenu.mixin;

import net.minecraft.client.MinecraftClient;
import net.ratman.modmenu.features.AimbotFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ClientTickMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        AimbotFeature.getInstance().tick(MinecraftClient.getInstance());
    }
}
