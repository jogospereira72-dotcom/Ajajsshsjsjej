package net.ratman.modmenu.features;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class XRayFeature {
    private static XRayFeature instance;
    public boolean enabled = false;

    private static final Block[] ORE_BLOCKS = {
        Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
        Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
        Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
        Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
        Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
        Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
        Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
        Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
        Blocks.NETHER_GOLD_ORE,
        Blocks.NETHER_QUARTZ_ORE,
        Blocks.ANCIENT_DEBRIS
    };

    public static XRayFeature getInstance() {
        if (instance == null) instance = new XRayFeature();
        return instance;
    }

    public boolean shouldRenderBlock(Block block) {
        if (!enabled) return true;

        // render ores and transparent blocks, hide stone/dirt
        if (isOre(block)) return true;
        if (isTransparent(block)) return true;

        return false;
    }

    private boolean isOre(Block block) {
        for (Block ore : ORE_BLOCKS) {
            if (block == ore) return true;
        }
        return false;
    }

    private boolean isTransparent(Block block) {
        return block == Blocks.AIR ||
               block == Blocks.CAVE_AIR ||
               block == Blocks.VOID_AIR ||
               block == Blocks.WATER ||
               block == Blocks.LAVA;
    }
}
