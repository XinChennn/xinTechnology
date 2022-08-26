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
