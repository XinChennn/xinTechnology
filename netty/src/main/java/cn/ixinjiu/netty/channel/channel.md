# Channel
## 1.What's Channel?
一种连接到网络套接字或能进行读、写、连接和绑定等I/O操作的组件

## 2.What's provide?
1.通道当前的状态（例如它是打开？还是已连接？）
2.channel的配置参数（例如接收缓冲区的大小）
3.channel支持的IO操作（例如读、写、连接和绑定），以及处理与channel相关联的所有IO事件和请求的ChannelPipeline

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
    System.out.println(entry.getKey() + " : " + entry.getValue());
}
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
    ctx.channel().writeAndFlush(Unpooled.copiedBuffer("这波啊，这波是肉蛋葱鸡~", CharsetUtil.UTF_8));
}
```
客户端控制台：
> //收到服务端/127.0.0.1:6666的消息：这波啊，这波是肉蛋葱鸡~

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