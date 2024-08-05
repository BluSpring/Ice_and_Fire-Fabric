package com.github.alexthe666.iceandfire.mixin.fabric;

import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected int useItemRemaining;

    @Shadow public abstract int getUseItemRemainingTicks();

    @Inject(method = "updateUsingItem", at = @At("HEAD"))
    private void iaf$tickUsingItem(ItemStack usingItem, CallbackInfo ci) {
        if (!usingItem.isEmpty()) {
            this.useItemRemaining = ServerEvents.onEntityStopUsingItem(usingItem, this.getUseItemRemainingTicks());
        }
    }

    @WrapWithCondition(method = "updateUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;onUseTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V"))
    private boolean iaf$useTickIfAboveZero(ItemStack instance, Level level, LivingEntity livingEntity, int count) {
        return this.getUseItemRemainingTicks() > 0;
    }
}
