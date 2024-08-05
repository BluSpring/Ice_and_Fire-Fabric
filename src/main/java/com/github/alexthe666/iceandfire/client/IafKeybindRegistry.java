package com.github.alexthe666.iceandfire.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class IafKeybindRegistry {
    public static KeyMapping dragon_fireAttack;
    public static KeyMapping dragon_strike;
    public static KeyMapping dragon_down;
    public static KeyMapping dragon_change_view;

    public static void init() {
        dragon_fireAttack = new KeyMapping("key.dragon_fireAttack", 82, "key.categories.gameplay");
        dragon_strike = new KeyMapping("key.dragon_strike", 71, "key.categories.gameplay");
        dragon_down = new KeyMapping("key.dragon_down", 88, "key.categories.gameplay");
        dragon_change_view = new KeyMapping("key.dragon_change_view", 296, "key.categories.misc");
        KeyBindingHelper.registerKeyBinding(dragon_fireAttack);
        KeyBindingHelper.registerKeyBinding(dragon_strike);
        KeyBindingHelper.registerKeyBinding(dragon_down);
        KeyBindingHelper.registerKeyBinding(dragon_change_view);
    }
}
