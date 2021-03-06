package org.sparrow.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 27/12/2015.
 */
public interface IDataReader
{
    long length();
    long currentPosition();
    int readChunck(long offset, ByteBuffer dst) throws IOException;
    int read(ByteBuffer src);
    void close();
}
