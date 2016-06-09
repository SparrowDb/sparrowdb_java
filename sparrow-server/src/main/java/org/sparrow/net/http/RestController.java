package org.sparrow.net.http;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.sparrow.common.DataDefinition;
import org.sparrow.db.Database;
import org.sparrow.db.SparrowDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mauricio on 24/12/2015.
 */
public class RestController
{
    private static final String GET_IMAGE_PATTERN = "^\\/([A-Za-z0-9]{3,20})\\/([A-Za-z0-9]{3,20})$";
    private RestResponse restResponse;
    private String uri;
    private HttpMethod method;

    public RestController()
    {
        restResponse = new RestResponse();
    }

    public RestResponse processRequest(HttpMethod method, String uri)
    {
        this.method = method;
        this.uri = uri;
        process();
        return restResponse;
    }

    private byte[] badMethodMessage()
    {
        String msg = "{\"message\": \"Method " + method.name() + " not supported\"}";
        return msg.getBytes();
    }

    private byte[] badRequestMessage()
    {
        String msg = "{\"message\": \"Wrong url " + uri + "\"}";
        return msg.getBytes();
    }

    private Matcher getUrlMatcher()
    {
        return Pattern.compile(GET_IMAGE_PATTERN).matcher(uri);
    }

    private void sendErrorResponse()
    {
        restResponse.setStatus(HttpResponseStatus.BAD_REQUEST);
        restResponse.setResponseType(RestResponse.ResponseType.TEXT);
        restResponse.setBuff(Unpooled.copiedBuffer(badRequestMessage()));
    }

    private void process()
    {
        if (!method.equals(HttpMethod.GET))
        {
            restResponse.setStatus(HttpResponseStatus.METHOD_NOT_ALLOWED);
            restResponse.setResponseType(RestResponse.ResponseType.TEXT);
            restResponse.setBuff(Unpooled.copiedBuffer(badMethodMessage()));
            return;
        }

        Matcher urlMatcher = getUrlMatcher();
        if (!urlMatcher.matches())
        {
            sendErrorResponse();
            return;
        }

        String dbname = urlMatcher.group(1);
        String imageKey = urlMatcher.group(2);

        Database database = SparrowDatabase.instance.getDatabase(dbname);

        if (database == null)
        {
            sendErrorResponse();
            return;
        }

        DataDefinition dataDefinition = database.getDataWithImageByKey32(imageKey);
        if (dataDefinition == null || dataDefinition.getState() != DataDefinition.DataState.ACTIVE)
        {
            sendErrorResponse();
            return;
        }

        restResponse.setStatus(HttpResponseStatus.OK);
        restResponse.setResponseType(RestResponse.ResponseType.IMAGE, "image/" + dataDefinition.getExtension());
        restResponse.setBuff(Unpooled.copiedBuffer(dataDefinition.getBuffer()));
    }
}
