package org.sparrow.util;

import java.nio.ByteBuffer;

/**
 * Created by mauricio on 30/12/2015.
 */
public class SPUtils
{
    public static int hash32(String data)
    {
        return MurmurHash.hash32(ByteBuffer.wrap(data.getBytes()), 0, data.length(), 0);
    }

    public static long hash64(String data)
    {
        return MurmurHash.hash2_64(ByteBuffer.wrap(data.getBytes()), 0, data.length(), 0);
    }
}