package org.sparrow.cache;

/**
 * Created by mauricio on 24/12/2015.
 */
public interface ICache<K, V>
{
    long capacity();
    void setCapacity(long capacity);
    boolean isEmpty();
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    int size();
    void clear();
    boolean containsKey(K key);
}
