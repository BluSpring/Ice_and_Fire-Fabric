package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.tile.IceAndFireTEISR;
import io.github.fabricators_of_create.porting_lib.common.util.Lazy;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
        Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new IceAndFireTEISR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

        BuiltinItemRendererRegistry.INSTANCE.register(this, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
            renderer.get().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
        }));
    }
}
