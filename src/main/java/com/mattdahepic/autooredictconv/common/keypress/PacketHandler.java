package com.mattdahepic.autooredictconv.common.keypress;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf,MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        INSTANCE.registerMessage(nextPacketId,messageType,encoder,decoder,handler);
        ++nextPacketId;
    }
}
