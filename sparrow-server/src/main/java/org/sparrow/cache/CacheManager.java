package org.sparrow.cache;

import org.sparrow.metrics.CacheMetrics;
import org.sparrow.metrics.SparrowMetrics;

/**
 * Created by mauricio on 4/18/16.
 */
public class CacheManager<K, V>
{
    private final ICache<K, V> cache;
    private CacheMetrics metrics;

    public CacheManager()
    {
        this.cache = new CacheProvider<>(80);
        this.metrics = new CacheMetrics(SparrowMetrics.instance.getMetrics(), cache);
    }

    public void put(K key, V value)
    {
        cache.put(key, value);
    }

    public V get(K key)
    {
        V v = cache.get(key);
        metrics.requests.mark();
        if (v != null)
            metrics.hits.mark();
        return v;
    }

    public void clear()
    {
        cache.clear();
        metrics = new CacheMetrics(SparrowMetrics.instance.getMetrics(), cache);
    }


    public CacheMetrics getMetrics()
    {
        return metrics;
    }
}
