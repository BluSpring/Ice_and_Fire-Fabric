package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.model.armor.ModelSeaSerpentArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelSilverArmor;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
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

public class ItemSilverArmor extends ArmorItem implements ArmorTextureItem {

    public ItemSilverArmor(ArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerRenderer();
        }
    }

    private void registerRenderer() {
        var outerModel = new ModelSilverArmor(false);
        var innerModel = new ModelSilverArmor(false);

        ArmorRendererRegistry.register((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            HumanoidModel<?> model = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD ? innerModel : outerModel;

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(new ResourceLocation(getArmorTexture(stack, entity, armorSlot, "")))), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }, this);
    }

    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "armor_silver_metal_layer_2" : "armor_silver_metal_layer_1") + ".png";
    }


}