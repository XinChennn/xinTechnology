# Netty

## 1.package client

### MyClient

```java
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
```

## 2.package handler

### 2.1.MyClientHandler

```java
package cn.ixinjiu.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by XinChen on 2022-08-26
 *
 * @Description:TODO 客户端处理器
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送消息到服务端
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hi, Client~", CharsetUtil.UTF_8));

        // scheduleTaskQueue延时任务队列
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("Long time business processing...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("received message from client" + ctx.channel().remoteAddress() + " send message：" + byteBuf.toString(CharsetUtil.UTF_8));
    }
}
```

### 2.2.MyServerHandler

```java
package cn.ixinjiu.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by XinChen on 2022-08-26
 *
 * @Description:TODO 服务端处理器
 * 自定义的Handler需要继承Netty规定好的HandlerAdapter
 * 才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("received message from client" + ctx.channel().remoteAddress() + " send message：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 发送消息给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("Server received, and give you a ?", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常，关闭通道
        ctx.close();
    }
}
```

## 3.package server

### MyServer

```java
package cn.ixinjiu.netty.server;

import cn.ixinjiu.netty.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by XinChen on 2022-08-26
 *
 * @Description:TODO 服务端启动类
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        // 创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端的启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组boosGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 给pipeline管道设置处理器
                            socketChannel.pipeline().addLast(new MyServerHandler());
                        }
                    });// 给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("XinChen's Server is ready...");
            // 绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
```

## 4.test

### 4.1.server

![](file:///Users/apple/Library/Application%20Support/marktext/images/2022-08-26-16-08-07-image.png?msec=1661501440941)

### 4.2.client

![](file:///Users/apple/Library/Application%20Support/marktext/images/2022-08-26-16-08-30-image.png?msec=1661501440952)

# Channel

## 1.What's Channel?

一种连接到网络套接字或能进行读、写、连接和绑定等I/O操作的组件

## 2.What's provide?

1. 通道当前的状态（例如它是打开？还是已连接？）

2. channel的配置参数（例如接收缓冲区的大小）

3. channel支持的IO操作（例如读、写、连接和绑定），以及处理与channel相关联的所有IO事件和请求的ChannelPipeline


## 3.Get channel status

```java
boolean isOpen(); // 如果通道打开，则返回true  
boolean isRegistered();// 如果通道注册到EventLoop，则返回true  
boolean isActive();// 如果通道处于活动状态并且已连接，则返回true  
boolean isWritable();// 当且仅当I/O线程将立即执行请求的写入操作时，返回true。  
```

## 4.获取单条配置信息 getOption()

```java
ChannelConfig config = channel.config();// 获取配置参数  
// 获取ChannelOption.SO_BACKLOG参数,  
Integer soBackLogConfig = config.getOption(ChannelOption.SO_BACKLOG);  
// 因为我启动器配置的是128，所以我这里获取的soBackLogConfig=128  
```

## 5.获取多条配置信息 getOptions()

```java
ChannelConfig config = channel.config();  
Map<ChannelOption<?>, Object> options = config.getOptions();  
for (Map.Entry<ChannelOption<?>, Object> entry : options.entrySet()) {  
    System.out.println(entry.getKey() + " : " + entry.getValue());}  
/**  
SO_REUSEADDR : false  
WRITE_BUFFER_LOW_WATER_MARK : 32768  
WRITE_BUFFER_WATER_MARK : WriteBufferWaterMark(low: 32768, high: 65536)  
SO_BACKLOG : 128  
以下省略...  
*/  
```

## 6.channel支持的IO操作

```java
@Override  
public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    ctx.channel().writeAndFlush(Unpooled.copiedBuffer("这波啊，这波是肉蛋葱鸡~", CharsetUtil.UTF_8));}  
```

客户端控制台：

> 收到服务端/127.0.0.1:6666的消息：这波啊，这波是肉蛋葱鸡~

连接操作，代码演示：

```java
ChannelFuture connect = channelFuture.channel().connect(new InetSocketAddress("127.0.0.1", 6666));// 一般使用启动器，这种方式不常用  
```

通过channel获取ChannelPipeline，并做相关的处理：

```java
//获取ChannelPipeline对象  
ChannelPipeline pipeline = ctx.channel().pipeline();  
//往pipeline中添加ChannelHandler处理器，装配流水线  
pipeline.addLast(new MyServerHandler());  
```