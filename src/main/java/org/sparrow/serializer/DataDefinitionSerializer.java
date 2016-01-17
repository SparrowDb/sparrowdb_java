package org.sparrow.serializer;

import org.sparrow.db.DataDefinition;

import java.io.*;

/**
 * Created by mauricio on 26/12/2015.
 */
public class DataDefinitionSerializer implements TypeSerializer<DataDefinition>
{
    public static final DataDefinitionSerializer instance = new DataDefinitionSerializer();

    @Override
    public byte[] serialize(DataDefinition dataDefinition)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream bos = new DataOutputStream(baos);
        try
        {
            bos.writeUTF(dataDefinition.getKey());
            bos.writeInt(dataDefinition.getKey32());
            bos.writeInt(dataDefinition.getSize());
            bos.writeLong(dataDefinition.getOffset());
            bos.writeInt(dataDefinition.getCrc32());
            bos.writeShort(DataDefinition.Extension.getShort(dataDefinition.getExtension()));
            bos.writeByte(DataDefinition.DataState.getByte(dataDefinition.getState()));
            if (dataDefinition.getSize() > 0) {
                bos.write(dataDefinition.getBuffer());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public DataDefinition deserialize(byte[] in)
    {
        return deserialize(in, false);
    }

    public DataDefinition deserialize(byte[] in, boolean withData)
    {
        DataDefinition dataDefinition = new DataDefinition();
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        DataInputStream dis = new DataInputStream(bais);
        try
        {
            dataDefinition.setKey(dis.readUTF().toString());
            dataDefinition.setKey32(dis.readInt());
            dataDefinition.setSize(dis.readInt());
            dataDefinition.setOffset(dis.readLong());
            dataDefinition.setCrc32(dis.readInt());
            dataDefinition.setExtension(DataDefinition.Extension.values()[dis.readShort()]);
            dataDefinition.setState(DataDefinition.DataState.values()[dis.readByte()]);
            if (withData) {
                byte[] data = new byte[dataDefinition.getSize()];
                dis.read(data);
                dataDefinition.setBuffer(data);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
                ", crc32=" + object.getCrc32() +
                ", extension=" + object.getExtension() +
                ", state=" + object.getState() +
                '}';
    }
}
