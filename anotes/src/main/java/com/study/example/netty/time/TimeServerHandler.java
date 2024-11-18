package com.study.example.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 节中要实现的协议是 TIME 协议。它与前面的示例不同，因为它发送一条消息，其中包含一个 32 位整数，而不接收任何请求，并在发送消息后关闭连接。
 * 在此示例中，您将学习如何构造和发送消息，以及如何在完成时关闭连接。
 * 因为我们要忽略任何接收到的数据，而是在建立连接后立即发送消息。所以这次我们不使用  channelRead() 方法，而是要重新 channelActive() 方法
 * @author xuyong
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * (1) 如前所述，当建立连接并准备好生成流量时，将调用该方法。让我们在此方法中编写一个 32 位整数来表示当前时间
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // (2) 要发送新消息，我们需要分配一个包含该消息的新缓冲区。我们将写入一个 32 位整数，因此我们需要一个容量至少为 4 字节的 ByteBuf。
        // 通过以下方式获取当前的 ByteBufAllocator 并分配新的缓冲区。
        final ByteBuf time = ctx.alloc().buffer(4);
        long val = System.currentTimeMillis() / 1000L + 2208988800L;
        log.info("write data:{}",val);
        time.writeInt((int) (val));
        // (3) 像往常一样，我们编写构造的消息。
        //但是等等，翻转在哪里？在 NIO 中发送消息之前，我们不是经常调用 java.NIO.ByteBuffer.flip()吗？ ByteBuf 没有这样的方法，因为它有两个指针；
        // 一个用于读取操作，另一个用于写入操作。写入某些内容时，写入器索引会增加，而读取器索引不会更改。
        // reader 索引和 writer 索引分别表示消息的开始和结束位置。
        //相比之下，NIO 缓冲区没有提供一种干净的方法来在不调用 flip 方法的情况下确定消息内容的开始和结束位置。
        // 当您忘记翻转缓冲区时，您将遇到麻烦，因为不会发送任何内容或不正确的数据。这样的错误在 Netty 中不会发生，因为我们有不同的指针用于不同的操作类型。
        // 随着您习惯了它，您会发现它使您的生活变得更加轻松 - 一种不会翻脸的生活！
        //需要注意的另一点是 ChannelHandlerContext.write()（and  writeAndFlush()） 方法返回 ChannelFuture。ChannelFuture 表示尚未发生的 I/O 操作。
        // 这意味着，任何请求的操作可能尚未执行，因为所有操作在 Netty 中都是异步的。例如，以下代码甚至可能在发送消息之前关闭连接：
        // ```Channel ch = ...;ch.writeAndFlush(message);ch.close();```
        //因此，您需要在 ChannelFuture 完成之后调用 close ()方法，该方法由 write ()方法返回，并在完成写操作之后通知其侦听器。
        // 请注意，close ()也可能不会立即关闭连接，并且它返回一个 ChannelFuture
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
        // (4)当写请求完成时，我们如何得到通知？这与向返回的 ChannelFuture 添加 ChannelFutureListener 一样简单。
        // 在这里，我们创建了一个新的匿名 ChannelFutureListener，它在操作完成时关闭 ChannelFutureListener。
        // 或者，您可以使用预定义的侦听器来简化代码:f.addListener(ChannelFutureListener.CLOSE);



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}