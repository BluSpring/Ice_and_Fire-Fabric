package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.datagen.IafPlacedFeatures;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.feature.*;
import com.github.alexthe666.iceandfire.world.gen.*;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.LevelData;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

public class IafWorldRegistry {
    public static final LazyRegistrar<Feature<?>> FEATURES = LazyRegistrar.create(BuiltInRegistries.FEATURE, IceAndFire.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_ROOST = register("fire_dragon_roost", () -> new WorldGenFireDragonRoosts(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_ROOST = register("ice_dragon_roost", () -> new WorldGenIceDragonRoosts(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_ROOST = register("lightning_dragon_roost", () -> new WorldGenLightningDragonRoosts(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_CAVE = register("fire_dragon_cave", () -> new WorldGenFireDragonCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> new WorldGenIceDragonCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_CAVE = register("lightning_dragon_cave",
            () -> new WorldGenLightningDragonCave(NoneFeatureConfiguration.CODEC));
    //TODO: Should be a structure
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CYCLOPS_CAVE = register("cyclops_cave", () -> new WorldGenCyclopsCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PIXIE_VILLAGE = register("pixie_village", () -> new WorldGenPixieVillage(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SIREN_ISLAND = register("siren_island", () -> new WorldGenSirenIsland(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HYDRA_CAVE = register("hydra_cave", () -> new WorldGenHydraCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_DESERT = register("myrmex_hive_desert", () -> new WorldGenMyrmexHive(false, false, NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_JUNGLE = register("myrmex_hive_jungle", () -> new WorldGenMyrmexHive(false, true, NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DEATH_WORM = register("spawn_death_worm", () -> new SpawnDeathWorm(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_L = register("spawn_dragon_skeleton_lightning",
            () -> new SpawnDragonSkeleton(IafEntityRegistry.LIGHTNING_DRAGON.get(), NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_F = register("spawn_dragon_skeleton_fire",
            () -> new SpawnDragonSkeleton(IafEntityRegistry.FIRE_DRAGON.get(), NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_I = register("spawn_dragon_skeleton_ice",
            () -> new SpawnDragonSkeleton(IafEntityRegistry.ICE_DRAGON.get(), NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_HIPPOCAMPUS = register("spawn_hippocampus", () -> new SpawnHippocampus(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_SEA_SERPENT = register("spawn_sea_serpent", () -> new SpawnSeaSerpent(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_STYMPHALIAN_BIRD = register("spawn_stymphalian_bird", () -> new SpawnStymphalianBird(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_WANDERING_CYCLOPS = register("spawn_wandering_cyclops", () -> new SpawnWanderingCyclops(NoneFeatureConfiguration.CODEC));


    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(final String name, final Supplier<? extends F> supplier) {
        return FEATURES.register(name, supplier);
    }

    public static boolean isFarEnoughFromSpawn(final LevelAccessor level, final BlockPos position) {
        LevelData spawnPoint = level.getLevelData();
        BlockPos spawnRelative = new BlockPos(spawnPoint.getXSpawn(), position.getY(), spawnPoint.getYSpawn());
        return !spawnRelative.closerThan(position, IafConfig.dangerousWorldGenDistanceLimit);
    }

    public static boolean isFarEnoughFromDangerousGen(final ServerLevelAccessor level, final BlockPos position, final String id) {
        return isFarEnoughFromDangerousGen(level, position, id, IafWorldData.FeatureType.SURFACE);
    }

    public static boolean isFarEnoughFromDangerousGen(final ServerLevelAccessor level, final BlockPos position, final String id, final IafWorldData.FeatureType type) {
        IafWorldData data = IafWorldData.get(level.getLevel());
        return data.check(type, position, id);
    }

    // Only a global variable because it's too bothersome to add it to the method call (alternative: method returns identifier or null)
    private static List<String> ADDED_FEATURES;

    public static void addFeatures() {
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.fireLilyBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.VEGETAL_DECORATION, IafPlacedFeatures.PLACED_FIRE_LILY);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.lightningLilyBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.VEGETAL_DECORATION, IafPlacedFeatures.PLACED_LIGHTNING_LILY);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.frostLilyBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.VEGETAL_DECORATION, IafPlacedFeatures.PLACED_FROST_LILY);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.oreGenBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.UNDERGROUND_ORES, IafPlacedFeatures.PLACED_SILVER_ORE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.sapphireBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.UNDERGROUND_ORES, IafPlacedFeatures.PLACED_SAPPHIRE_ORE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.fireDragonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_FIRE_DRAGON_ROOST);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.lightningDragonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_LIGHTNING_DRAGON_ROOST);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.iceDragonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_ICE_DRAGON_ROOST);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.fireDragonCaveBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, IafPlacedFeatures.PLACED_FIRE_DRAGON_CAVE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.lightningDragonCaveBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, IafPlacedFeatures.PLACED_LIGHTNING_DRAGON_CAVE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.iceDragonCaveBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, IafPlacedFeatures.PLACED_ICE_DRAGON_CAVE);


        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.cyclopsCaveBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_CYCLOPS_CAVE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.pixieBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_PIXIE_VILLAGE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.hydraBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_HYDRA_CAVE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.desertMyrmexBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_MYRMEX_HIVE_DESERT);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.jungleMyrmexBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_MYRMEX_HIVE_JUNGLE);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.sirenBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SIREN_ISLAND);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.deathwormBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_DEATH_WORM);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.wanderingCyclopsBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_WANDERING_CYCLOPS);

        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.lightningDragonSkeletonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_L);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.fireDragonSkeletonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_F);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.iceDragonSkeletonBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_I);

        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.hippocampusBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_HIPPOCAMPUS);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.seaSerpentBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_SEA_SERPENT);
        BiomeModifications.addFeature(ctx -> safelyTestBiome(BiomeConfig.stymphalianBiomes, ctx.getBiomeRegistryEntry()), GenerationStep.Decoration.SURFACE_STRUCTURES, IafPlacedFeatures.PLACED_SPAWN_STYMPHALIAN_BIRD);
    }

    private static boolean safelyTestBiome(Pair<String, SpawnBiomeData> entry, Holder<Biome> biomeHolder) {
        try {
            return BiomeConfig.test(entry, biomeHolder);
        } catch (Exception e) {
            return false;
        }
    }

}
