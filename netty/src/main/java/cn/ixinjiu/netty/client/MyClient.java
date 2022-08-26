package cn.ixinjiu.netty.client;

import cn.ixinjiu.netty.handler.MyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by XinChen on 2022-08-26
 *
 * @Description:TODO 客户端启动类
 */
public class MyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组
            bootstrap.group(eventExecutors)
                    // 设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    // 使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加客户端通道的处理器
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
            System.out.println("The Client is ready~");
            // 连接服务端
            // ChannelFuture提供操作完成时一种异步通知的方式。一般在Socket编程中，等待响应结果都是同步阻塞的，
            // 而Netty则不会造成阻塞，因为ChannelFuture是采取类似`观察者模式`的形式进行获取结果。
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            // 添加监听
            channelFuture.addListener(new ChannelFutureListener() {
                // 使用匿名内部类，ChannelFutureListener接口
                // 重写operationComplete方法
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.err.println("The connection is successful!");
                    }else {
                        System.err.println("The connection is failed!");
                    }
                }
            });
            // 对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}
