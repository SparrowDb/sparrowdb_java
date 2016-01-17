package org.sparrow.db;

/**
 * Created by mauricio on 25/12/2015.
 */
public class DataDefinition
{
    public enum Extension
    {
        PNG(0), JPG(1), JPGEG(2), TIFF(3), BMP(4);

        private short id;
        Extension(int id)
        {
            this.id = (short)id;
        }

        public static short getShort(Extension ext)
        {
            return Extension.values()[ext.ordinal()].id;
        }
    }

    public enum DataState
    {
        ACTIVE(0), REMOVED(1), DESTROY(2);

        private byte id;
        DataState(int id)
        {
            this.id = (byte)id;
        }

        public static byte getByte(DataState state)
        {
            return DataState.values()[state.id].id;
        }
    }

    public DataDefinition()
    {

    }

    private String key;
    private int key32;
    private int size;
    private long offset;
    private int crc32;
    private Extension extension;
    private DataState state;
    private byte[] buffer;

    public DataDefinition(String key, int key32, int size, long offset, int crc32, Extension extension, DataState state)
    {
        this.key = key;
        this.key32 = key32;
        this.size = size;
        this.offset = offset;
        this.crc32 = crc32;
        this.extension = extension;
        this.state = state;
    }

    public DataDefinition(String key, int key32, int size, long offset, int crc32, Extension extension, DataState state, byte[] buffer)
    {
        this.key = key;
        this.key32 = key32;
        this.size = size;
        this.offset = offset;
        this.crc32 = crc32;
        this.extension = extension;
        this.state = state;
        this.buffer = buffer;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public int getKey32()
    {
        return key32;
    }

    public void setKey32(int key32)
    {
        this.key32 = key32;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setOffset(long offset)
    {
        this.offset = offset;
    }

    public int getCrc32()
    {
        return crc32;
    }

    public void setCrc32(int crc32)
    {
        this.crc32 = crc32;
    }

    public Extension getExtension()
    {
        return extension;
    }

    public void setExtension(Extension extension)
    {
        this.extension = extension;
    }

    public DataState getState()
    {
        return state;
    }

    public void setState(DataState state)
    {
        this.state = state;
    }

    public byte[] getBuffer()
    {
        return buffer;
    }

    public void setBuffer(byte[] buffer)
    {
        this.buffer = buffer;
    }
}
