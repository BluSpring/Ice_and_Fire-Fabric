package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.model.armor.ModelCopperArmor;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public class ItemCopperArmor extends ArmorItem implements ArmorTextureItem {

    public ItemCopperArmor(ArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerArmorRenderer();
        }
    }

    @Environment(EnvType.CLIENT)
    private ModelCopperArmor cachedOuterModel;
    @Environment(EnvType.CLIENT)
    private ModelCopperArmor cachedInnerModel;

    private void registerArmorRenderer() {
        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel, armorModel) -> {
            var texture = new ResourceLocation(getArmorTexture(stack, entity, slot, ""));
            if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.HEAD) {
                if (cachedInnerModel == null) {
                    cachedInnerModel = new ModelCopperArmor(true);
                }

                cachedInnerModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            } else {
                if (cachedOuterModel == null) {
                    cachedOuterModel = new ModelCopperArmor(true);
                }

                cachedOuterModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }, this);
    }

    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "armor_copper_metal_layer_2" : "armor_copper_metal_layer_1") + ".png";
    }


}