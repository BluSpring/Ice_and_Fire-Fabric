package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.fabric.FabricClientUtils;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemScaleArmor extends ArmorItem implements IProtectAgainstDragonItem, ArmorTextureItem {

    public EnumDragonArmor armor_type;
    public EnumDragonEgg eggType;

    public ItemScaleArmor(EnumDragonEgg eggType, EnumDragonArmor armorType, CustomArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);
        this.armor_type = armorType;
        this.eggType = eggType;

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            registerRenderer();
        }
    }

    @Override
    public @NotNull String getDescriptionId() {
        switch (this.type) {
            case HELMET:
                return "item.iceandfire.dragon_helmet";
            case CHESTPLATE:
                return "item.iceandfire.dragon_chestplate";
            case LEGGINGS:
                return "item.iceandfire.dragon_leggings";
            case BOOTS:
                return "item.iceandfire.dragon_boots";
        }
        return "item.iceandfire.dragon_helmet";
    }

    private void registerRenderer() {
        FabricClientUtils.registerScaleArmorRenderer(this);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/" + armor_type.name() + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
    }


    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("dragon." + eggType.toString().toLowerCase()).withStyle(eggType.color));
        tooltip.add(Component.translatable("item.dragonscales_armor.desc").withStyle(ChatFormatting.GRAY));
    }
}
