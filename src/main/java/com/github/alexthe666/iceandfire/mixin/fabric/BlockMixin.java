package com.github.alexthe666.iceandfire.mixin.fabric;

import io.github.fabricators_of_create.porting_lib.blocks.extensions.OnExplodedBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin implements OnExplodedBlock {
}
