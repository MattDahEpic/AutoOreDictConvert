package com.mattdahepic.autooredictconv.keypress;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {
    public static KeyHandler instance = new KeyHandler();
    public static KeyBinding keybind;

    public KeyHandler () {
        keybind = new KeyBinding("Convert Ore Dictionary Items",46,"Auto Ore Dictionary Converter");
        ClientRegistry.registerKeyBinding(keybind);
    }
    public static void init () {
        MinecraftForge.EVENT_BUS.register(instance);
        FMLCommonHandler.instance().bus().register(instance);
    }
    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent e) {
        this.handleConvert();
    }
    public void handleConvert () {
        if (keybind.isPressed()) {
            ODCPacket.ODCMessage msg = new ODCPacket.ODCMessage();
            PacketHandler.net.sendToServer(msg);
        }
    }
}
