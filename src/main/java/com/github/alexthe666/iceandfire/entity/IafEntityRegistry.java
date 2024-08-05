package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class IafEntityRegistry {

    public static final LazyRegistrar<EntityType<?>> ENTITIES = LazyRegistrar.create(BuiltInRegistries.ENTITY_TYPE,
        IceAndFire.MODID);

    public static final RegistryObject<EntityType<EntityDragonPart>> DRAGON_MULTIPART = registerEntity(EntityType.Builder.<EntityDragonPart>of(EntityDragonPart::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "dragon_multipart");
    public static final RegistryObject<EntityType<EntitySlowPart>> SLOW_MULTIPART = registerEntity(EntityType.Builder.<EntitySlowPart>of(EntitySlowPart::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "multipart");
    public static final RegistryObject<EntityType<EntityHydraHead>> HYDRA_MULTIPART = registerEntity(EntityType.Builder.<EntityHydraHead>of(EntityHydraHead::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "hydra_multipart");
    public static final RegistryObject<EntityType<EntityCyclopsEye>> CYCLOPS_MULTIPART = registerEntity(EntityType.Builder.<EntityCyclopsEye>of(EntityCyclopsEye::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "cylcops_multipart");
    public static final RegistryObject<EntityType<EntityDragonEgg>> DRAGON_EGG = registerEntity(EntityType.Builder.of(EntityDragonEgg::new, MobCategory.MISC).sized(0.45F, 0.55F).fireImmune(), "dragon_egg");
    public static final RegistryObject<EntityType<EntityDragonArrow>> DRAGON_ARROW = registerEntity(EntityType.Builder.<EntityDragonArrow>of(EntityDragonArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "dragon_arrow");
    public static final RegistryObject<EntityType<EntityDragonSkull>> DRAGON_SKULL = registerEntity(EntityType.Builder.of(EntityDragonSkull::new, MobCategory.MISC).sized(0.9F, 0.65F), "dragon_skull");
    public static final RegistryObject<EntityType<EntityFireDragon>> FIRE_DRAGON = registerEntity(EntityType.Builder.<EntityFireDragon>of(EntityFireDragon::new, MobCategory.CREATURE).sized(0.78F, 1.2F).fireImmune().clientTrackingRange(10), 256, "fire_dragon");
    public static final RegistryObject<EntityType<EntityIceDragon>> ICE_DRAGON = registerEntity(EntityType.Builder.<EntityIceDragon>of(EntityIceDragon::new, MobCategory.CREATURE).sized(0.78F, 1.2F).clientTrackingRange(10), 256, "ice_dragon");
    public static final RegistryObject<EntityType<EntityLightningDragon>> LIGHTNING_DRAGON = registerEntity(EntityType.Builder.<EntityLightningDragon>of(EntityLightningDragon::new, MobCategory.CREATURE).sized(0.78F, 1.2F).clientTrackingRange(10), 256, "lightning_dragon");
    public static final RegistryObject<EntityType<EntityDragonFireCharge>> FIRE_DRAGON_CHARGE = registerEntity(EntityType.Builder.<EntityDragonFireCharge>of(EntityDragonFireCharge::new, MobCategory.MISC).sized(0.9F, 0.9F), "fire_dragon_charge");
    public static final RegistryObject<EntityType<EntityDragonIceCharge>> ICE_DRAGON_CHARGE = registerEntity(EntityType.Builder.<EntityDragonIceCharge>of(EntityDragonIceCharge::new, MobCategory.MISC).sized(0.9F, 0.9F), "ice_dragon_charge");
    public static final RegistryObject<EntityType<EntityDragonLightningCharge>> LIGHTNING_DRAGON_CHARGE = registerEntity(EntityType.Builder.<EntityDragonLightningCharge>of(EntityDragonLightningCharge::new, MobCategory.MISC).sized(0.9F, 0.9F), "lightning_dragon_charge");
    public static final RegistryObject<EntityType<EntityHippogryphEgg>> HIPPOGRYPH_EGG = registerEntity(EntityType.Builder.<EntityHippogryphEgg>of(EntityHippogryphEgg::new, MobCategory.MISC).sized(0.5F, 0.5F), "hippogryph_egg");
    public static final RegistryObject<EntityType<EntityHippogryph>> HIPPOGRYPH = registerEntity(EntityType.Builder.of(EntityHippogryph::new, MobCategory.CREATURE).sized(1.7F, 1.6F), 128, "hippogryph");
    public static final RegistryObject<EntityType<EntityStoneStatue>> STONE_STATUE = registerEntity(EntityType.Builder.of(EntityStoneStatue::new, MobCategory.CREATURE).sized(0.5F, 0.5F), "stone_statue");
    public static final RegistryObject<EntityType<EntityGorgon>> GORGON = registerEntity(EntityType.Builder.of(EntityGorgon::new, MobCategory.CREATURE).sized(0.8F, 1.99F), "gorgon");
    public static final RegistryObject<EntityType<EntityPixie>> PIXIE = registerEntity(EntityType.Builder.of(EntityPixie::new, MobCategory.CREATURE).sized(0.4F, 0.8F), "pixie");
    public static final RegistryObject<EntityType<EntityCyclops>> CYCLOPS = registerEntity(EntityType.Builder.of(EntityCyclops::new, MobCategory.CREATURE).sized(1.95F, 7.4F).clientTrackingRange(8), "cyclops");
    public static final RegistryObject<EntityType<EntitySiren>> SIREN = registerEntity(EntityType.Builder.of(EntitySiren::new, MobCategory.CREATURE).sized(1.6F, 0.9F), "siren");
    public static final RegistryObject<EntityType<EntityHippocampus>> HIPPOCAMPUS = registerEntity(EntityType.Builder.of(EntityHippocampus::new, MobCategory.CREATURE).sized(1.95F, 0.95F), "hippocampus");
    public static final RegistryObject<EntityType<EntityDeathWorm>> DEATH_WORM = registerEntity(EntityType.Builder.of(EntityDeathWorm::new, MobCategory.CREATURE).sized(0.8F, 0.8F), 128, "deathworm");
    public static final RegistryObject<EntityType<EntityDeathWormEgg>> DEATH_WORM_EGG = registerEntity(EntityType.Builder.<EntityDeathWormEgg>of(EntityDeathWormEgg::new, MobCategory.MISC).sized(0.5F, 0.5F), "deathworm_egg");
    public static final RegistryObject<EntityType<EntityCockatrice>> COCKATRICE = registerEntity(EntityType.Builder.of(EntityCockatrice::new, MobCategory.CREATURE).sized(1.1F, 1F), "cockatrice");
    public static final RegistryObject<EntityType<EntityCockatriceEgg>> COCKATRICE_EGG = registerEntity(EntityType.Builder.<EntityCockatriceEgg>of(EntityCockatriceEgg::new, MobCategory.MISC).sized(0.5F, 0.5F), "cockatrice_egg");
    public static final RegistryObject<EntityType<EntityStymphalianBird>> STYMPHALIAN_BIRD = registerEntity(EntityType.Builder.of(EntityStymphalianBird::new, MobCategory.CREATURE).sized(1.3F, 1.2F), 128, "stymphalian_bird");
    public static final RegistryObject<EntityType<EntityStymphalianFeather>> STYMPHALIAN_FEATHER = registerEntity(EntityType.Builder.<EntityStymphalianFeather>of(EntityStymphalianFeather::new, MobCategory.MISC).sized(0.5F, 0.5F), "stymphalian_feather");
    public static final RegistryObject<EntityType<EntityStymphalianArrow>> STYMPHALIAN_ARROW = registerEntity(EntityType.Builder.<EntityStymphalianArrow>of(EntityStymphalianArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "stymphalian_arrow");
    public static final RegistryObject<EntityType<EntityTroll>> TROLL = registerEntity(EntityType.Builder.of(EntityTroll::new, MobCategory.MONSTER).sized(1.2F, 3.5F), "troll");
    public static final RegistryObject<EntityType<EntityMyrmexWorker>> MYRMEX_WORKER = registerEntity(EntityType.Builder.of(EntityMyrmexWorker::new, MobCategory.CREATURE).sized(0.9F, 0.9F), "myrmex_worker");
    public static final RegistryObject<EntityType<EntityMyrmexSoldier>> MYRMEX_SOLDIER = registerEntity(EntityType.Builder.of(EntityMyrmexSoldier::new, MobCategory.CREATURE).sized(1.2F, 0.95F), "myrmex_soldier");
    public static final RegistryObject<EntityType<EntityMyrmexSentinel>> MYRMEX_SENTINEL = registerEntity(EntityType.Builder.of(EntityMyrmexSentinel::new, MobCategory.CREATURE).sized(1.3F, 1.95F), "myrmex_sentinel");
    public static final RegistryObject<EntityType<EntityMyrmexRoyal>> MYRMEX_ROYAL = registerEntity(EntityType.Builder.of(EntityMyrmexRoyal::new, MobCategory.CREATURE).sized(1.9F, 1.86F), "myrmex_royal");
    public static final RegistryObject<EntityType<EntityMyrmexQueen>> MYRMEX_QUEEN = registerEntity(EntityType.Builder.of(EntityMyrmexQueen::new, MobCategory.CREATURE).sized(2.9F, 1.86F), "myrmex_queen");
    public static final RegistryObject<EntityType<EntityMyrmexEgg>> MYRMEX_EGG = registerEntity(EntityType.Builder.of(EntityMyrmexEgg::new, MobCategory.MISC).sized(0.45F, 0.55F), "myrmex_egg");
    public static final RegistryObject<EntityType<EntityAmphithere>> AMPHITHERE = registerEntity(EntityType.Builder.of(EntityAmphithere::new, MobCategory.CREATURE).sized(2.5F, 1.25F).clientTrackingRange(8), 128, "amphithere");
    public static final RegistryObject<EntityType<EntityAmphithereArrow>> AMPHITHERE_ARROW = registerEntity(EntityType.Builder.<EntityAmphithereArrow>of(EntityAmphithereArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "amphithere_arrow");
    public static final RegistryObject<EntityType<EntitySeaSerpent>> SEA_SERPENT = registerEntity(EntityType.Builder.of(EntitySeaSerpent::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8), 256, "sea_serpent");
    public static final RegistryObject<EntityType<EntitySeaSerpentBubbles>> SEA_SERPENT_BUBBLES = registerEntity(EntityType.Builder.<EntitySeaSerpentBubbles>of(EntitySeaSerpentBubbles::new, MobCategory.MISC).sized(0.9F, 0.9F), "sea_serpent_bubbles");
    public static final RegistryObject<EntityType<EntitySeaSerpentArrow>> SEA_SERPENT_ARROW = registerEntity(EntityType.Builder.<EntitySeaSerpentArrow>of(EntitySeaSerpentArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "sea_serpent_arrow");
    public static final RegistryObject<EntityType<EntityChainTie>> CHAIN_TIE = registerEntity(EntityType.Builder.<EntityChainTie>of(EntityChainTie::new, MobCategory.MISC).sized(0.8F, 0.9F), "chain_tie");
    public static final RegistryObject<EntityType<EntityPixieCharge>> PIXIE_CHARGE = registerEntity(EntityType.Builder.<EntityPixieCharge>of(EntityPixieCharge::new, MobCategory.MISC).sized(0.5F, 0.5F), "pixie_charge");
    public static final RegistryObject<EntityType<EntityMyrmexSwarmer>> MYRMEX_SWARMER = registerEntity(EntityType.Builder.of(EntityMyrmexSwarmer::new, MobCategory.CREATURE).sized(0.5F, 0.5F), "myrmex_swarmer");
    public static final RegistryObject<EntityType<EntityTideTrident>> TIDE_TRIDENT = registerEntity(EntityType.Builder.<EntityTideTrident>of(EntityTideTrident::new, MobCategory.MISC).sized(0.85F, 0.5F), "tide_trident");
    public static final RegistryObject<EntityType<EntityMobSkull>> MOB_SKULL = registerEntity(EntityType.Builder.of(EntityMobSkull::new, MobCategory.MISC).sized(0.85F, 0.85F), "mob_skull");
    public static final RegistryObject<EntityType<EntityDreadThrall>> DREAD_THRALL = registerEntity(EntityType.Builder.of(EntityDreadThrall::new, MobCategory.MONSTER).sized(0.6F, 1.8F), "dread_thrall");
    public static final RegistryObject<EntityType<EntityDreadGhoul>> DREAD_GHOUL = registerEntity(EntityType.Builder.of(EntityDreadGhoul::new, MobCategory.MONSTER).sized(0.6F, 1.8F), "dread_ghoul");
    public static final RegistryObject<EntityType<EntityDreadBeast>> DREAD_BEAST = registerEntity(EntityType.Builder.of(EntityDreadBeast::new, MobCategory.MONSTER).sized(1.2F, 0.9F), "dread_beast");
    public static final RegistryObject<EntityType<EntityDreadScuttler>> DREAD_SCUTTLER = registerEntity(EntityType.Builder.of(EntityDreadScuttler::new, MobCategory.MONSTER).sized(1.5F, 1.3F), "dread_scuttler");
    public static final RegistryObject<EntityType<EntityDreadLich>> DREAD_LICH = registerEntity(EntityType.Builder.of(EntityDreadLich::new, MobCategory.MONSTER).sized(0.6F, 1.8F), "dread_lich");
    public static final RegistryObject<EntityType<EntityDreadLichSkull>> DREAD_LICH_SKULL = registerEntity(EntityType.Builder.<EntityDreadLichSkull>of(EntityDreadLichSkull::new, MobCategory.MISC).sized(0.5F, 0.5F), "dread_lich_skull");
    public static final RegistryObject<EntityType<EntityDreadKnight>> DREAD_KNIGHT = registerEntity(EntityType.Builder.of(EntityDreadKnight::new, MobCategory.MONSTER).sized(0.6F, 1.8F), "dread_knight");
    public static final RegistryObject<EntityType<EntityDreadHorse>> DREAD_HORSE = registerEntity(EntityType.Builder.of(EntityDreadHorse::new, MobCategory.MONSTER).sized(1.3964844F, 1.6F), "dread_horse");
    public static final RegistryObject<EntityType<EntityHydra>> HYDRA = registerEntity(EntityType.Builder.of(EntityHydra::new, MobCategory.CREATURE).sized(2.8F, 1.39F), "hydra");
    public static final RegistryObject<EntityType<EntityHydraBreath>> HYDRA_BREATH = registerEntity(EntityType.Builder.<EntityHydraBreath>of(EntityHydraBreath::new, MobCategory.MISC).sized(0.9F, 0.9F), "hydra_breath");
    public static final RegistryObject<EntityType<EntityHydraArrow>> HYDRA_ARROW = registerEntity(EntityType.Builder.<EntityHydraArrow>of(EntityHydraArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "hydra_arrow");
    public static final RegistryObject<EntityType<EntityGhost>> GHOST = registerEntity(EntityType.Builder.of(EntityGhost::new, MobCategory.MONSTER).sized(0.8F, 1.9F).fireImmune(), "ghost");
    public static final RegistryObject<EntityType<EntityGhostSword>> GHOST_SWORD = registerEntity(EntityType.Builder.<EntityGhostSword>of(EntityGhostSword::new, MobCategory.MISC).sized(0.5F, 0.5F), "ghost_sword");

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(EntityType.Builder<T> builder, String entityName) {
        return registerEntity(builder, -1, entityName);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(EntityType.Builder<T> builder, int serverTrackingRange, String entityName) {
        return ENTITIES.register(entityName, () -> {
            if (serverTrackingRange != -1)
                builder.clientTrackingRange(serverTrackingRange);

            return builder.build(entityName);
        });
    }
    
    public static void bakeAttributes() {
        FabricDefaultAttributeRegistry.register(DRAGON_EGG.get(), EntityDragonEgg.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DRAGON_SKULL.get(), EntityDragonSkull.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FIRE_DRAGON.get(), EntityFireDragon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ICE_DRAGON.get(), EntityIceDragon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(LIGHTNING_DRAGON.get(), EntityLightningDragon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(HIPPOGRYPH.get(), EntityHippogryph.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GORGON.get(), EntityGorgon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(STONE_STATUE.get(), EntityStoneStatue.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(PIXIE.get(), EntityPixie.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CYCLOPS.get(), EntityCyclops.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SIREN.get(), EntitySiren.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(HIPPOCAMPUS.get(), EntityHippocampus.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DEATH_WORM.get(), EntityDeathWorm.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(COCKATRICE.get(), EntityCockatrice.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(STYMPHALIAN_BIRD.get(), EntityStymphalianBird.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TROLL.get(), EntityTroll.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_WORKER.get(), EntityMyrmexWorker.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_SOLDIER.get(), EntityMyrmexSoldier.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_SENTINEL.get(), EntityMyrmexSentinel.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_ROYAL.get(), EntityMyrmexRoyal.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_QUEEN.get(), EntityMyrmexQueen.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_EGG.get(), EntityMyrmexEgg.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MYRMEX_SWARMER.get(), EntityMyrmexSwarmer.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(AMPHITHERE.get(), EntityAmphithere.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SEA_SERPENT.get(), EntitySeaSerpent.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MOB_SKULL.get(), EntityMobSkull.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_THRALL.get(), EntityDreadThrall.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_LICH.get(), EntityDreadLich.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_BEAST.get(), EntityDreadBeast.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_HORSE.get(), EntityDreadHorse.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_GHOUL.get(), EntityDreadGhoul.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_KNIGHT.get(), EntityDreadKnight.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DREAD_SCUTTLER.get(), EntityDreadScuttler.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(HYDRA.get(), EntityHydra.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GHOST.get(), EntityGhost.bakeAttributes().build());
    }
    
    public static void commonSetup() {
        SpawnPlacements.register(HIPPOGRYPH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityHippogryph::checkMobSpawnRules);
        SpawnPlacements.register(TROLL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTroll::canTrollSpawnOn);
        SpawnPlacements.register(DREAD_LICH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityDreadLich::canLichSpawnOn);
        SpawnPlacements.register(COCKATRICE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCockatrice::checkMobSpawnRules);
        SpawnPlacements.register(AMPHITHERE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, EntityAmphithere::canAmphithereSpawnOn);
        
        addSpawners();
    }
    
    public static void addSpawners() {
        BiomeModifications.addSpawn(ctx -> IafConfig.spawnHippogryphs && BiomeConfig.test(BiomeConfig.hippogryphBiomes, ctx.getBiomeRegistryEntry()), MobCategory.CREATURE, IafEntityRegistry.HIPPOGRYPH.get(), IafConfig.hippogryphSpawnRate, 1, 1);
        BiomeModifications.addSpawn(ctx -> IafConfig.spawnLiches && BiomeConfig.test(BiomeConfig.mausoleumBiomes, ctx.getBiomeRegistryEntry()), MobCategory.MONSTER, IafEntityRegistry.DREAD_LICH.get(), IafConfig.lichSpawnRate, 1, 1);
        BiomeModifications.addSpawn(ctx -> IafConfig.spawnCockatrices && BiomeConfig.test(BiomeConfig.cockatriceBiomes, ctx.getBiomeRegistryEntry()), MobCategory.CREATURE, IafEntityRegistry.COCKATRICE.get(), IafConfig.cockatriceSpawnRate, 1, 2);
        BiomeModifications.addSpawn(ctx -> IafConfig.spawnAmphitheres && BiomeConfig.test(BiomeConfig.amphithereBiomes, ctx.getBiomeRegistryEntry()), MobCategory.CREATURE, IafEntityRegistry.AMPHITHERE.get(), IafConfig.amphithereSpawnRate, 1, 1);
        BiomeModifications.addSpawn(ctx -> IafConfig.spawnTrolls && (
            BiomeConfig.test(BiomeConfig.forestTrollBiomes, ctx.getBiomeRegistryEntry()) ||
            BiomeConfig.test(BiomeConfig.snowyTrollBiomes, ctx.getBiomeRegistryEntry()) ||
            BiomeConfig.test(BiomeConfig.mountainTrollBiomes, ctx.getBiomeRegistryEntry())
        ), MobCategory.MONSTER, IafEntityRegistry.TROLL.get(), IafConfig.trollSpawnRate, 1, 3);
    }
}
