package org.sparrow.db;


import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mauricio on 27/12/2015.
 */
public class IndexSummary
{
    private LinkedHashMap<Integer, Long> index_ = Maps.newLinkedHashMap();

    public IndexSummary()
    {
    }

    public synchronized void put(int key, long value)
    {
        index_.put(key, value);
    }

    public Long get(int key)
    {
        return index_.get(key);
    }

    public boolean hasKey(Integer key)
    {
        return index_.containsKey(key);
    }

    public Map<Integer, Long> getIndexList()
    {
        return index_;
    }

    public void clear()
    {
        index_.clear();
    }

    public void delete(int key)
    {
        index_.remove(key);
    }

    public int size()
    {
        return index_.size();
    }
}
