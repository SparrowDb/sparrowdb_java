package org.sparrow.net.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * Created by mauricio on 24/12/2015.
 */
public class RestResponse
{
    private HttpResponseStatus status;
    private ByteBuf buff;
    private HttpVersion version = HttpVersion.HTTP_1_1;
    private ResponseType responseType;
    private String contentType;

    public enum ResponseType
    {
        IMAGE, TEXT
    }

    public RestResponse() {}

    public RestResponse(HttpResponseStatus status, ByteBuf buff, ResponseType responseType)
    {
        this.status = status;
        this.buff = buff;
        this.responseType = responseType;
    }

    public FullHttpResponse getResponse()
    {
        FullHttpResponse response = new DefaultFullHttpResponse(version, status, buff);
        response.headers().set(CONTENT_TYPE, contentType);
        return response;
    }

    public HttpResponseStatus getStatus()
    {
        return status;
    }

    public void setStatus(HttpResponseStatus status)
    {
        this.status = status;
    }

    public ByteBuf getBuff()
    {
        return buff;
    }

    public void setBuff(ByteBuf buff)
    {
        this.buff = buff;
    }

    public HttpVersion getVersion()
    {
        return version;
    }

    public void setVersion(HttpVersion version)
    {
        this.version = version;
    }

    public ResponseType getResponseType()
    {
        return responseType;
    }

    public void setResponseType(ResponseType responseType)
    {
        setResponseType(responseType, "text/plain; charset=UTF-8");
    }

    public void setResponseType(ResponseType responseType, String mimeType)
    {
        this.responseType = responseType;
        this.contentType = mimeType;
    }
}
