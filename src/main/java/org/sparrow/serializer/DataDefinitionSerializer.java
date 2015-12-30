package org.sparrow.serializer;

import org.sparrow.db.DataDefinition;

import java.nio.ByteBuffer;

/**
 * Created by mauricio on 26/12/2015.
 */
public class DataDefinitionSerializer implements TypeSerializer<DataDefinition>
{
    public static final int DEFAULT_SIZE = 31;
    public static final DataDefinitionSerializer instance = new DataDefinitionSerializer();

    @Override
    public ByteBuffer serialize(DataDefinition object)
    {
        ByteBuffer buff = ByteBuffer.allocate(object.getSize() > 0 ? DEFAULT_SIZE + object.getSize() : DEFAULT_SIZE);
        buff.putInt(object.getKey32());
        buff.putLong(object.getKey64());
        buff.putInt(object.getSize());
        buff.putLong(object.getOffset());
        buff.putInt(object.getCrc32());
        buff.putShort(DataDefinition.Extension.getShort(object.getExtension()));
        buff.put(DataDefinition.DataState.getByte(object.getState()));
        if (object.getSize() > 0)
            buff.put(object.getBuffer());
        buff.flip();
        return buff;
    }

    @Override
    public DataDefinition deserialize(ByteBuffer in)
    {
        DataDefinition dataDefinition = new DataDefinition();
        dataDefinition.setKey32(in.getInt());
        dataDefinition.setKey64(in.getLong());
        dataDefinition.setSize(in.getInt());
        dataDefinition.setOffset(in.getLong());
        dataDefinition.setCrc32(in.getInt());
        dataDefinition.setExtension(DataDefinition.Extension.values()[in.getShort()]);
        dataDefinition.setState(DataDefinition.DataState.values()[in.get()]);
        return dataDefinition;
    }

    @Override
    public String toString(DataDefinition object)
    {
        return "DataDefinition{" +
                "key32=" + object.getKey32() +
                ", key64=" + object.getKey64() +
                ", size=" + object.getSize() +
                ", offset=" + object.getOffset() +
                ", crc32=" + object.getCrc32() +
                ", extension=" + object.getExtension() +
                ", state=" + object.getState() +
                '}';
    }
}
