package org.sparrow.common.serializer;

import java.io.IOException;

/**
 * Created by mauricio on 26/12/2015.
 */
public interface TypeSerializer<T>
{
    byte[] serialize (T object) throws IOException;
    T deserialize(byte[] in) throws IOException;
    String toString(T object);
}