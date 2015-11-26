package com.mattdahepic.autooredictconv.keypress;

import com.mattdahepic.autooredictconv.convert.Convert;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ODCPacket implements IMessageHandler<ODCPacket.ODCMessage,IMessage> {
    public IMessage onMessage(ODCPacket.ODCMessage message, MessageContext ctx) {
        if(ctx.side.isServer()) {
            Convert.convertPlayer(ctx.getServerHandler().playerEntity);
            ctx.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("Converting!"));
        }

        return null;
    }

    public static class ODCMessage implements IMessage {
        public ODCMessage() {
        }

        public void fromBytes(ByteBuf buf) {
        }

        public void toBytes(ByteBuf buf) {
        }
    }
}
