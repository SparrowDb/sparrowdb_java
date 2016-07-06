package org.sparrow.cache;

import org.sparrow.common.DataDefinition;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mauricio on 7/4/16.
 */
public class LRUCache<K> implements ICache<K, DataDefinition>
{
    private final Map<K, Long> keyMap;
    private long currentSize = 0L;
    private final long maxSize;

    public LRUCache(final long maxSize)
    {
        keyMap = new LinkedHashMap<>();
        this.maxSize = maxSize;
    }

    @Override
    public void put(K key, DataDefinition dataDefinition)
    {
        try
        {
            int len = dataDefinition.getSize();
            Long address = keyMap.get(key);

            if ((currentSize + len) > maxSize)
            {
                Iterator<K> it = keyMap.keySet().iterator();
                it.next();
                it.remove();
            }

            if (address == null)
            {
                free(key);
                address = UnsafeEntry.instance.putEntry(dataDefinition);
                currentSize += len;
            } else
            {
                keyMap.remove(key);
            }
            keyMap.put(key, address);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public DataDefinition get(K key)
    {
        DataDefinition dataDefinition = null;
        try
        {
            Long address = keyMap.get(key);
            if (address != null)
            {
                dataDefinition = (DataDefinition) UnsafeEntry.instance.getEntry(address);
                put(key, dataDefinition);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dataDefinition;
    }

    public void free(K key)
    {
        if (keyMap.containsKey(key))
        {
            UnsafeEntry.instance.free(keyMap.get(key));
        }
    }

    @Override
    public void clear()
    {
        for (Map.Entry<K, Long> e : keyMap.entrySet())
        {
            UnsafeEntry.instance.free(e.getValue());
        }
    }

    @Override
    public long capacity()
    {
        return maxSize;
    }

    @Override
    public long size()
    {
        return currentSize;
    }
}