package org.sparrow.serializer;

import org.sparrow.db.Index;

import java.nio.ByteBuffer;

/**
 * Created by mauricio on 27/12/2015.
 */
public class IndexSerializer implements TypeSerializer<Index>
{
    public static final int DEFAULT_SIZE = 20;
    public static final IndexSerializer instance = new IndexSerializer();

    @Override
    public ByteBuffer serialize(Index object)
    {
        ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_SIZE);
        buffer.putInt(object.getKey());
        buffer.putLong(object.getOffset());
        buffer.putLong(object.getTimestamp());
        buffer.flip();
        return buffer;
    }

    @Override
    public Index deserialize(ByteBuffer in)
    {
        Index index = new Index();
        index.setKey(in.getInt());
        index.setOffset(in.getLong());
        index.setTimestamp(in.getLong());
        return index;
    }

    @Override
    public String toString(Index object)
    {
        return "Index{" +
                "key=" + object.getKey() +
                ", offset=" + object.getOffset() +
                ", timestamp=" + object.getTimestamp() +
                '}';
    }
}
