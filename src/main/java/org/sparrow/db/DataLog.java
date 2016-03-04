package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.util.SPUtils;
import org.xerial.snappy.Snappy;

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
        dataWriter = StorageWriter.open(filename);
        dataReader = StorageReader.open(filename);
    }

    private void append(DataDefinition dataDefinition)
    {
        dataDefinition.setOffset(dataWriter.length());
        logger.debug("Writing data: {}", DataDefinitionSerializer.instance.toString(dataDefinition));
        try
        {
            byte[] serializedData = DataDefinitionSerializer.instance.serialize(dataDefinition);
            byte[] compressed = Snappy.compress(serializedData);
            DataOutput.save(dataWriter, compressed);
            currentSize += dataDefinition.getSize();
            indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
        }
        catch (IOException e)
        {
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

    @Override
    protected void deleteData(DataDefinition dataDefinition)
    {

    }

    private void flush()
    {
        String nextFileName = SPUtils.getDbPath(dbname, DataHolder.DataHolderFileManager.getNextFilename(dbname));
        logger.debug("Flushing data into {}", nextFileName);
        close();

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
            dataWriter = StorageWriter.open(filename);
            dataReader = StorageReader.open(filename);
        }

        logger.debug("-------------- End flushing");
    }

    public void load()
    {
        long fileSize = dataReader.length();

        while (currentSize < fileSize)
        {
            byte[] dataCompressedBytes = DataInput.load(dataReader, currentSize);
            byte[] uncompressed = null;
            DataDefinition dataDefinition = null;

            try
            {
                uncompressed = Snappy.uncompress(dataCompressedBytes);
                dataDefinition = DataDefinitionSerializer.instance.deserialize(uncompressed, true);
            }
            catch (IOException e)
            {
                logger.warn("{} Dataholder {} is corrupted size {}, truncating file...", dbname, filename, fileSize);
                dataWriter.truncate(0);
                break;
            }

            indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
            currentSize += (dataCompressedBytes.length + 4);
        }
    }

}