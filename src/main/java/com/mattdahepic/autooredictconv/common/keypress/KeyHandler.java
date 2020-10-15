package com.mattdahepic.autooredictconv.common.keypress;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static KeyBinding keybind = new KeyBinding("Convert Ore Dictionary Items", GLFW.GLFW_KEY_C,"Auto Ore Dictionary Converter");

    public static void register () {
        ClientRegistry.registerKeyBinding(keybind);
    }

    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (keybind.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new ConvertPacket());
        }
    }
}
