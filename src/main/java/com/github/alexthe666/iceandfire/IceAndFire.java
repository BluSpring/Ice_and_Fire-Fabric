package com.github.alexthe666.iceandfire;

import com.github.alexthe666.citadel.server.message.CitadelPacket;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.config.ConfigHolder;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.IafVillagerRegistry;
import com.github.alexthe666.iceandfire.entity.props.SyncEntityData;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.inventory.IafContainerRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.IafTabRegistry;
import com.github.alexthe666.iceandfire.loot.IafLootRegistry;
import com.github.alexthe666.iceandfire.message.*;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.recipe.IafBannerPatterns;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import com.github.alexthe666.iceandfire.recipe.IafRecipeSerializers;
import com.github.alexthe666.iceandfire.world.IafPlacementFilterRegistry;
import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.fabricators_of_create.porting_lib.util.ServerLifecycleHooks;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class IceAndFire implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "iceandfire";
    public static final SimpleChannel NETWORK_WRAPPER = new SimpleChannel(new ResourceLocation("iceandfire", "main_channel"));
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static boolean DEBUG = true;
    public static String VERSION = "UNKNOWN";
    public static CommonProxy PROXY = safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    private static int packetsRegistered = 0;

    private static <T> T safeRunForDist(Supplier<Supplier<T>> clientSide, Supplier<Supplier<T>> serverSide) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            return clientSide.get().get();
        } else {
            return serverSide.get().get();
        }
    }

    public void onInitialize() {
        try {
            ModContainer mod = FabricLoader.getInstance().getModContainer(IceAndFire.MODID).orElseThrow(NullPointerException::new);
            VERSION = mod.getMetadata().getVersion().getFriendlyString();
        } catch (Exception ignored) {
        }

        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        PROXY.init();

        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);


        /*
        final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, IceAndFire.MODID);
        biomeModifiers.register();
        biomeModifiers.register("iaf_mob_spawns", IafMobSpawnBiomeModifier::makeCodec);
        biomeModifiers.register("iaf_features", IafFeatureBiomeModifier::makeCodec);
         */
        IafWorldRegistry.addFeatures();

        IafItemRegistry.ITEMS.register();
        IafItemRegistry.init();
        IafBlockRegistry.BLOCKS.register();
        IafTabRegistry.TAB_REGISTER.register();
        IafEntityRegistry.ENTITIES.register();
        IafEntityRegistry.bakeAttributes();
        IafTileEntityRegistry.TYPES.register();
        IafPlacementFilterRegistry.PLACEMENT_MODIFIER_TYPES.register();
        IafWorldRegistry.FEATURES.register();
        IafRecipeRegistry.RECIPE_TYPE.register();
        IafBannerPatterns.BANNERS.register();
        IafStructureTypes.STRUCTURE_TYPES.register();
        IafContainerRegistry.CONTAINERS.register();
        IafRecipeSerializers.SERIALIZERS.register();
        IafProcessors.PROCESSORS.register();

        IafVillagerRegistry.POI_TYPES.register();
        IafVillagerRegistry.PROFESSIONS.register();

        //MinecraftForge.EVENT_BUS.register(IafBlockRegistry.class);
        IafRecipeRegistry.preInit();
        IafSoundRegistry.init();
        IafDamageRegistry.init();
        this.setup();
        this.setupComplete();
        
        NETWORK_WRAPPER.initServerListener();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.setupClient();
        }
    }
    
    public void onServerStarted(MinecraftServer server) {
    }

    public static <MSG extends C2SPacket> void sendMSGToServer(MSG message) {
        NETWORK_WRAPPER.sendToServer(message);
    }

    public static <MSG extends S2CPacket> void sendMSGToAll(MSG message) {
        NETWORK_WRAPPER.sendToClientsInServer(message, ServerLifecycleHooks.getCurrentServer());
    }

    public static <MSG extends S2CPacket> void sendMSGToPlayer(MSG message, ServerPlayer player) {
        NETWORK_WRAPPER.sendToClient(message, player);
    }

    private void setup() {
        IafEntityRegistry.commonSetup();
        registerMessage(packetsRegistered++, MessageDaytime.class, MessageDaytime::write, MessageDaytime::read);
        registerMessage(packetsRegistered++, MessageDeathWormHitbox.class, MessageDeathWormHitbox::write, MessageDeathWormHitbox::read);
        registerMessage(packetsRegistered++, MessageDragonControl.class, MessageDragonControl::write, MessageDragonControl::read);
        registerMessage(packetsRegistered++, MessageDragonSetBurnBlock.class, MessageDragonSetBurnBlock::write, MessageDragonSetBurnBlock::read);
        registerMessage(packetsRegistered++, MessageDragonSyncFire.class, MessageDragonSyncFire::write, MessageDragonSyncFire::read);
        registerMessage(packetsRegistered++, MessageGetMyrmexHive.class, MessageGetMyrmexHive::write, MessageGetMyrmexHive::read);
        registerMessage(packetsRegistered++, MessageMyrmexSettings.class, MessageMyrmexSettings::write, MessageMyrmexSettings::read);
        registerMessage(packetsRegistered++, MessageHippogryphArmor.class, MessageHippogryphArmor::write, MessageHippogryphArmor::read);
        registerMessage(packetsRegistered++, MessageMultipartInteract.class, MessageMultipartInteract::write, MessageMultipartInteract::read);
        registerMessage(packetsRegistered++, MessagePlayerHitMultipart.class, MessagePlayerHitMultipart::write, MessagePlayerHitMultipart::read);
        registerMessage(packetsRegistered++, MessageSetMyrmexHiveNull.class, MessageSetMyrmexHiveNull::write, MessageSetMyrmexHiveNull::read);
        registerMessage(packetsRegistered++, MessageSirenSong.class, MessageSirenSong::write, MessageSirenSong::read);
        registerMessage(packetsRegistered++, MessageSpawnParticleAt.class, MessageSpawnParticleAt::write, MessageSpawnParticleAt::read);
        registerMessage(packetsRegistered++, MessageStartRidingMob.class, MessageStartRidingMob::write, MessageStartRidingMob::read);
        registerMessage(packetsRegistered++, MessageUpdatePixieHouse.class, MessageUpdatePixieHouse::write, MessageUpdatePixieHouse::read);
        registerMessage(packetsRegistered++, MessageUpdatePixieHouseModel.class, MessageUpdatePixieHouseModel::write, MessageUpdatePixieHouseModel::read);
        registerMessage(packetsRegistered++, MessageUpdatePixieJar.class, MessageUpdatePixieJar::write, MessageUpdatePixieJar::read);
        registerMessage(packetsRegistered++, MessageUpdatePodium.class, MessageUpdatePodium::write, MessageUpdatePodium::read);
        registerMessage(packetsRegistered++, MessageUpdateDragonforge.class, MessageUpdateDragonforge::write, MessageUpdateDragonforge::read);
        registerMessage(packetsRegistered++, MessageUpdateLectern.class, MessageUpdateLectern::write, MessageUpdateLectern::read);
        registerMessage(packetsRegistered++, MessageSyncPath.class, MessageSyncPath::write, MessageSyncPath::read);
        registerMessage(packetsRegistered++, MessageSyncPathReached.class, MessageSyncPathReached::write, MessageSyncPathReached::read);
        registerMessage(packetsRegistered++, MessageSwingArm.class, MessageSwingArm::write, MessageSwingArm::read);
        registerMessage(packetsRegistered++, SyncEntityData.class, SyncEntityData::encode, SyncEntityData::decode);
        PROXY.setup();
        IafLootRegistry.init();
    }
    
    private static <T extends CitadelPacket> void registerMessage(int id, Class<T> clazz, BiConsumer<T, FriendlyByteBuf> writer, Function<FriendlyByteBuf, T> reader) {
        NETWORK_WRAPPER.registerC2SPacket(clazz, id, reader);
        NETWORK_WRAPPER.registerS2CPacket(clazz, id, reader);
    }

    private void setupClient() {
        PROXY.clientInit();
        NETWORK_WRAPPER.initClientListener();
    }

    private void setupComplete() {
        PROXY.postInit();
    }

}