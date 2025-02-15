package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class IafPlacementFilterRegistry {
    public static final LazyRegistrar<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = LazyRegistrar.create(Registries.PLACEMENT_MODIFIER_TYPE, IceAndFire.MODID);

    public static RegistryObject<PlacementModifierType<CustomBiomeFilter>> CUSTOM_BIOME_FILTER = PLACEMENT_MODIFIER_TYPES.register("biome_extended", () -> () -> CustomBiomeFilter.CODEC);
}
