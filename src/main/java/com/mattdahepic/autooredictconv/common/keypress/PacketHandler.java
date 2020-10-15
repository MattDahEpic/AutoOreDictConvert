package com.mattdahepic.autooredictconv.common.keypress;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("autooredictconv","main"),() -> PROTOCOL_VERSION,PROTOCOL_VERSION::equals,PROTOCOL_VERSION::equals);

    private static int nextPacketId = 0;

    public static void initPackets() {
        registerMessage(ConvertPacket.class, ConvertPacket::encode, ConvertPacket::decode, ConvertPacket::handle);
    }

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer,MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        INSTANCE.registerMessage(nextPacketId,messageType,encoder,decoder,handler);
        ++nextPacketId;
    }
}
