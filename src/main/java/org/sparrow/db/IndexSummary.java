package org.sparrow.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mauricio on 27/12/2015.
 */
public class IndexSummary
{
    private ConcurrentHashMap<Integer, Long> index_ = new ConcurrentHashMap<Integer, Long>();

    public IndexSummary()
    {
    }

    public boolean put(int key, long value)
    {
        if (index_.contains(key))
            return false;
        index_.put(key, value);
        return true;
    }

    public Long get(int key)
    {
        return index_.get(key);
    }

    public boolean hasKey(Integer key)
    {
        return index_.contains(key);
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
