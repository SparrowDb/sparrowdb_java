package org.sparrow.net.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mauricio on 24/12/2015.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<HttpObject>
{
    private static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
    private RestController restController = new RestController();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        if (msg instanceof HttpRequest)
        {
            HttpRequest request = (HttpRequest) msg;
            RestResponse response = restController.processRequest(request.getMethod(), request.getUri());
            ctx.writeAndFlush(response.getResponse()).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
