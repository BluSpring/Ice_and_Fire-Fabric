package com.github.alexthe666.iceandfire;

import com.github.alexthe666.iceandfire.config.ConfigHolder;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.List;

public class IafConfig {
    public static boolean customMainMenu = true;
    public static boolean useVanillaFont = false;

    //public static boolean logCascadingWorldGen = false;
    public static boolean generateSilverOre = true;
    public static boolean generateCopperOre = true;
    public static boolean generateSapphireOre = true;
    public static boolean generateAmythestOre = true;
    public static boolean generateDragonSkeletons = true;
    public static int generateDragonSkeletonChance = 300;
    public static boolean generateDragonDens = true;
    public static int generateDragonDenChance = 180;
    public static boolean generateDragonRoosts = true;
    public static int generateDragonRoostChance = 360;
    public static int dragonDenGoldAmount = 4;
    public static boolean useDimensionBlackList = true;
    public static List<? extends String> whitelistedDimensions = Lists.newArrayList("minecraft:overworld");
    public static List<? extends String> blacklistedDimensions = Lists.newArrayList("minecraft:nether", "minecraft:the_end");
    public static List<? extends String> dragonWhitelistedDimensions = Lists.newArrayList("minecraft:overworld");
    public static List<? extends String> dragonBlacklistedDimensions = Lists.newArrayList("minecraft:the_nether", "minecraft:the_end");
    public static List<? extends String> blacklistedBreakBlocks = Lists.newArrayList();
    public static List<? extends String> noDropBreakBlocks = Lists.newArrayList("minecraft:stone", "minecraft:dirt", "minecraft:grass_block");
    public static boolean blacklistBreakBlocksIsWhiteList = false;
    public static boolean spawnGlaciers = true;
    public static int glacierSpawnChance = 4;
    public static int oreToStoneRatioForDragonCaves = 45;
    public static int dragonEggTime = 7200;
    public static int dragonGriefing = 0;
    public static boolean tamedDragonGriefing = true;
    public static int dragonFlapNoiseDistance = 4;
    public static int dragonFluteDistance = 8;
    public static double dragonHealth = 500;
    public static int dragonAttackDamage = 17;
    public static double dragonAttackDamageFire = 2F;
    public static double dragonAttackDamageIce = 2.5F;
    public static double dragonAttackDamageLightning = 3.5F;
    public static int maxDragonFlight = 128;
    public static int dragonGoldSearchLength = 30;
    public static boolean canDragonsDespawn = true;
    public static boolean doDragonsSleep = true;
    public static boolean dragonDigWhenStuck = true;
    public static int dragonBreakBlockCooldown = 5;
    public static boolean dragonDropSkull = true;
    public static boolean dragonDropHeart = true;
    public static boolean dragonDropBlood = true;
    public static int dragonTargetSearchLength = 128;
    public static int dragonWanderFromHomeDistance = 40;
    public static int dragonHungerTickRate = 3000;
    public static boolean spawnHippogryphs = true;
    public static int hippogryphSpawnRate = 2;
    public static boolean spawnGorgons = true;
    public static int spawnGorgonsChance = 16;
    public static double gorgonMaxHealth = 100D;
    public static boolean spawnPixies = true;
    public static int spawnPixiesChance = 60;
    public static int pixieVillageSize = 5;
    public static boolean pixiesStealItems = true;
    public static boolean generateCyclopsCaves = true;
    public static boolean generateWanderingCyclops = true;
    public static int spawnWanderingCyclopsChance = 900;
    public static int spawnCyclopsCaveChance = 170;
    public static int cyclopesSheepSearchLength = 17;
    public static double cyclopsMaxHealth = 150;
    public static double cyclopsAttackStrength = 15;
    public static double cyclopsBiteStrength = 40;
    public static boolean cyclopsGriefing = true;
    public static double sirenMaxHealth = 50D;
    public static boolean generateSirenIslands = true;
    public static boolean sirenShader = true;
    public static int sirenMaxSingTime = 12000;
    public static int sirenTimeBetweenSongs = 2000;
    public static int generateSirenChance = 600;
    public static boolean spawnHippocampus = true;
    public static int hippocampusSpawnChance = 70;
    public static int deathWormTargetSearchLength = 64;
    public static double deathWormMaxHealth = 10D;
    public static double deathWormAttackStrength = 3D;
    public static boolean spawnDeathWorm = true;
    public static boolean deathWormAttackMonsters = true;
    public static int deathWormSpawnRate = 30;
    public static int deathWormSpawnCheckChance = 3;
    public static int cockatriceChickenSearchLength = 32;
    public static int cockatriceEggChance = 30;
    public static double cockatriceMaxHealth = 40.0D;
    public static boolean chickensLayRottenEggs = true;
    public static boolean spawnCockatrices = true;
    public static int cockatriceSpawnRate = 4;
    public static int cockatriceSpawnCheckChance = 0;
    public static int stymphalianBirdTargetSearchLength = 64;
    public static int stymphalianBirdFeatherDropChance = 25;
    public static double stymphalianBirdFeatherAttackStength = 1F;
    public static int stymphalianBirdFlockLength = 40;
    public static int stymphalianBirdFlightHeight = 80;
    public static boolean spawnStymphalianBirds = true;
    public static boolean stymphalianBirdsDataTagDrops = true;
    public static boolean stympahlianBirdAttackAnimals = false;
    public static int stymphalianBirdSpawnChance = 100;
    public static boolean spawnTrolls = true;
    public static int trollSpawnRate = 60;
    public static int trollSpawnCheckChance = 40;
    public static boolean trollsDropWeapon = true;
    public static double trollMaxHealth = 50;
    public static double trollAttackStrength = 10;
    public static boolean villagersFearDragons = true;
    public static boolean animalsFearDragons = true;
    public static boolean generateMyrmexColonies = true;
    public static int myrmexPregnantTicks = 2500;
    public static int myrmexEggTicks = 3000;
    public static int myrmexLarvaTicks = 35000;
    public static int myrmexColonyGenChance = 150;
    public static int myrmexColonySize = 80;
    public static double myrmexBaseAttackStrength = 3.0D;
    public static boolean experimentalPathFinder;
    public static boolean spawnAmphitheres = true;
    public static int amphithereSpawnRate = 5;
    public static float amphithereVillagerSearchLength = 64;
    public static int amphithereTameTime = 400;
    public static double amphithereFlightSpeed = 1.75F;
    public static double amphithereMaxHealth = 50D;
    public static double amphithereAttackStrength = 7D;
    public static boolean spawnSeaSerpents = true;
    public static int seaSerpentSpawnChance = 300;
    public static boolean seaSerpentGriefing = true;
    public static double seaSerpentBaseHealth = 20D;
    public static double seaSerpentAttackStrength = 4D;
    public static double dragonsteelBaseDamage = 25F;
    public static int dragonsteelBaseArmor = 12;
    public static int dragonsteelBaseDurability = 8000;
    public static boolean spawnStructuresOnSuperflat = true;
    public static boolean dragonMovedWronglyFix = false;
    public static int dreadlandsDimensionId = -12;
    public static boolean weezerTinkers = true;
    public static double dragonBlockBreakingDropChance = 0.1D;
    public static boolean completeDragonPathfinding = false;
    public static boolean dragonAuto3rdPerson = true;
    public static double dreadQueenMaxHealth = 750;
    public static boolean generateMausoleums = true;
    public static int generateMausoleumChance = 30;
    public static boolean spawnLiches = true;
    public static int lichSpawnRate = 1;
    public static double hydraMaxHealth = 250D;
    public static boolean generateHydraCaves = true;
    public static int generateHydraChance = 200;
    public static boolean explosiveDragonBreath = false;
    public static double weezerTinkersDisarmChance = 0.2F;
    public static boolean chunkLoadSummonCrystal = true;
    public static double dangerousWorldGenDistanceLimit = 1000;
    public static double dangerousWorldGenSeparationLimit = 300;
    public static double dragonFlightSpeedMod = 1F;

    public static void bakeClient(final ModConfig config) {
        try {
            customMainMenu = ConfigHolder.CLIENT.customMainMenu.get();
            useVanillaFont = ConfigHolder.CLIENT.useVanillaFont.get();
         dragonAuto3rdPerson = ConfigHolder.CLIENT.dragonAuto3rdPerson.get();
        } catch (Exception e) {
            IceAndFire.LOGGER.warn("An exception was caused trying to load the client config for Ice and Fire.");
            e.printStackTrace();
        }
    }

    public static void bakeServer(final ModConfig config) {
        try {
            generateSilverOre = ConfigHolder.SERVER.generateSilverOre.get();
            generateCopperOre = ConfigHolder.SERVER.generateCopperOre.get();
            generateSapphireOre = ConfigHolder.SERVER.generateSapphireOre.get();
            generateAmythestOre = ConfigHolder.SERVER.generateAmythestOre.get();
            generateDragonSkeletons = ConfigHolder.SERVER.generateDragonSkeletons.get();
            generateDragonSkeletonChance = ConfigHolder.SERVER.generateDragonSkeletonChance.get();
            generateDragonDens = ConfigHolder.SERVER.generateDragonDens.get();
            generateDragonDenChance = ConfigHolder.SERVER.generateDragonDenChance.get();
            generateDragonRoosts = ConfigHolder.SERVER.generateDragonRoosts.get();
            generateDragonRoostChance = ConfigHolder.SERVER.generateDragonRoostChance.get();
            dragonDenGoldAmount = ConfigHolder.SERVER.dragonDenGoldAmount.get();
            spawnGlaciers = ConfigHolder.SERVER.spawnGlaciers.get();
            glacierSpawnChance = ConfigHolder.SERVER.glacierSpawnChance.get();
            oreToStoneRatioForDragonCaves = ConfigHolder.SERVER.oreToStoneRatioForDragonCaves.get();
            dragonEggTime = ConfigHolder.SERVER.dragonEggTime.get();
            dragonGriefing = ConfigHolder.SERVER.dragonGriefing.get();
            tamedDragonGriefing = ConfigHolder.SERVER.tamedDragonGriefing.get();
            dragonFlapNoiseDistance = ConfigHolder.SERVER.dragonFlapNoiseDistance.get();
            dragonFluteDistance = ConfigHolder.SERVER.dragonFluteDistance.get();
            dragonHealth = ConfigHolder.SERVER.dragonHealth.get();
            dragonAttackDamage = ConfigHolder.SERVER.dragonAttackDamage.get();
            dragonAttackDamageFire = ConfigHolder.SERVER.dragonAttackDamageFire.get();
            dragonAttackDamageIce = ConfigHolder.SERVER.dragonAttackDamageIce.get();
            dragonAttackDamageLightning = ConfigHolder.SERVER.dragonAttackDamageLightning.get();
            maxDragonFlight = ConfigHolder.SERVER.maxDragonFlight.get();
            dragonGoldSearchLength = ConfigHolder.SERVER.dragonGoldSearchLength.get();
            canDragonsDespawn = ConfigHolder.SERVER.canDragonsDespawn.get();
            doDragonsSleep = ConfigHolder.SERVER.doDragonsSleep.get();
            dragonDigWhenStuck = ConfigHolder.SERVER.dragonDigWhenStuck.get();
            dragonBreakBlockCooldown = ConfigHolder.SERVER.dragonBreakBlockCooldown.get();
            dragonDropSkull = ConfigHolder.SERVER.dragonDropSkull.get();
            dragonDropHeart = ConfigHolder.SERVER.dragonDropHeart.get();
            dragonDropBlood = ConfigHolder.SERVER.dragonDropBlood.get();
            dragonTargetSearchLength = ConfigHolder.SERVER.dragonTargetSearchLength.get();
            dragonWanderFromHomeDistance = ConfigHolder.SERVER.dragonWanderFromHomeDistance.get();
            dragonHungerTickRate = ConfigHolder.SERVER.dragonHungerTickRate.get();
            spawnHippogryphs = ConfigHolder.SERVER.spawnHippogryphs.get();
            hippogryphSpawnRate = ConfigHolder.SERVER.hippogryphSpawnRate.get();
            spawnGorgons = ConfigHolder.SERVER.spawnGorgons.get();
            spawnGorgonsChance = ConfigHolder.SERVER.spawnGorgonsChance.get();
            gorgonMaxHealth = ConfigHolder.SERVER.gorgonMaxHealth.get();
            spawnPixies = ConfigHolder.SERVER.spawnPixies.get();
            spawnPixiesChance = ConfigHolder.SERVER.spawnPixiesChance.get();
            pixieVillageSize = ConfigHolder.SERVER.pixieVillageSize.get();
            pixiesStealItems = ConfigHolder.SERVER.pixiesStealItems.get();
            generateCyclopsCaves = ConfigHolder.SERVER.generateCyclopsCaves.get();
            generateWanderingCyclops = ConfigHolder.SERVER.generateWanderingCyclops.get();
            spawnWanderingCyclopsChance = ConfigHolder.SERVER.spawnWanderingCyclopsChance.get();
            spawnCyclopsCaveChance = ConfigHolder.SERVER.spawnCyclopsCaveChance.get();
            cyclopesSheepSearchLength = ConfigHolder.SERVER.cyclopesSheepSearchLength.get();
            cyclopsMaxHealth = ConfigHolder.SERVER.cyclopsMaxHealth.get();
            cyclopsAttackStrength = ConfigHolder.SERVER.cyclopsAttackStrength.get();
            cyclopsBiteStrength = ConfigHolder.SERVER.cyclopsBiteStrength.get();
            cyclopsGriefing = ConfigHolder.SERVER.cyclopsGriefing.get();
            sirenMaxHealth = ConfigHolder.SERVER.sirenMaxHealth.get();
            generateSirenIslands = ConfigHolder.SERVER.generateSirenIslands.get();
            sirenShader = ConfigHolder.SERVER.sirenShader.get();
            sirenMaxSingTime = ConfigHolder.SERVER.sirenMaxSingTime.get();
            sirenTimeBetweenSongs = ConfigHolder.SERVER.sirenTimeBetweenSongs.get();
            generateSirenChance = ConfigHolder.SERVER.generateSirenChance.get();
            spawnHippocampus = ConfigHolder.SERVER.spawnHippocampus.get();
            hippocampusSpawnChance = ConfigHolder.SERVER.hippocampusSpawnChance.get();
            deathWormTargetSearchLength = ConfigHolder.SERVER.deathWormTargetSearchLength.get();
            deathWormMaxHealth = ConfigHolder.SERVER.deathWormMaxHealth.get();
            deathWormAttackStrength = ConfigHolder.SERVER.deathWormAttackStrength.get();
            spawnDeathWorm = ConfigHolder.SERVER.spawnDeathWorm.get();
            deathWormAttackMonsters = ConfigHolder.SERVER.deathWormAttackMonsters.get();
            deathWormSpawnRate = ConfigHolder.SERVER.deathWormSpawnRate.get();
            deathWormSpawnCheckChance = ConfigHolder.SERVER.deathWormSpawnCheckChance.get();
            cockatriceChickenSearchLength = ConfigHolder.SERVER.cockatriceChickenSearchLength.get();
            cockatriceEggChance = ConfigHolder.SERVER.cockatriceEggChance.get();
            cockatriceMaxHealth = ConfigHolder.SERVER.cockatriceMaxHealth.get();
            chickensLayRottenEggs = ConfigHolder.SERVER.chickensLayRottenEggs.get();
            spawnCockatrices = ConfigHolder.SERVER.spawnCockatrices.get();
            cockatriceSpawnRate = ConfigHolder.SERVER.cockatriceSpawnRate.get();
            cockatriceSpawnCheckChance = ConfigHolder.SERVER.cockatriceSpawnCheckChance.get();
            stymphalianBirdTargetSearchLength = ConfigHolder.SERVER.stymphalianBirdTargetSearchLength.get();
            stymphalianBirdFeatherDropChance = ConfigHolder.SERVER.stymphalianBirdFeatherDropChance.get();
            stymphalianBirdFeatherAttackStength = ConfigHolder.SERVER.stymphalianBirdFeatherAttackStength.get();
            stymphalianBirdFlockLength = ConfigHolder.SERVER.stymphalianBirdFlockLength.get();
            stymphalianBirdFlightHeight = ConfigHolder.SERVER.stymphalianBirdFlightHeight.get();
            spawnStymphalianBirds = ConfigHolder.SERVER.spawnStymphalianBirds.get();
            stymphalianBirdsDataTagDrops = ConfigHolder.SERVER.stymphalianBirdsDataTagDrops.get();
            stympahlianBirdAttackAnimals = ConfigHolder.SERVER.stympahlianBirdAttackAnimals.get();
            stymphalianBirdSpawnChance = ConfigHolder.SERVER.stymphalianBirdSpawnChance.get();
            spawnTrolls = ConfigHolder.SERVER.spawnTrolls.get();
            trollSpawnRate = ConfigHolder.SERVER.trollSpawnRate.get();
            trollSpawnCheckChance = ConfigHolder.SERVER.trollSpawnCheckChance.get();
            trollsDropWeapon = ConfigHolder.SERVER.trollsDropWeapon.get();
            trollMaxHealth = ConfigHolder.SERVER.trollMaxHealth.get();
            trollAttackStrength = ConfigHolder.SERVER.trollAttackStrength.get();
            villagersFearDragons = ConfigHolder.SERVER.villagersFearDragons.get();
            animalsFearDragons = ConfigHolder.SERVER.animalsFearDragons.get();
            generateMyrmexColonies = ConfigHolder.SERVER.generateMyrmexColonies.get();
            myrmexPregnantTicks = ConfigHolder.SERVER.myrmexPregnantTicks.get();
            myrmexEggTicks = ConfigHolder.SERVER.myrmexEggTicks.get();
            myrmexLarvaTicks = ConfigHolder.SERVER.myrmexLarvaTicks.get();
            myrmexColonyGenChance = ConfigHolder.SERVER.myrmexColonyGenChance.get();
            myrmexColonySize = ConfigHolder.SERVER.myrmexColonySize.get();
            myrmexBaseAttackStrength = ConfigHolder.SERVER.myrmexBaseAttackStrength.get();
            experimentalPathFinder = ConfigHolder.SERVER.experimentalPathFinder.get();
            spawnAmphitheres = ConfigHolder.SERVER.spawnAmphitheres.get();
            amphithereSpawnRate = ConfigHolder.SERVER.amphithereSpawnRate.get();
            amphithereVillagerSearchLength = ConfigHolder.SERVER.amphithereVillagerSearchLength.get();
            amphithereTameTime = ConfigHolder.SERVER.amphithereTameTime.get();
            amphithereFlightSpeed = ConfigHolder.SERVER.amphithereFlightSpeed.get();
            amphithereMaxHealth = ConfigHolder.SERVER.amphithereMaxHealth.get();
            amphithereAttackStrength = ConfigHolder.SERVER.amphithereAttackStrength.get();
            spawnSeaSerpents = ConfigHolder.SERVER.spawnSeaSerpents.get();
            seaSerpentSpawnChance = ConfigHolder.SERVER.seaSerpentSpawnChance.get();
            seaSerpentGriefing = ConfigHolder.SERVER.seaSerpentGriefing.get();
            seaSerpentBaseHealth = ConfigHolder.SERVER.seaSerpentBaseHealth.get();
            seaSerpentAttackStrength = ConfigHolder.SERVER.seaSerpentAttackStrength.get();
            dragonsteelBaseDamage = ConfigHolder.SERVER.dragonsteelBaseDamage.get();
            dragonsteelBaseArmor = ConfigHolder.SERVER.dragonsteelBaseArmor.get();
            dragonsteelBaseDurability = ConfigHolder.SERVER.dragonsteelBaseDurability.get();
            dragonMovedWronglyFix = ConfigHolder.SERVER.dragonMovedWronglyFix.get();
            weezerTinkers = ConfigHolder.SERVER.weezerTinkers.get();
            dragonBlockBreakingDropChance = ConfigHolder.SERVER.dragonBlockBreakingDropChance.get();
            generateMausoleums = ConfigHolder.SERVER.generateMausoleums.get();
            generateMausoleumChance = ConfigHolder.SERVER.generateMausoleumChance.get();
            spawnLiches = ConfigHolder.SERVER.spawnLiches.get();
            lichSpawnRate = ConfigHolder.SERVER.lichSpawnRate.get();
            hydraMaxHealth = ConfigHolder.SERVER.hydraMaxHealth.get();
            generateHydraCaves = ConfigHolder.SERVER.generateHydraCaves.get();
            generateHydraChance = ConfigHolder.SERVER.generateHydraChance.get();
            explosiveDragonBreath = ConfigHolder.SERVER.explosiveDragonBreath.get();
            weezerTinkersDisarmChance = ConfigHolder.SERVER.weezerTinkersDisarmChance.get();
            chunkLoadSummonCrystal = ConfigHolder.SERVER.chunkLoadSummonCrystal.get();
            dangerousWorldGenDistanceLimit = ConfigHolder.SERVER.dangerousWorldGenDistanceLimit.get();
            dangerousWorldGenSeparationLimit = ConfigHolder.SERVER.dangerousWorldGenSeparationLimit.get();
            blacklistedBreakBlocks = ConfigHolder.SERVER.blacklistedBreakBlocks.get();
            noDropBreakBlocks = ConfigHolder.SERVER.noDropBreakBlocks.get();
            useDimensionBlackList = ConfigHolder.SERVER.useDimensionBlackList.get();
            dragonWhitelistedDimensions = ConfigHolder.SERVER.dragonWhitelistDimensions.get();
            dragonBlacklistedDimensions = ConfigHolder.SERVER.dragonBlacklistDimensions.get();
            whitelistedDimensions = ConfigHolder.SERVER.whitelistDimensions.get();
            blacklistedDimensions = ConfigHolder.SERVER.blacklistDimensions.get();
            dragonFlightSpeedMod = ConfigHolder.SERVER.dragonFlightSpeedMod.get();
        } catch (Exception e) {
            IceAndFire.LOGGER.warn("An exception was caused trying to load the common config for Ice and Fire.");
            e.printStackTrace();
        }
    }

}
