package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import com.github.alexthe666.iceandfire.fabric.FabricClientUtils;
import com.github.alexthe666.iceandfire.fabric.FabricUtils;
import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class ItemTrollArmor extends ArmorItem implements ArmorTextureItem {

    public EnumTroll troll;

    public ItemTrollArmor(EnumTroll troll, CustomArmorMaterial material, ArmorItem.Type slot) {
        super(material, slot, new Item.Properties()/*.tab(IceAndFire.TAB_ITEMS)*/);
        this.troll = troll;

        FabricUtils.runForEnv(this::registerRenderer, () -> {});
    }

    public static String getName(EnumTroll troll, EquipmentSlot slot) {
        return "%s_troll_leather_%s".formatted(troll.name().toLowerCase(Locale.ROOT), getArmorPart(slot));
    }

    @Override
    public @NotNull ArmorMaterial getMaterial() {
        return troll.material;
    }


    private static String getArmorPart(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> "helmet";
            case CHEST -> "chestplate";
            case LEGS -> "leggings";
            case FEET -> "boots";
            default -> "";
        };
    }

    // this is what you call painfully working around things
    private void registerRenderer() {
        FabricClientUtils.registerTrollArmorRenderer(this);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/armor_troll_" + troll.name().toLowerCase(Locale.ROOT) + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.iceandfire.troll_leather_armor_" + getArmorPart(type.getSlot()) + ".desc").withStyle(ChatFormatting.GREEN));
    }
}
