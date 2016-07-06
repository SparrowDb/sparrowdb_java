package org.sparrow.cache;

import org.sparrow.common.DataDefinition;
import org.sparrow.metrics.CacheMetrics;
import org.sparrow.metrics.SparrowMetrics;

/**
 * Created by mauricio on 4/18/16.
 */
public class CacheManager<K>
{
    private final LRUCache<K> cache;
    private CacheMetrics metrics;

    public CacheManager(String name, Long maxSize)
    {
        this.cache = new LRUCache<>(maxSize);
        this.metrics = new CacheMetrics(SparrowMetrics.instance.getMetrics(), name, cache);
    }

    public void put(K key, DataDefinition value)
    {
        cache.put(key, value);
    }

    public DataDefinition get(K key)
    {
        DataDefinition v = cache.get(key);
        metrics.requests.mark();
        if (v != null)
            metrics.hits.mark();
        return v;
    }

    public void clear()
    {
        cache.clear();
    }

    public CacheMetrics getMetrics()
    {
        return metrics;
    }
}
