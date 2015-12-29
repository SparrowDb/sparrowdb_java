package org.sparrow.io.compression;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by mauricio on 26/12/2015.
 */
public interface ICompressor
{
    int compress (ByteBuffer input, ByteBuffer output) throws IOException;
    int uncompress (ByteBuffer input, ByteBuffer output) throws IOException;
    int uncompress(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) throws IOException;
}
