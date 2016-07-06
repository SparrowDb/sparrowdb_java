package org.sparrow.cache;


/**
 * Created by mauricio on 24/12/2015.
 */
public interface ICache<K, V>
{
    void put(K key, V value);
    V get(K key);
    void clear();
    long capacity();
    long size();
}
