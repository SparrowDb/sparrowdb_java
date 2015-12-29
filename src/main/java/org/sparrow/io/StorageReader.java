package org.sparrow.io;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * Created by mauricio on 27/12/2015.
 */
public class StorageReader implements IDataReader
{
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(StorageWriter.class);
    private FileChannel fchannel;

    private StorageReader(File file) throws IOException
    {
        try
        {
            if (file.exists())
            {
                fchannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
            }
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
    }

    public static IDataReader open(File file)
    {
        try
        {
            return new StorageReader(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long length() throws IOException
    {
        return fchannel.size();
    }

    @Override
    public long currentPosition()
    {
        try
        {
            return fchannel.position();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int readChunck(long offset, ByteBuffer dst) throws IOException
    {
        return fchannel.read(dst, offset);
    }

    @Override
    public int read(ByteBuffer src)
    {
        try
        {
            return fchannel.read(src);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void close()
    {
        try
        {
            fchannel.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
