package com.study.example.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 使用 netty 实现 discard 协议。简而言之，就是服务端丢掉接收到的所有数据
 *
 * @author windit
 * Handles a server-side channel.
 * (1)DiscardServerHandler扩展了 ChannelInboundHandlerAdapter，它是 ChannelInboundHandler 的实现。ChannelInboundHandler
 * 提供了您可以重写的各种事件处理程序方法。目前，只需扩展 ChannelInboundHandlerAdapter 就足够了，而不是自己实现处理程序接口。
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * (2) 我们在此处重写事件处理程序方法。每当从客户端收到新数据时，都会使用收到的消息调用此方法。在此示例中，收到的消息的类型为 ByteBuf
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {


        //下面这段try-finally代码是通过打印来验证服务器接受到了消息，否则 还是用下下面这段 try-finally
        ByteBuf in = (ByteBuf) msg;
        try {
            // (1)这个低效的循环实际上可以简化为：System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            // (2) 或者，您可以在此处执行此操作。in.release()
            ReferenceCountUtil.release(msg);
        }


        // Discard the received data silently.
        try {
            // Do something with msg
            // (3)要实现协议，处理程序必须忽略收到的消息。ByteBuf 是一个引用计数的对象，必须通过该方法显式释放。请记住，处理程序负责释放传递给处理程序的任何引用计数对象。通常像这样处理
            ((ByteBuf) msg).release();
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }
    /**
     * (4) 当 Netty 由于 I/O 错误而引发异常时，或者由于处理事件时引发的异常而由处理程序实现引发异常时，使用 Throwable
     * 调用事件处理程序方法。在大多数情况下，应记录捕获的异常，并在此处关闭其关联的通道，尽管此方法的实现可能会有所不同，
     * 具体取决于您要做什么来处理异常情况。例如，您可能希望在关闭连接之前发送带有错误代码的响应消息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
