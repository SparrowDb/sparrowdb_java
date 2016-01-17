package org.sparrow.serializer;

/**
 * Created by mauricio on 26/12/2015.
 */
public interface TypeSerializer<T>
{
    byte[] serialize (T object);
    T deserialize(byte[] in);
    String toString(T object);
}