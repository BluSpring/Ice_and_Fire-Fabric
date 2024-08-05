package com.github.alexthe666.iceandfire.entity.tile;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

public class TileEntityDragonforgeBrick extends BlockEntity {

    public TileEntityDragonforgeBrick(BlockPos pos, BlockState state) {
        super(IafTileEntityRegistry.DRAGONFORGE_BRICK.get(), pos, state);
    }

    static {
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
            if (blockEntity.getConnectedTileEntity() != null) {
                return ItemStorage.SIDED.find(blockEntity.getConnectedTileEntity().getLevel(), blockEntity.getConnectedTileEntity().getBlockPos(), direction);
            }

            return null;
        }, IafTileEntityRegistry.DRAGONFORGE_BRICK.get());
    }

    private BlockEntity getConnectedTileEntity() {
        for (Direction facing : Direction.values()) {
            if (level.getBlockEntity(worldPosition.relative(facing)) instanceof TileEntityDragonforge) {
                return level.getBlockEntity(worldPosition.relative(facing));
            }
        }
        return null;
    }

}
