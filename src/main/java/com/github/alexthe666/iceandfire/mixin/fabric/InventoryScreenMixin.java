package com.github.alexthe666.iceandfire.mixin.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    @WrapOperation(method = "renderEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventory(Lnet/minecraft/client/gui/GuiGraphics;IIILorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private static void setBodyOffset(GuiGraphics guiGraphics, int x, int y, int scale, Quaternionf pose, Quaternionf cameraOrientation, LivingEntity entity, Operation<Void> original) {
        var bodyRot = entity.yBodyRotO;
        var xRotO = entity.xRotO;
        entity.yBodyRotO = entity.yBodyRot;
        entity.xRotO = entity.getXRot();
        original.call(guiGraphics, x, y, scale, pose, cameraOrientation, entity);
        entity.yBodyRotO = bodyRot;
        entity.xRotO = xRotO;
    }
}
