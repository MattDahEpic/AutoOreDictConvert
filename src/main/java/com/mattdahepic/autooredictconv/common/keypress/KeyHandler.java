package com.mattdahepic.autooredictconv.common.keypress;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeyHandler {
    public static KeyMapping keybind = new KeyMapping("Convert Ore Dictionary Items", GLFW.GLFW_KEY_C,"Auto Ore Dictionary Converter");

    public static void register (RegisterKeyMappingsEvent event) {
        event.register(keybind);
    }

    public static void onKeyInput(InputEvent.Key e) {
        if (keybind.consumeClick()) {
            PacketHandler.INSTANCE.sendToServer(new ConvertPacket());
        }
    }
}
