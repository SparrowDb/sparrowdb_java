package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.util.SPUtils;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by mauricio on 07/01/2016.
 */
public class DataLog
{
    private static Logger logger = LoggerFactory.getLogger(DataLog.class);
    private IndexSummary indexer = new IndexSummary();
    private String filename;
    private String dbname;
    private Set<DataHolder> dataHolders;
    private IDataWriter dataWriter;
    private IDataReader dataReader;
    private long currentSize = 0;
    private volatile LinkedBlockingQueue<DataDefinition> queue = new LinkedBlockingQueue<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public DataLog(String dbname, Set<DataHolder> dataHolders, String filename)
    {
        this.dbname = dbname;
        this.filename = filename;
        this.dataHolders = dataHolders;
        dataWriter = StorageWriter.open(filename);
        dataReader = StorageReader.open(filename);
        startConsumer();
    }

    public synchronized void add(DataDefinition dataDefinition)
    {
        queue.add(dataDefinition);
    }

    private void append(DataDefinition dataDefinition)
    {
        dataDefinition.setOffset(dataWriter.length());
        logger.debug("Writing data: {}", DataDefinitionSerializer.instance.toString(dataDefinition));
        byte[] serializedData = DataDefinitionSerializer.instance.serialize(dataDefinition);
        DataOutput.save(dataWriter, serializedData);
        currentSize += dataDefinition.getSize();
        indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
    }

    private void startConsumer()
    {
        Runnable task = () -> {
            for(;;)
            {
                DataDefinition dataDefinition = null;
                lock.writeLock().lock();

                try
                {
                    dataDefinition = queue.take();
                    if ((dataDefinition.getSize() + currentSize) >= DatabaseDescriptor.config.max_datalog_size)
                    {
                        flush();
                    }
                    append(dataDefinition);
                    dataDefinition = null;
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    lock.writeLock().unlock();
                }
            }
        };
        new Thread(task).start();
    }

    private void flush()
    {
        DataHolder dataHolder = null;
        long fileSize = dataReader.length();
        long readOffset = 0;
        String nextFileName = SPUtils.getDbPath(dbname, DataHolder.getNextFilename(dbname));

        logger.debug("Flushing data into {}", nextFileName);

        dataHolder = DataHolder.create(nextFileName);
        dataHolder.beforeAppend();

        while (readOffset < fileSize)
        {
            byte[] bytes = DataInput.load(dataReader, readOffset);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(bytes, true);
            dataHolder.append(dataDefinition);
            indexer.delete(dataDefinition.getKey32());
            readOffset += (bytes.length + 4);
        }

        dataHolder.afterAppend();

        logger.debug("-------------- End flushing");

        dataWriter.truncate(0);
        indexer.clear();
        currentSize = 0;
        dataHolders.add(dataHolder);
    }

    public void load()
    {
        long fileSize = dataReader.length();

        while (currentSize < fileSize)
        {
            byte[] bytes = DataInput.load(dataReader, currentSize);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(bytes, true);
            indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
            currentSize += (bytes.length + 4);
        }
    }

    public DataDefinition get(String key)
    {
        return get(SPUtils.hash32(key));
    }

    public DataDefinition get(int key32)
    {
        Long offset = indexer.get(key32);

        if (offset != null)
        {
            byte[] bytes = DataInput.load(dataReader, offset);
            return DataDefinitionSerializer.instance.deserialize(bytes, true);
        }
        return null;
    }

    public boolean isEmpty()
    {
        return getSize() <= 0;
    }

    public long getSize()
    {
        return dataReader.length();
    }

    public void clear()
    {
        dataWriter.truncate(0);
    }

    public void close()
    {
        if (dataWriter != null) dataWriter.close();
        if (dataReader != null) dataReader.close();
    }
}
