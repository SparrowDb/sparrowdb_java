package org.sparrow.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 27/12/2015.
 */
public interface IDataWriter
{
    long length();
    long currentPosition();
    long lastModified();
    void write(byte[] src) throws IOException;
    int write(ByteBuffer src) throws IOException;
    void fsync();
    void truncate(long newSize);
    void close();
}
