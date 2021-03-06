package org.sparrow.common.serializer;


import org.sparrow.common.DataDefinition;

import java.io.*;

/**
 * Created by mauricio on 26/12/2015.
 */
public class DataDefinitionSerializer implements TypeSerializer<DataDefinition>
{
    public static final DataDefinitionSerializer instance = new DataDefinitionSerializer();

    @Override
    public byte[] serialize(DataDefinition dataDefinition) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bos = new DataOutputStream(baos);

        bos.writeUTF(dataDefinition.getKey());
        bos.writeInt(dataDefinition.getKey32());
        bos.writeInt(dataDefinition.getSize());
        bos.writeLong(dataDefinition.getOffset());
        bos.writeLong(dataDefinition.getTimestamp());
        bos.writeUTF(dataDefinition.getExtension());
        bos.writeByte(DataDefinition.DataState.getByte(dataDefinition.getState()));
        if (dataDefinition.getSize() > 0)
        {
            bos.write(dataDefinition.getBuffer());
        }
        bos.close();
        return baos.toByteArray();
    }

    @Override
    public DataDefinition deserialize(byte[] in) throws IOException
    {
        return deserialize(in, false);
    }
    public DataDefinition deserialize(byte[] in, boolean withData) throws IOException
    {
        DataDefinition dataDefinition = new DataDefinition();
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        DataInputStream dis = new DataInputStream(bais);

        dataDefinition.setKey(dis.readUTF());
        dataDefinition.setKey32(dis.readInt());
        dataDefinition.setSize(dis.readInt());
        dataDefinition.setOffset(dis.readLong());
        dataDefinition.setTimestamp(dis.readLong());
        dataDefinition.setExtension(dis.readUTF());
        dataDefinition.setState(DataDefinition.DataState.values()[dis.readByte()]);
        if (withData)
        {
            byte[] data = new byte[dataDefinition.getSize()];
            dis.read(data);
            dataDefinition.setBuffer(data);
        }

        dis.close();

        return dataDefinition;
    }

    @Override
    public String toString(DataDefinition object)
    {
        return "DataDefinition{" +
                "key=" + object.getKey() +
                ", key32=" + object.getKey32() +
                ", size=" + object.getSize() +
                ", offset=" + object.getOffset() +
                ", timestamp=" + object.getTimestamp() +
                ", extension=" + object.getExtension() +
                ", state=" + object.getState() +
                '}';
    }
}
