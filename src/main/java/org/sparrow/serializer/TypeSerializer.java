package org.sparrow.serializer;

import java.nio.ByteBuffer;

/**
 * Created by mauricio on 26/12/2015.
 */
public interface TypeSerializer<T>
{
    ByteBuffer serialize (T object);
    T deserialize(ByteBuffer in);
    String toString(T object);
}