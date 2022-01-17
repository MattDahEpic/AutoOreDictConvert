package com.mattdahepic.autooredictconv.common.keypress;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static KeyMapping keybind = new KeyMapping("Convert Ore Dictionary Items", GLFW.GLFW_KEY_C,"Auto Ore Dictionary Converter");

    public static void register () {
        ClientRegistry.registerKeyBinding(keybind);
    }

    public static void onKeyInput(InputEvent.KeyInputEvent e) {
        if (keybind.consumeClick()) {
            PacketHandler.INSTANCE.sendToServer(new ConvertPacket());
        }
    }
}
