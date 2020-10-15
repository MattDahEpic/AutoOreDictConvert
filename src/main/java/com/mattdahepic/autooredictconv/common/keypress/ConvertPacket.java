package com.mattdahepic.autooredictconv.common.keypress;

import com.mattdahepic.autooredictconv.common.convert.Conversions;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ConvertPacket {
    public void encode(PacketBuffer buffer) {
        //NO-OP
    }
    public static ConvertPacket decode(PacketBuffer buffer) {
        return new ConvertPacket();
    }
    public static void handle (ConvertPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Conversions.convert(Objects.requireNonNull(ctx.get().getSender()));
            ctx.get().getSender().sendStatusMessage(new TranslationTextComponent("autooredictconv.converting"),true);
        });
        ctx.get().setPacketHandled(true);
    }
}
