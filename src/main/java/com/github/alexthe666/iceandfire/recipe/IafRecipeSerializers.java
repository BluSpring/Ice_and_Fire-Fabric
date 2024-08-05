package com.github.alexthe666.iceandfire.recipe;

import com.github.alexthe666.iceandfire.IceAndFire;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class IafRecipeSerializers {
    public static final LazyRegistrar<RecipeSerializer<?>> SERIALIZERS = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, IceAndFire.MODID);
    public static final RegistryObject<RecipeSerializer<?>> DRAGONFORGE_SERIALIZER = SERIALIZERS.register("dragonforge", DragonForgeRecipe.Serializer::new);

}
