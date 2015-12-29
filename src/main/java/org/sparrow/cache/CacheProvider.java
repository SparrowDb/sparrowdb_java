package org.sparrow.cache;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mauricio on 24/12/2015.
 */
public class CacheProvider<K, V> implements ICache<K, V>
{
    private static Logger logger = LoggerFactory.getLogger(CacheProvider.class);

    private static final int DEFAULT_CONCURRENCY_LEVEL = 64;
    private final ConcurrentLinkedHashMap<K, V> map;

    public CacheProvider(long capacity)
    {
        this.map = new ConcurrentLinkedHashMap.Builder<K, V>().concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL).maximumWeightedCapacity(capacity).build();
    }

    @Override
    public long capacity()
    {
        return map.capacity();
    }

    @Override
    public void setCapacity(long capacity)
    {
        map.setCapacity(capacity);
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public void put(K key, V value)
    {
        map.put(key, value);
    }

    @Override
    public V get(K key)
    {
        return map.get(key);
    }

    @Override
    public void remove(K key)
    {
        map.remove(key);
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public boolean containsKey(K key)
    {
        return map.containsKey(key);
    }
}
