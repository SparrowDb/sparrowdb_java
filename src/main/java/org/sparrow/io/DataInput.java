package org.sparrow.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 09/01/2016.
 */
public class DataInput
{
    public static byte[] load(IDataReader dataReader, long offset)
    {
        ByteBuffer dataBuffer = null;
        ByteBuffer buff = null;
        try
        {
            dataBuffer = ByteBuffer.allocate(4);
            dataReader.readChunck(offset, dataBuffer);
            dataBuffer.flip();
            int dataSize = dataBuffer.getInt();
            buff = ByteBuffer.allocate(dataSize);
            dataReader.readChunck(offset + 4, buff);
            dataBuffer.clear();
            return buff.array();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(dataBuffer!=null) dataBuffer.clear();
            if(buff!=null) buff.clear();
        }
        return null;
    }
}
