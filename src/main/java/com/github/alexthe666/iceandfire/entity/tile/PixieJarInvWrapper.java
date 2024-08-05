package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class PixieJarInvWrapper implements SlottedStackStorage {

    private final TileEntityJar tile;
    private final PixieJarStorage storage = new PixieJarStorage();

    public PixieJarInvWrapper(TileEntityJar tile) {
        this.tile = tile;
    }

    public static LazyOptional<SlottedStackStorage> create(TileEntityJar trashCan) {
        return LazyOptional.of(() -> new PixieJarInvWrapper(trashCan));
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        // this.tile.hasProduced = false;
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public SingleSlotStorage<ItemVariant> getSlot(int slot) {
        return storage;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.tile.hasProduced ? new ItemStack(IafItemRegistry.PIXIE_DUST.get()) : ItemStack.EMPTY;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return 0L;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return storage.extract(resource, maxAmount, transaction);
    }

    @Override
    public boolean supportsInsertion() {
        return false;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, int count) {
        return false;
    }

    private class PixieJarStorage implements SingleSlotStorage<ItemVariant> {

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            return 0;
        }

        @Override
        public boolean supportsInsertion() {
            return false;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            var snapshot = new PixieJarSnapshot();
            snapshot.updateSnapshots(transaction);

            if (snapshot.value) {
                snapshot.value = false;
                return 1;
            }

            return 0;
        }

        @Override
        public boolean isResourceBlank() {
            return !tile.hasProduced;
        }

        @Override
        public ItemVariant getResource() {
            return tile.hasProduced ? ItemVariant.of(IafItemRegistry.PIXIE_DUST.get()) : ItemVariant.blank();
        }

        @Override
        public long getAmount() {
            return tile.hasProduced ? 1 : 0;
        }

        @Override
        public long getCapacity() {
            return 1;
        }
    }

    private class PixieJarSnapshot extends SnapshotParticipant<Boolean> {
        private boolean value = PixieJarInvWrapper.this.tile.hasProduced;

        @Override
        protected Boolean createSnapshot() {
            return value;
        }

        @Override
        protected void readSnapshot(Boolean snapshot) {
            value = snapshot;
        }

        @Override
        protected void onFinalCommit() {
            PixieJarInvWrapper.this.tile.hasProduced = value;
        }
    }
}
