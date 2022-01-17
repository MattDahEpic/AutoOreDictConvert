package com.mattdahepic.autooredictconv.common.keypress;

import com.mattdahepic.autooredictconv.common.convert.Conversions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ConvertPacket {
    public void encode(FriendlyByteBuf buffer) {
        //NO-OP
    }
    public static ConvertPacket decode(FriendlyByteBuf buffer) {
        return new ConvertPacket();
    }
    public static void handle (ConvertPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Conversions.convert(Objects.requireNonNull(ctx.get().getSender()));
            ctx.get().getSender().displayClientMessage(new TranslatableComponent("autooredictconv.converting"),true);
        });
        ctx.get().setPacketHandled(true);
    }
}
