package org.sparrow.db;

/**
 * Created by mauricio on 27/12/2015.
 */
public class Index
{
    private int key;
    private long offset;
    private long timestamp;

    public Index()
    {
    }

    public Index(int key, long offset, long timestamp)
    {
        this.key = key;
        this.offset = offset;
        this.timestamp = timestamp;
    }

    public int getKey()
    {
        return key;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setOffset(long offset)
    {
        this.offset = offset;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
