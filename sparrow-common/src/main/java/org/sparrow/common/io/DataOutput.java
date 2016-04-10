package org.sparrow.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 09/01/2016.
 */
public class DataOutput
{
    public static void save(IDataWriter dataWriter, byte[] bytes)
    {
        ByteBuffer dataBuff = null;
        ByteBuffer dataSizeBuff = null;

        try
        {
            dataBuff = ByteBuffer.wrap(bytes);
            dataSizeBuff = ByteBuffer.allocate(4);
            dataSizeBuff.putInt(bytes.length);
            dataSizeBuff.flip();
            dataWriter.write(dataSizeBuff);
            dataWriter.write(dataBuff);
            dataWriter.fsync();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (dataBuff != null) dataBuff.clear();
            if (dataSizeBuff != null) dataSizeBuff.clear();
        }
    }
}
