package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.datagen.tags.*;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

import java.util.concurrent.atomic.AtomicReference;


public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var generator = fabricDataGenerator.createPack();
        generator.addProvider((output, lookupProvider) -> new RegistryDataGenerator(output, lookupProvider));
        generator.addProvider((output, lookupProvider) -> new BannerPatternTagGenerator(output, lookupProvider));
        generator.addProvider((output, lookupProvider) -> new POITagGenerator(output, lookupProvider));
        generator.addProvider((output, lookupProvider) -> new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Ice and Fire"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES)))
        );
        generator.addProvider((output, lookupProvider) -> new IafBiomeTagGenerator(output, lookupProvider, null));
        generator.addProvider((output, lookupProvider) -> new AtlasGenerator(output, null));
        AtomicReference<BlockTagProvider> blocktags = new AtomicReference<>();
        generator.addProvider((output, lookupProvider) -> {
            var provider = new IafBlockTags(output, lookupProvider);
            blocktags.set(provider);
            return provider;
        });
        generator.addProvider((output, lookupProvider) -> new IafItemTags(output, lookupProvider, blocktags.get().contentsGetter()));
        generator.addProvider((output, lookupProvider) -> new IafEntityTags(output, lookupProvider));
        generator.addProvider((output, lookupProvider) -> new IafRecipes(output));

        generator.addProvider(IafDamageRegistry.IafDamageTypeTagsProvider::new);
    }
}
