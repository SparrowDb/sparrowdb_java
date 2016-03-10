package org.sparrow.serializer;

import java.io.*;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Created by mauricio on 09/01/2016.
 */
public class IndexSeralizer implements TypeSerializer<Map.Entry<Integer, Long>>
{
    public static final IndexSeralizer instance = new IndexSeralizer();

    @Override
    public byte[] serialize(Map.Entry<Integer, Long> index) throws IOException
    {
        return serialize(index.getKey(), index.getValue());
    }

    public byte[] serialize(int key, long offset) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bos = new DataOutputStream(baos);
        bos.writeInt(key);
        bos.writeLong(offset);
        bos.close();
        return baos.toByteArray();
    }

    @Override
    public Map.Entry<Integer, Long> deserialize(byte[] in) throws IOException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        DataInputStream dis = new DataInputStream(bais);
        int key = dis.readInt();
        long offset = dis.readLong();
        dis.close();
        return new AbstractMap.SimpleEntry<>(key, offset);

    }

    @Override
    public String toString(Map.Entry<Integer, Long> object)
    {
        return "Index{" +
                "key=" + object.getKey() +
                ", offset=" + object.getValue() +
                '}';
    }
}
