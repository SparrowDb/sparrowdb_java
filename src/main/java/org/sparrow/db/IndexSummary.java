package org.sparrow.db;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.sparrow.io.IDataReader;
import org.sparrow.io.IDataWriter;
import org.sparrow.io.StorageReader;
import org.sparrow.io.StorageWriter;
import org.sparrow.serializer.IndexSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mauricio on 25/12/2015.
 */
public class IndexSummary
{
    private ConcurrentHashMap<Integer, Index> indexMap;
    private final File indexFile;
    private IDataWriter indexWriter;
    private IDataReader indexReader;

    public IndexSummary(File indexFile)
    {
        indexMap = new ConcurrentHashMap<>();
        this.indexFile = indexFile;
        this.indexWriter = StorageWriter.open(this.indexFile);
        this.indexReader = StorageReader.open(this.indexFile);
    }

    public void loadIndexFromDisk()
    {
        ByteBuffer reader = ByteBuffer.allocate(IndexSerializer.DEFAULT_SIZE);
        while(indexReader.read(reader)>0)
        {
            reader.flip();
            Index index = IndexSerializer.instance.deserialize(reader);
            indexMap.put(index.getKey(), index);
            reader.clear();
        }
    }

    public boolean exists(int key)
    {
        return indexMap.contains(key);
    }

    public boolean addIndex(Index index)
    {
        if (exists(index.getKey()))
            return false;

        indexMap.put(index.getKey(), index);
        ByteBuffer buffer = IndexSerializer.instance.serialize(index);
        try
        {
            indexWriter.write(buffer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public Index get(int hash32)
    {
        return indexMap.get(hash32);
    }

    public boolean remove(Integer key)
    {
        if (exists(key))
        {
            indexMap.remove(key);
            return true;
        }
        return false;
    }

    public ConcurrentHashMap<Integer, Index> getAll()
    {
        return indexMap;
    }

    public Map filterByTimestamp(long value)
    {
        Predicate<Map.Entry<Integer, Index>> wherePredicate = element -> element.getValue().getTimestamp() == value;
        return Maps.filterEntries(indexMap, wherePredicate);
    }

    public Map filterByKey(int value)
    {
        Predicate<Map.Entry<Integer, Index>> wherePredicate = element -> element.getValue().getKey() == value;
        return Maps.filterEntries(indexMap, wherePredicate);
    }
}
