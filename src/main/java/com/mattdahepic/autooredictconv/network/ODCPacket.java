package com.mattdahepic.autooredictconv.network;

//props to diesieben07 on #minecraftforge irc

//import com.mattdahepic.autooredictconv.convert.Convert;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ODCPacket implements IMessageHandler<ODCPacket.ODCMessage, IMessage> {
    @Override
    public IMessage onMessage (ODCMessage message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            //Convert.convert(ctx.getServerHandler().playerEntity);
        }
        return null;
    }
    public static class ODCMessage implements IMessage {
        public ODCMessage () {}
        @Override
        public void fromBytes(ByteBuf buf) {}
        @Override
        public void toBytes(ByteBuf buf) {}
    }
}
