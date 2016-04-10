package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.DataDefinition;
import org.sparrow.config.DatabaseDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by mauricio on 07/01/2016.
 */
public final class DataLog extends DataFile
{
    private static Logger logger = LoggerFactory.getLogger(DataLog.class);
    private String dbname;
    private Set<DataHolder> dataHolders;
    private long currentSize;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private ExecutorService executor = Executors.newCachedThreadPool();

    public DataLog(String dbname, Set<DataHolder> dataHolders, String filename)
    {
        super();
        this.dbname = dbname;
        this.filename = filename;
        this.dataHolders = dataHolders;
        dataHolderProxy = new DataHolderProxy(filename);
    }

    private void append(DataDefinition dataDefinition)
    {
        try {
            dataHolderProxy.append(dataDefinition);
            currentSize += dataDefinition.getSize();
            indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
        } catch (IOException e) {
            logger.error("Could not append data to DataLog {}: {} ", filename, e.getMessage());
        }
    }

    public void add(DataDefinition dataDefinition)
    {
        if ((dataDefinition.getSize() + currentSize) >= DatabaseDescriptor.config.max_datalog_size)
        {
            flush();
        }

        lock.writeLock().lock();
        try
        {
            append(dataDefinition);
        }
        finally
        {
            lock.writeLock().unlock();
        }
    }

    public void flush()
    {
        String nextFileName = DataFileManager.getDbPath(dbname, DataFileManager.getNextDataHolderName(dbname));
        logger.debug("Flushing data into {}", nextFileName);

        if (new File(filename).renameTo(new File(nextFileName)))
        {
            IndexSummary temp = new IndexSummary();
            temp.getIndexList().putAll(this.indexer.getIndexList());

            executor.execute(() -> {
                DataHolder dataHolder = DataHolder.create(nextFileName, temp);
                dataHolders.add(dataHolder);
            });

            this.indexer.clear();

            currentSize = 0;
            dataHolderProxy.open(filename);
        }

        logger.debug("-------------- End flushing");
    }

    public void load()
    {
        dataHolderProxy.iterateDataHolder((dataDefinition, bytesRead) -> {
            indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
        });
    }
}