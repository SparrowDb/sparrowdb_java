package org.sparrow.io;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * Created by mauricio on 25/12/2015.
 */
public class StorageWriter implements IDataWriter
{
    private final File file;
    private FileChannel fchannel;

    private StorageWriter(File file) throws IOException
    {
        fchannel = file.exists() ? FileChannel.open(file.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE) :
                                   FileChannel.open(file.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        this.file = file;
    }

    public static IDataWriter open(String file)
    {
        try
        {
            return new StorageWriter(new File(file));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long length()
    {
        try
        {
            return fchannel.size();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long currentPosition()
    {
        try
        {
            return fchannel.position();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long lastModified()
    {
        return file.lastModified();
    }

    @Override
    public void write(byte[] buffer) throws IOException
    {
        write(ByteBuffer.wrap(buffer));
    }

    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        write(ByteBuffer.wrap(buffer, offset, length));
    }

    @Override
    public int write(ByteBuffer src) throws IOException
    {
        fchannel.position(fchannel.size());
        int length = src.remaining();
        while (src.hasRemaining())
            fchannel.write(src);
        fsync();
        return length;
    }

    @Override
    public void fsync()
    {
        try
        {
            fchannel.force(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void truncate(long newSize)
    {
        try
        {
            fchannel.truncate(newSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close()
    {
        try
        {
            fchannel.close();
            fchannel = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}