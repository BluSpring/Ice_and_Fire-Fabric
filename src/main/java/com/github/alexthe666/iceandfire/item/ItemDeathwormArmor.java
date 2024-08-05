package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.model.armor.ModelCopperArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDeathWormArmor;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

import java.util.function.Consumer;

public class ItemDeathwormArmor extends ArmorItem implements ArmorTextureItem {

    public ItemDeathwormArmor(ArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerArmorRenderer();
        }
    }

    @Environment(EnvType.CLIENT)
    private ModelDeathWormArmor cachedOuterModel;
    @Environment(EnvType.CLIENT)
    private ModelDeathWormArmor cachedInnerModel;

    private void registerArmorRenderer() {
        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel, armorModel) -> {
            var texture = new ResourceLocation(getArmorTexture(stack, entity, slot, ""));
            if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.HEAD) {
                if (cachedInnerModel == null) {
                    cachedInnerModel = new ModelDeathWormArmor(ModelDeathWormArmor.getBakedModel(true));
                }

                cachedInnerModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            } else {
                if (cachedOuterModel == null) {
                    cachedOuterModel = new ModelDeathWormArmor(ModelDeathWormArmor.getBakedModel(true));
                }

                cachedOuterModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }, this);
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (this.getMaterial() == IafItemRegistry.DEATHWORM_2_ARMOR_MATERIAL) {
            return "iceandfire:textures/models/armor/armor_deathworm_red" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
        } else if (this.getMaterial() == IafItemRegistry.DEATHWORM_1_ARMOR_MATERIAL) {
            return "iceandfire:textures/models/armor/armor_deathworm_white" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
        } else {
            return "iceandfire:textures/models/armor/armor_deathworm_yellow" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
        }
    }
}
