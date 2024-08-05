package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.fabric.FabricClientUtils;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemSilverArmor extends ArmorItem implements ArmorTextureItem {

    public ItemSilverArmor(ArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerRenderer();
        }
    }

    // this could probably have been done better... but oh well.
    private void registerRenderer() {
        FabricClientUtils.registerSilverArmorRenderer(this);
    }

    @Override
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "armor_silver_metal_layer_2" : "armor_silver_metal_layer_1") + ".png";
    }


}