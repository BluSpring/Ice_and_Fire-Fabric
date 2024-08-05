package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.fabric.FabricClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class BlockItemWithRender extends BlockItem {
    public BlockItemWithRender(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerRenderer();
        }
    }

    private void registerRenderer() {
        FabricClientUtils.registerBlockItemRenderer(this);
    }
}
