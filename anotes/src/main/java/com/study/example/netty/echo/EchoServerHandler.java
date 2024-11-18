package com.study.example.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 使用 netty 实现 echo 协议。简而言之，就是你发送什么，服务器就回复什么
 *
 * @author xuyong
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // (1) ChannelHandlerContext 对象提供了各种操作，使您能够触发各种 I/O 事件和操作。
        // 在这里，我们调用  write(Object) 以逐字写入收到的消息。请注意，我们没有像 DISCARD 示例中那样发布收到的消息。这是因为 Netty 在将其写出到网络时为您释放了它
        ctx.write(msg);
        // (2)ctx.write(Object)不会将消息写出到网络。它在内部缓冲，然后由 ctx.flush() 刷出网络 .或者，您可以要求简洁 使用 ctx.writeAndFlush(msg)
        ctx.flush();
    }
}
