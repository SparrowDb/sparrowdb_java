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
    public byte[] serialize(Map.Entry<Integer, Long> index)
    {
        return serialize(index.getKey(), index.getValue());
    }

    public byte[] serialize(int key, long offset)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bos = new DataOutputStream(baos);
        try
        {
            bos.writeInt(key);
            bos.writeLong(offset);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public Map.Entry<Integer, Long> deserialize(byte[] in)
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        DataInputStream dis = new DataInputStream(bais);
        try
        {
            int key = dis.readInt();
            long offset = dis.readLong();
            return new AbstractMap.SimpleEntry<>(key, offset);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString(Map.Entry<Integer, Long> object)
    {
        return "DataDefinition{" +
                "key=" + object.getKey() +
                ", offset=" + object.getValue() +
                '}';
    }
}
