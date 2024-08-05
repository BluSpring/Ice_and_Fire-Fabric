package com.github.alexthe666.iceandfire.mixin.fabric;

import com.github.alexthe666.iceandfire.fabric.extensions.RiderSittingEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z"))
    private boolean iaf$shouldSit(LivingEntity instance, Operation<Boolean> original) {
        var value = original.call(instance);

        if (value && instance.getVehicle() != null && instance.getVehicle() instanceof RiderSittingEntity sittingEntity) {
            return sittingEntity.shouldRiderSit();
        }

        return value;
    }
}
