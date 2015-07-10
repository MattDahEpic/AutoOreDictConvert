package com.mattdahepic.autooredictconv.network;

//props to CatDany: https://gist.github.com/CatDany/4a3df7fcb3c8270cf70b

import com.mattdahepic.autooredictconv.OreDictConv;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper net;
    public static void initPackets () {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(OreDictConv.MODID.toUpperCase());
        registerMessage(ODCPacket.class,ODCPacket.ODCMessage.class);
    }
    private static int nextPacketId = 0;
    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet,message,nextPacketId, Side.CLIENT);
        net.registerMessage(packet,message,nextPacketId,Side.SERVER);
        nextPacketId++;
    }
}
