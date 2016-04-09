package com.mattdahepic.autooredictconv.keypress;

import com.mattdahepic.autooredictconv.AutoOreDictConv;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper net;
    private static int nextPacketId = 0;

    public static void initPackets() {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(AutoOreDictConv.MODID.toUpperCase());
        registerMessage(ODCPacket.class, ODCPacket.ODCMessage.class,Side.SERVER);
    }

    private static void registerMessage(Class packet, Class message, Side receiver) {
        net.registerMessage(packet, message, nextPacketId, receiver);
        ++nextPacketId;
    }
}
