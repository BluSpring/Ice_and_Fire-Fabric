package com.github.alexthe666.iceandfire.client;


import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.gui.IafGuiRegistry;
import com.github.alexthe666.iceandfire.client.model.*;
import com.github.alexthe666.iceandfire.client.model.animator.FireDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.IceDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.LightningTabulaDragonAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.SeaSerpentTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.*;
import com.github.alexthe666.iceandfire.client.render.entity.*;
import com.github.alexthe666.iceandfire.client.render.tile.*;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.github.alexthe666.iceandfire.item.ItemDragonHorn;
import com.github.alexthe666.iceandfire.item.ItemSummoningCrystal;
import com.github.alexthe666.iceandfire.mixin.ItemPropertiesAccessor;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.io.IOException;

public class IafClientSetup {

    public static TabulaModel FIRE_DRAGON_BASE_MODEL;
    public static TabulaModel ICE_DRAGON_BASE_MODEL;
    public static TabulaModel SEA_SERPENT_BASE_MODEL;
    public static TabulaModel LIGHTNING_DRAGON_BASE_MODEL;
    private static ShaderInstance rendertypeDreadPortalShader;
    public static final ResourceLocation GHOST_CHEST_LOCATION = new ResourceLocation(IceAndFire.MODID, "models/ghost/ghost_chest");
    public static final ResourceLocation GHOST_CHEST_LEFT_LOCATION = new ResourceLocation(IceAndFire.MODID, "models/ghost/ghost_chest_left");
    public static final ResourceLocation GHOST_CHEST_RIGHT_LOCATION = new ResourceLocation(IceAndFire.MODID, "models/ghost/ghost_chest_right");


    public static void clientInit() {
        EntityRendererRegistry.register(IafEntityRegistry.FIRE_DRAGON.get(), x -> new RenderDragonBase(x, FIRE_DRAGON_BASE_MODEL, 0));
        EntityRendererRegistry.register(IafEntityRegistry.ICE_DRAGON.get(), manager -> new RenderDragonBase(manager, ICE_DRAGON_BASE_MODEL, 1));
        EntityRendererRegistry.register(IafEntityRegistry.LIGHTNING_DRAGON.get(), manager -> new RenderLightningDragon(manager, LIGHTNING_DRAGON_BASE_MODEL, 2));
        EntityRendererRegistry.register(IafEntityRegistry.DRAGON_EGG.get(), RenderDragonEgg::new);
        EntityRendererRegistry.register(IafEntityRegistry.DRAGON_ARROW.get(), RenderDragonArrow::new);
        EntityRendererRegistry.register(IafEntityRegistry.DRAGON_SKULL.get(), manager -> new RenderDragonSkull(manager, FIRE_DRAGON_BASE_MODEL, ICE_DRAGON_BASE_MODEL, LIGHTNING_DRAGON_BASE_MODEL));
        EntityRendererRegistry.register(IafEntityRegistry.FIRE_DRAGON_CHARGE.get(), manager -> new RenderDragonFireCharge(manager, true));
        EntityRendererRegistry.register(IafEntityRegistry.ICE_DRAGON_CHARGE.get(), manager -> new RenderDragonFireCharge(manager, false));
        EntityRendererRegistry.register(IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), RenderDragonLightningCharge::new);
        EntityRendererRegistry.register(IafEntityRegistry.HIPPOGRYPH_EGG.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(IafEntityRegistry.HIPPOGRYPH.get(), RenderHippogryph::new);
        EntityRendererRegistry.register(IafEntityRegistry.STONE_STATUE.get(), RenderStoneStatue::new);
        EntityRendererRegistry.register(IafEntityRegistry.GORGON.get(), RenderGorgon::new);
        EntityRendererRegistry.register(IafEntityRegistry.PIXIE.get(), RenderPixie::new);
        EntityRendererRegistry.register(IafEntityRegistry.CYCLOPS.get(), RenderCyclops::new);
        EntityRendererRegistry.register(IafEntityRegistry.SIREN.get(), RenderSiren::new);
        EntityRendererRegistry.register(IafEntityRegistry.HIPPOCAMPUS.get(), RenderHippocampus::new);
        EntityRendererRegistry.register(IafEntityRegistry.DEATH_WORM.get(), RenderDeathWorm::new);
        EntityRendererRegistry.register(IafEntityRegistry.DEATH_WORM_EGG.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(IafEntityRegistry.COCKATRICE.get(), RenderCockatrice::new);
        EntityRendererRegistry.register(IafEntityRegistry.COCKATRICE_EGG.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(IafEntityRegistry.STYMPHALIAN_BIRD.get(), RenderStymphalianBird::new);
        EntityRendererRegistry.register(IafEntityRegistry.STYMPHALIAN_FEATHER.get(), RenderStymphalianFeather::new);
        EntityRendererRegistry.register(IafEntityRegistry.STYMPHALIAN_ARROW.get(), RenderStymphalianArrow::new);
        EntityRendererRegistry.register(IafEntityRegistry.TROLL.get(), RenderTroll::new);
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_WORKER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexWorker(), 0.5F));
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_SOLDIER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexSoldier(), 0.75F));
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_QUEEN.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexQueen(), 1.25F));
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_EGG.get(), RenderMyrmexEgg::new);
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_SENTINEL.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexSentinel(), 0.85F));
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_ROYAL.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.75F));
        EntityRendererRegistry.register(IafEntityRegistry.MYRMEX_SWARMER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.25F));
        EntityRendererRegistry.register(IafEntityRegistry.AMPHITHERE.get(), RenderAmphithere::new);
        EntityRendererRegistry.register(IafEntityRegistry.AMPHITHERE_ARROW.get(), RenderAmphithereArrow::new);
        EntityRendererRegistry.register(IafEntityRegistry.SEA_SERPENT.get(), manager -> new RenderSeaSerpent(manager, SEA_SERPENT_BASE_MODEL));
        EntityRendererRegistry.register(IafEntityRegistry.SEA_SERPENT_BUBBLES.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.SEA_SERPENT_ARROW.get(), RenderSeaSerpentArrow::new);
        EntityRendererRegistry.register(IafEntityRegistry.CHAIN_TIE.get(), RenderChainTie::new);
        EntityRendererRegistry.register(IafEntityRegistry.PIXIE_CHARGE.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.TIDE_TRIDENT.get(), RenderTideTrident::new);
        EntityRendererRegistry.register(IafEntityRegistry.MOB_SKULL.get(), manager -> new RenderMobSkull(manager, SEA_SERPENT_BASE_MODEL));
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_SCUTTLER.get(), RenderDreadScuttler::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_GHOUL.get(), RenderDreadGhoul::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_BEAST.get(), RenderDreadBeast::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_SCUTTLER.get(), RenderDreadScuttler::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_THRALL.get(), RenderDreadThrall::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_LICH.get(), RenderDreadLich::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_LICH_SKULL.get(), RenderDreadLichSkull::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_KNIGHT.get(), RenderDreadKnight::new);
        EntityRendererRegistry.register(IafEntityRegistry.DREAD_HORSE.get(), RenderDreadHorse::new);
        EntityRendererRegistry.register(IafEntityRegistry.HYDRA.get(), RenderHydra::new);
        EntityRendererRegistry.register(IafEntityRegistry.HYDRA_BREATH.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.HYDRA_ARROW.get(), RenderHydraArrow::new);
        EntityRendererRegistry.register(IafEntityRegistry.SLOW_MULTIPART.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.DRAGON_MULTIPART.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.CYCLOPS_MULTIPART.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.HYDRA_MULTIPART.get(), RenderNothing::new);
        EntityRendererRegistry.register(IafEntityRegistry.GHOST.get(), RenderGhost::new);
        EntityRendererRegistry.register(IafEntityRegistry.GHOST_SWORD.get(), RenderGhostSword::new);

        BlockEntityRenderers.register(IafTileEntityRegistry.PODIUM.get(), RenderPodium::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.IAF_LECTERN.get(), RenderLectern::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.EGG_IN_ICE.get(), RenderEggInIce::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.PIXIE_HOUSE.get(), RenderPixieHouse::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.PIXIE_JAR.get(), RenderJar::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.DREAD_PORTAL.get(), RenderDreadPortal::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.DREAD_SPAWNER.get(), RenderDreadSpawner::new);
        BlockEntityRenderers.register(IafTileEntityRegistry.GHOST_CHEST.get(), RenderGhostChest::new);

    }
    
    static {
        setupClient();

        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(new ResourceLocation(IceAndFire.MODID, "rendertype_dread_portal"), DefaultVertexFormat.POSITION_COLOR, (instance) -> {
                rendertypeDreadPortalShader = instance;
            });
        });
    }

    public static ShaderInstance getRendertypeDreadPortalShader() {
        return rendertypeDreadPortalShader;
    }
    
    private static void registerUnclamped(Item item, ResourceLocation id, ItemPropertyFunction property) {
        (ItemPropertiesAccessor.getPROPERTIES().computeIfAbsent(item, (itemx) -> {
            return Maps.newHashMap();
        })).put(id, property);
    }
    
    public static void setupClient() {
        {
            IafGuiRegistry.register();
            EnumSeaSerpentAnimations.initializeSerpentModels();
            DragonAnimationsLibrary.register(EnumDragonPoses.values(), EnumDragonModelTypes.values());

            try {
                SEA_SERPENT_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/seaserpent/seaserpent_base"), new SeaSerpentTabulaModelAnimator());
                FIRE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/firedragon/firedragon_ground"), new FireDragonTabulaModelAnimator());
                ICE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/icedragon/icedragon_ground"), new IceDragonTabulaModelAnimator());
                LIGHTNING_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/lightningdragon/lightningdragon_ground"), new LightningTabulaDragonAnimator());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.GOLD_PILE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.SILVER_PILE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.LECTERN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_OAK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_BIRCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_SPRUCE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_JUNGLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_ACACIA.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PODIUM_DARK_OAK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.FIRE_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.FROST_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.LIGHTNING_LILY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.DRAGON_ICE_SPIKES.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_DESERT_RESIN_BLOCK.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_DESERT_RESIN_GLASS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_JUNGLE_RESIN_BLOCK.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_JUNGLE_RESIN_GLASS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_DESERT_BIOLIGHT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.MYRMEX_JUNGLE_BIOLIGHT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.DREAD_STONE_FACE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.DREAD_TORCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.BURNT_TORCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.EGG_IN_ICE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_EMPTY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_PIXIE_0.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_PIXIE_1.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_PIXIE_2.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_PIXIE_3.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.JAR_PIXIE_4.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_OAK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_BIRCH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.DREAD_SPAWNER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.DREAD_TORCH_WALL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IafBlockRegistry.BURNT_TORCH_WALL.get(), RenderType.cutout());
        ItemPropertyFunction pulling = ItemProperties.getProperty(Items.BOW, new ResourceLocation("pulling"));
        ItemPropertyFunction pull = (stack, worldIn, entity, p) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                ItemDragonBow item = ((ItemDragonBow) stack.getItem());
                return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        };

        
            registerUnclamped(IafItemRegistry.DRAGON_BOW.get().asItem(), new ResourceLocation("pulling"), pulling);
            registerUnclamped(IafItemRegistry.DRAGON_BOW.get().asItem(), new ResourceLocation("pull"), pull);
            registerUnclamped(IafItemRegistry.DRAGON_HORN.get(), new ResourceLocation("iceorfire"), (stack, level, entity, p) -> {
                return ItemDragonHorn.getDragonType(stack) * 0.25F;
            });
            registerUnclamped(IafItemRegistry.SUMMONING_CRYSTAL_FIRE.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            registerUnclamped(IafItemRegistry.SUMMONING_CRYSTAL_ICE.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            registerUnclamped(IafItemRegistry.SUMMONING_CRYSTAL_LIGHTNING.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            registerUnclamped(IafItemRegistry.TIDE_TRIDENT.get(), new ResourceLocation("throwing"), (stack, level, entity, p) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
            });
        }
    }

}
