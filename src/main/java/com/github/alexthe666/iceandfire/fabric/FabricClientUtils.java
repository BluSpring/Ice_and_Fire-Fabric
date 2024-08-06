package com.github.alexthe666.iceandfire.fabric;

import com.github.alexthe666.iceandfire.client.model.armor.*;
import com.github.alexthe666.iceandfire.client.render.tile.IceAndFireTEISR;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.item.*;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.common.util.Lazy;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import static com.github.alexthe666.iceandfire.item.IafItemRegistry.*;

// This class is what we call: bro wtf
public class FabricClientUtils {
    public static void registerTrollArmorRenderer(ItemTrollArmor armor) {
        var outerModel = new ModelTrollArmor(false);
        var innerModel = new ModelTrollArmor(false);

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel) -> {
            HumanoidModel<LivingEntity> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")));
            matrices.popPose();
        }, armor);
    }

    public static void registerSilverArmorRenderer(ItemSilverArmor armor) {
        var outerModel = new ModelSilverArmor(false);
        var innerModel = new ModelSilverArmor(true);

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel) -> {
            HumanoidModel<LivingEntity> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")));
            matrices.popPose();
        }, armor);
    }

    private static final Object2ObjectArrayMap<ArmorMaterial, HumanoidModel<LivingEntity>> outerModelCache = new Object2ObjectArrayMap<>();
    private static final Object2ObjectArrayMap<ArmorMaterial, HumanoidModel<LivingEntity>> innerModelCache = new Object2ObjectArrayMap<>();

    private static final Object2ObjectArrayMap<DragonType, HumanoidModel<LivingEntity>> scaleOuterModelCache = new Object2ObjectArrayMap<>();
    private static final Object2ObjectArrayMap<DragonType, HumanoidModel<LivingEntity>> scaleInnerModelCache = new Object2ObjectArrayMap<>();

    public static void registerDragonsteelArmorRenderer(ItemDragonsteelArmor armor) {
        outerModelCache.put(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, new ModelDragonsteelFireArmor(false));
        outerModelCache.put(DRAGONSTEEL_ICE_ARMOR_MATERIAL, new ModelDragonsteelIceArmor(false));
        outerModelCache.put(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, new ModelDragonsteelLightningArmor(false));

        innerModelCache.put(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, new ModelDragonsteelFireArmor(true));
        innerModelCache.put(DRAGONSTEEL_ICE_ARMOR_MATERIAL, new ModelDragonsteelIceArmor(true));
        innerModelCache.put(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, new ModelDragonsteelLightningArmor(true));

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel) -> {
            if (!(stack.getItem() instanceof ArmorItem armorItem))
                return;

            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            var material = armorItem.getMaterial();
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, ""));

            HumanoidModel<LivingEntity> model;
            if (inner) {
                model = innerModelCache.getOrDefault(material, contextModel);
            } else {
                model = outerModelCache.getOrDefault(material, contextModel);
            }

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
            matrices.popPose();
        }, armor);
    }

    public static void registerScaleArmorRenderer(ItemScaleArmor armor) {
        scaleOuterModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(false));
        scaleOuterModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(false));
        scaleOuterModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(false));

        scaleInnerModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(true));
        scaleInnerModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(true));
        scaleInnerModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(true));

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel) -> {
            if (!(stack.getItem() instanceof ItemScaleArmor scaleArmor))
                return;

            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            var dragonType = scaleArmor.armor_type.eggType.dragonType;
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, ""));

            HumanoidModel<LivingEntity> model;
            if (inner) {
                model = scaleInnerModelCache.getOrDefault(dragonType, contextModel);
            } else {
                model = scaleOuterModelCache.getOrDefault(dragonType, contextModel);
            }

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
            matrices.popPose();
        }, armor);
    }

    public static void registerSeaSerpentArmorRenderer(ItemSeaSerpentArmor armor) {
        var outerModel = new ModelSeaSerpentArmor(false);
        var innerModel = new ModelSeaSerpentArmor(true);
        
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel) -> {
            HumanoidModel<LivingEntity> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, armorSlot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, new ResourceLocation(armor.getArmorTexture(stack, entity, armorSlot, "")));
            matrices.popPose();
        }, armor);
    }

    private static ModelDeathWormArmor cachedOuterModel;
    private static ModelDeathWormArmor cachedInnerModel;

    public static void registerDeathwormArmor(ItemDeathwormArmor armor) {
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, slot, ""));
            HumanoidModel<LivingEntity> model;
            if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.HEAD) {
                if (cachedInnerModel == null) {
                    cachedInnerModel = new ModelDeathWormArmor(ModelDeathWormArmor.getBakedModel(true));
                }

                model = cachedInnerModel;
            } else {
                if (cachedOuterModel == null) {
                    cachedOuterModel = new ModelDeathWormArmor(ModelDeathWormArmor.getBakedModel(false));
                }

                model = cachedOuterModel;
            }

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, slot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
            matrices.popPose();
        }, armor);
    }

    private static ModelCopperArmor cachedOuterModelCopper;
    private static ModelCopperArmor cachedInnerModelCopper;

    public static void registerCopperArmor(ItemCopperArmor armor) {
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            var texture = new ResourceLocation(armor.getArmorTexture(stack, entity, slot, ""));
            HumanoidModel<LivingEntity> model;
            if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.HEAD) {
                if (cachedInnerModelCopper == null) {
                    cachedInnerModelCopper = new ModelCopperArmor(true);
                }

                model = cachedInnerModelCopper;
            } else {
                if (cachedOuterModelCopper == null) {
                    cachedOuterModelCopper = new ModelCopperArmor(false);
                }

                model = cachedOuterModelCopper;
            }

            contextModel.copyPropertiesTo(model);
            setPartVisibility(model, slot);
            setupAnim(matrices, entity, model);
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
            matrices.popPose();
        }, armor);
    }

    public static void registerBlockItemRenderer(BlockItemWithRender item) {
        Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(() -> new IceAndFireTEISR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

        BuiltinItemRendererRegistry.INSTANCE.register(item, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
            renderer.get().renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
        }));
    }

    private static void setupAnim(PoseStack poseStack, LivingEntity entity, HumanoidModel<LivingEntity> model) {
        float partialTicks = Minecraft.getInstance().getFrameTime();

        poseStack.pushPose();

        model.attackTime = entity.getAttackAnim(partialTicks);
        model.riding = entity.isPassenger();
        model.young = entity.isBaby();

        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float g = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float h = g - f;

        float k = 0.0f;
        Direction direction;

        if (entity.hasPose(Pose.SLEEPING) && (direction = entity.getBedOrientation()) != null) {
            k = entity.getEyeHeight(Pose.STANDING) - 0.1f;
            poseStack.translate((float)(-direction.getStepX()) * k, 0.0f, (float)(-direction.getStepZ()) * k);
        }

        k = 0.0f;

        float j = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        float l = 0.0f;

        if (!entity.isPassenger() && entity.isAlive()) {
            k = entity.walkAnimation.speed(partialTicks);
            l = entity.walkAnimation.position(partialTicks);
            if (entity.isBaby()) {
                l *= 3.0f;
            }
            if (k > 1.0f) {
                k = 1.0f;
            }
        }

        float i = (float) (entity.tickCount) + partialTicks;

        model.prepareMobModel(entity, l, k, partialTicks);
        model.setupAnim(entity, l, k, i, h, j);
    }

    private static void setPartVisibility(HumanoidModel<LivingEntity> model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
                break;
            case LEGS:
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }

    }
}
