package com.github.alexthe666.iceandfire.fabric;

import com.github.alexthe666.iceandfire.client.model.armor.*;
import com.github.alexthe666.iceandfire.client.render.tile.IceAndFireTEISR;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.item.*;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.common.util.Lazy;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import static com.github.alexthe666.iceandfire.item.IafItemRegistry.*;

// This class is what we call: bro wtf
public class FabricClientUtils {
    public static void registerTrollArmorRenderer(ItemTrollArmor armor) {
        var outerModel = new ModelTrollArmor(false);
        var innerModel = new ModelTrollArmor(false);

        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            HumanoidModel<?> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")))), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }, armor);
    }

    public static void registerSilverArmorRenderer(ItemSilverArmor armor) {
        var outerModel = new ModelSilverArmor(false);
        var innerModel = new ModelSilverArmor(true);

        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            HumanoidModel<?> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")))), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }, armor);
    }

    private static final Object2ObjectArrayMap<ArmorMaterial, HumanoidModel<?>> outerModelCache = new Object2ObjectArrayMap<>();
    private static final Object2ObjectArrayMap<ArmorMaterial, HumanoidModel<?>> innerModelCache = new Object2ObjectArrayMap<>();

    private static final Object2ObjectArrayMap<DragonType, HumanoidModel<?>> scaleOuterModelCache = new Object2ObjectArrayMap<>();
    private static final Object2ObjectArrayMap<DragonType, HumanoidModel<?>> scaleInnerModelCache = new Object2ObjectArrayMap<>();

    public static void registerDragonsteelArmorRenderer(ItemDragonsteelArmor armor) {
        outerModelCache.put(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, new ModelDragonsteelFireArmor(false));
        outerModelCache.put(DRAGONSTEEL_ICE_ARMOR_MATERIAL, new ModelDragonsteelIceArmor(false));
        outerModelCache.put(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, new ModelDragonsteelLightningArmor(false));

        innerModelCache.put(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, new ModelDragonsteelFireArmor(true));
        innerModelCache.put(DRAGONSTEEL_ICE_ARMOR_MATERIAL, new ModelDragonsteelIceArmor(true));
        innerModelCache.put(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, new ModelDragonsteelLightningArmor(true));

        ArmorRendererRegistry.register(((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            if (!(stack.getItem() instanceof ArmorItem armorItem))
                return;

            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            var material = armorItem.getMaterial();
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, ""));

            HumanoidModel<?> model;
            if (inner) {
                model = innerModelCache.getOrDefault(material, armorModel);
            } else {
                model = outerModelCache.getOrDefault(material, armorModel);
            }

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }), armor);
    }

    public static void registerScaleArmorRenderer(ItemScaleArmor armor) {
        scaleOuterModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(false));
        scaleOuterModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(false));
        scaleOuterModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(false));

        scaleInnerModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(true));
        scaleInnerModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(true));
        scaleInnerModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(true));

        ArmorRendererRegistry.register(((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            if (!(stack.getItem() instanceof ItemScaleArmor scaleArmor))
                return;

            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            var dragonType = scaleArmor.armor_type.eggType.dragonType;
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, ""));

            HumanoidModel<?> model;
            if (inner) {
                model = scaleInnerModelCache.getOrDefault(dragonType, armorModel);
            } else {
                model = scaleOuterModelCache.getOrDefault(dragonType, armorModel);
            }

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }), armor);
    }

    public static void registerSeaSerpentArmorRenderer(ItemSeaSerpentArmor armor) {
        var outerModel = new ModelSeaSerpentArmor(false);
        var innerModel = new ModelSeaSerpentArmor(false);

        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            HumanoidModel<?> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")))), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }, armor);
    }

    public static void registerBlockItemRenderer(BlockItemWithRender item) {
        Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new IceAndFireTEISR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

        BuiltinItemRendererRegistry.INSTANCE.register(item, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
            renderer.get().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
        }));
    }
}
