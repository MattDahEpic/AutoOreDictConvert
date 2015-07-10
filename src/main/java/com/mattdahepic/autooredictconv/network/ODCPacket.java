package com.mattdahepic.autooredictconv.network;

//props to diesieben07 on #minecraftforge irc

//import com.mattdahepic.autooredictconv.convert.Convert;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ODCPacket implements IMessageHandler<ODCPacket.ODCMessage, IMessage> {
    @Override
    public IMessage onMessage (ODCMessage message, MessageContext ctx) {
        //correct side?
        if (ctx.side.isServer()) {
           //update inventory bitshes!
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
