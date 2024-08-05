package com.github.alexthe666.iceandfire.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockDragonBone extends RotatedPillarBlock implements IDragonProof {

    public BlockDragonBone() {
        super(
            BlockBehaviour.Properties
                .of()
                .mapColor(MapColor.STONE)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.WOOD)
                .strength(30F, 500F)
                .requiresCorrectToolForDrops()
                .pushReaction(PushReaction.BLOCK)
		);

    }
}
