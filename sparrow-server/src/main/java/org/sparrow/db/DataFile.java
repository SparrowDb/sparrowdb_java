package org.sparrow.db;

import org.sparrow.common.DataDefinition;
import org.sparrow.common.util.SPUtils;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 03/03/16.
 */
abstract class DataFile
{
    IndexSummary indexer = new IndexSummary();
    OperableDataFile dataHolderProxy;
    String filename;
    String indexFile;

    public DataDefinition get(String key)
    {
        return get(SPUtils.hash32(key));
    }

    public DataDefinition get(int key32)
    {
        Long offset = indexer.get(key32);
        return offset == null ? null : dataHolderProxy.getData(offset);
    }

    public LinkedHashSet<DataDefinition> fetchAll()
    {
        return indexer.getIndexList().entrySet().stream().map(idx -> get(idx.getKey())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String getFilename()
    {
        return filename;
    }

    public void clear()
    {
        dataHolderProxy.truncate();
    }

    public boolean isEmpty()
    {
        return dataHolderProxy.isEmpty();
    }

    public long getSize()
    {
        return dataHolderProxy.getSize();
    }

    public void close()
    {
        dataHolderProxy.close();
    }

    public int count()
    {
        return indexer.size();
    }

    public String getIndexFileName()
    {
        return indexFile;
    }

    public IndexSummary getIndexer()
    {
        return indexer;
    }
}
