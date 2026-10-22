package net.ratman.modmenu.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BlockModelPart;
import net.ratman.modmenu.features.XRayFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelRenderer.class)
public class BlockRenderMixin {
    @Inject(method = "render(Lnet/minecraft/client/render/model/BlockModelPart;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/render/VertexConsumer;ZLnet/minecraft/util/math/random/Random;JI)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(BlockModelPart model, BlockState state,
                               net.minecraft.util.math.BlockPos pos,
                               net.minecraft.client.render.VertexConsumer vertexConsumer,
                               boolean cull, net.minecraft.util.math.random.Random random,
                               long seed, int overlay, CallbackInfo ci) {
        if (XRayFeature.getInstance().enabled) {
            if (!XRayFeature.getInstance().shouldRenderBlock(state.getBlock())) {
                ci.cancel();
            }
        }
    }
}
