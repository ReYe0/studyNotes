package com.study.example.netty.discard;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 *
 * @author xuyong
 */
public class DiscardServer {

    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }

    public void run() throws Exception {
        // (1) NioEventLoopGroup 是一个处理 I/O 操作的多线程事件循环。Netty 为不同类型的传输提供了各种 EventLoopGroup 实现。
        // 在此示例中，我们正在实现一个服务器端应用程序，因此将使用两个 NioEventLoopGroup。第一个，通常称为 'boss'，接受传入连接。
        // 第二个，通常称为 'worker'，一旦 Boss 接受连接并将接受的连接注册  到 worker，它就会处理接受连接的流量。
        // 使用多少个 Thread 以及它们如何映射到创建的 Channel取决于 EventLoopGroup 实现，甚至可以通过构造函数进行配置。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // (2)  ServerBootstrap 是设置服务器的帮助程序类。您可以直接使用 Channel 设置服务器。但是，请注意，这是一个乏味的过程，在大多数情况下您不需要这样做。
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // (3) 在这里，我们指定使用 NioServerSocketChannel 类，该类用于实例化新的 Channel 以接受传入连接
                    .channel(NioServerSocketChannel.class)
                    // (4) 此处指定的处理程序将始终由新接受的 Channel 进行评估。ChannelInitializer 是一个特殊的处理程序，旨在帮助用户配置新的 Channel。
                    // 您很可能希望通过添加一些处理程序来配置新 Channel 的 ChannelPipeline，例如实现您的网络应用程序。随着应用程序变得复杂，
                    // 您可能会向管道添加更多处理程序，并最终将此匿名类提取到顶级类中
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // (5) 您还可以设置特定于实施的参数。我们正在编写一个 TCP/IP 服务器，因此我们可以设置套接字选项，例如  tcpNoDelay and keepAlive。
                    // 请参考 ChannelOption 的 apidocs 和具体的 ChannelConfig 实现
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // (6) 你注意到了吗  option() and childOption() ？ option() 用于接受传入连接的 NioServerSocketChannel。
                    // childOption() 用于父 ServerChannel 接受的 Channel，在本例中为 NioSocketChannel
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            // (7) 我们现在准备好了。剩下的就是绑定到端口并启动服务器。这里，我们绑定到机器中所有 NIC（网络接口卡）的端口。现在，您可以根据需要多次调用该方法（使用不同的绑定地址）
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
