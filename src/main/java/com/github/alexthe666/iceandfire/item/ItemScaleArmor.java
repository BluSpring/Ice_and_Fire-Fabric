package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.client.model.armor.*;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRendererRegistry;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static com.github.alexthe666.iceandfire.item.IafItemRegistry.*;
import static com.github.alexthe666.iceandfire.item.IafItemRegistry.DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL;

public class ItemScaleArmor extends ArmorItem implements IProtectAgainstDragonItem, ArmorTextureItem {

    public EnumDragonArmor armor_type;
    public EnumDragonEgg eggType;

    public ItemScaleArmor(EnumDragonEgg eggType, EnumDragonArmor armorType, CustomArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);
        this.armor_type = armorType;
        this.eggType = eggType;
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

    @Environment(EnvType.CLIENT)
    private Object2ObjectArrayMap<DragonType, HumanoidModel<?>> outerModelCache = new Object2ObjectArrayMap<>();
    @Environment(EnvType.CLIENT)
    private Object2ObjectArrayMap<DragonType, HumanoidModel<?>> innerModelCache = new Object2ObjectArrayMap<>();

    private void registerRenderer() {
        outerModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(false));
        outerModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(false));
        outerModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(false));

        innerModelCache.put(DragonType.FIRE, new ModelFireDragonScaleArmor(true));
        innerModelCache.put(DragonType.ICE, new ModelIceDragonScaleArmor(true));
        innerModelCache.put(DragonType.LIGHTNING, new ModelLightningDragonScaleArmor(true));

        ArmorRendererRegistry.register(((matrices, vertexConsumers, stack, entity, armorSlot, light, contextModel, armorModel) -> {
            if (!(stack.getItem() instanceof ItemScaleArmor scaleArmor))
                return;

            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            var dragonType = scaleArmor.armor_type.eggType.dragonType;
            var texture = new ResourceLocation(getArmorTexture(stack, entity, armorSlot, ""));

            HumanoidModel<?> model;
            if (inner) {
                model = innerModelCache.getOrDefault(dragonType, armorModel);
            } else {
                model = outerModelCache.getOrDefault(dragonType, armorModel);
            }

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture)), light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }), this);
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
