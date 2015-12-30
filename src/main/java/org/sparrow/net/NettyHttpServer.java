package org.sparrow.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.net.http.HttpRequestHandler;
import org.sparrow.thrift.Server;

import java.net.InetSocketAddress;

/**
 * Created by mauricio on 24/12/2015.
 */
public class NettyHttpServer implements Server
{
    private static Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);
    private InetSocketAddress address;
    private HttpServerWorker server;

    public NettyHttpServer(String host, int port)
    {
        this.address = new InetSocketAddress(host, port);
    }

    @Override
    public void start()
    {
        if (server == null)
        {
            server = new HttpServerWorker(address);
            server.start();
        }
    }

    @Override
    public void stop()
    {
        if (server != null)
        {
            server.channel.close();
            try
            {
                server.join();
            }
            catch (InterruptedException e)
            {
                logger.error("Interrupted while waiting http server to stop", e);
            }
            server = null;
        }
    }

    @Override
    public boolean isRunning()
    {
        return false;
    }

    private class HttpServerWorker extends Thread
    {
        private ServerBootstrap serverBootstrap;
        private Channel channel;
        private InetSocketAddress address;

        public HttpServerWorker(InetSocketAddress address)
        {
            this.address = address;
            serverBootstrap = new ServerBootstrap();
        }

        public void run()
        {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            serverBootstrap.group(workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new HttpChannelPipelineFactory());

            try
            {
                channel = serverBootstrap.bind(this.address).sync().channel();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            try
            {
                channel.closeFuture().sync();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class HttpChannelPipelineFactory extends ChannelInitializer
    {
        @Override
        protected void initChannel(Channel channel) throws Exception
        {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpRequestDecoder());
            pipeline.addLast(new HttpResponseEncoder());
            pipeline.addLast(new HttpRequestHandler());
        }
    }
}
