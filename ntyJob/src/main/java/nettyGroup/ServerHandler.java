package nettyGroup;

import io.netty.channel.*;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    static Set<Channel> connections = new HashSet<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        connections.add(channel);
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(socketAddress+"上线了");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel nowChannel = ctx.channel();
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        msg+=socketAddress+": "+msg;
        System.out.println(msg);
        for (Iterator<Channel> iterator = connections.iterator(); iterator.hasNext(); ) {
            Channel channel =  iterator.next();
            if(channel!=nowChannel){
                channel.writeAndFlush(msg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
