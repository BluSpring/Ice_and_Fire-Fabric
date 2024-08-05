package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.IceAndFire;
import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, IafConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, IafPlacedFeatures::bootstrap)
            .add(Registries.STRUCTURE, IafStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, IafStructureSets::bootstrap)
            .add(Registries.PROCESSOR_LIST, IafProcessorLists::bootstrap)
            .add(Registries.TEMPLATE_POOL, IafStructurePieces::bootstrap);
            //.add(ForgeRegistries.Keys.BIOME_MODIFIERS, IafBiomeModifierSerializers::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", IceAndFire.MODID));
    }
}

