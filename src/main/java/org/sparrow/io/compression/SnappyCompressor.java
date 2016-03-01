package org.sparrow.io.compression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 26/12/2015.
 */
public class SnappyCompressor implements ICompressor
{
    private static final SnappyCompressor instance = new SnappyCompressor();

    public static SnappyCompressor create()
    {
        Snappy.getNativeLibraryVersion();
        return instance;
    }

    @Override
    public int compress(ByteBuffer input, ByteBuffer output) throws IOException
    {
        int result = Snappy.compress(input, output);
        output.position(output.limit());
        output.limit(output.capacity());
        return result;
    }

    @Override
    public int uncompress(ByteBuffer input, ByteBuffer output) throws IOException
    {
        return Snappy.uncompress(input, output);
    }

    @Override
    public int uncompress(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) throws IOException
    {
        return Snappy.rawUncompress(input, inputOffset, inputLength, output, outputOffset);
    }
}
