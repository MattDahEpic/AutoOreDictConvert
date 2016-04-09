package com.mattdahepic.autooredictconv.keypress;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {
    public static KeyBinding keybind = new KeyBinding("Convert Ore Dictionary Items",46,AutoOreDictConv.NAME);

    public KeyHandler () {
        ClientRegistry.registerKeyBinding(keybind);
    }
    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent e) {
        if (keybind.isPressed()) {
            ODCPacket.ODCMessage msg = new ODCPacket.ODCMessage();
            PacketHandler.net.sendToServer(msg);
        }
    }
}
