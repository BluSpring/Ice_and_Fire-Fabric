package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemMobSkull;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.item.Item;

import java.util.Locale;

public enum EnumSkullType {
    HIPPOGRYPH,
    CYCLOPS,
    COCKATRICE,
    STYMPHALIAN,
    TROLL,
    AMPHITHERE,
    SEASERPENT,
    HYDRA;

    public String itemResourceName;
    public RegistryObject<Item> skull_item;

    EnumSkullType() {
        itemResourceName = this.name().toLowerCase(Locale.ROOT) + "_skull";
    }

    public static void initItems() {
        for (EnumSkullType skull : EnumSkullType.values()) {
            skull.skull_item = IafItemRegistry.registerItem(skull.itemResourceName, () -> new ItemMobSkull(skull));
        }
    }
}
