package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.IceAndFire;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IafBiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> HAS_GORGON_TEMPLE = TagKey.create(Registries.BIOME, new ResourceLocation(IceAndFire.MODID, "has_structure/gorgon_temple"));
    public static final TagKey<Biome> HAS_MAUSOLEUM = TagKey.create(Registries.BIOME, new ResourceLocation(IceAndFire.MODID, "has_structure/mausoleum"));
    public static final TagKey<Biome> HAS_GRAVEYARD = TagKey.create(Registries.BIOME, new ResourceLocation(IceAndFire.MODID, "has_structure/graveyard"));


    public IafBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(HAS_GRAVEYARD).addTag(BiomeTags.IS_OVERWORLD);
        tag(HAS_MAUSOLEUM).addTag(BiomeTags.IS_OVERWORLD);
        tag(HAS_GORGON_TEMPLE).addTag(BiomeTags.IS_OVERWORLD);
    }

    @Override
    public String getName() {
        return "Ice and Fire Biome Tags";
    }
}
