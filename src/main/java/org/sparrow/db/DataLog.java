package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.util.Set;


/**
 * Created by mauricio on 07/01/2016.
 */
public class DataLog
{
    private static Logger logger = LoggerFactory.getLogger(DataLog.class);
    private IndexSummary indexer = new IndexSummary();
    private String filename;
    private String dbname;
    private IDataWriter dataWriter;
    private IDataReader dataReader;
    private long currentSize = 0;
    private boolean isFlushingData = false;


    public DataLog(String dbname, String filename)
    {
        this.dbname = dbname;
        this.filename = filename;
        dataWriter = StorageWriter.open(filename);
        dataReader = StorageReader.open(filename);
    }

    public boolean needFlush(long size)
    {
        return (size + currentSize) >= DatabaseDescriptor.config.max_datalog_size;
    }

    public void append(DataDefinition dataDefinition)
    {
        dataDefinition.setOffset(dataWriter.length());
        logger.debug("Writing data: {}", DataDefinitionSerializer.instance.toString(dataDefinition));
        byte[] serializedData = DataDefinitionSerializer.instance.serialize(dataDefinition);
        DataOutput.save(dataWriter, serializedData);
        currentSize += dataDefinition.getSize();
        indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
    }

    public void flush(Set<DataHolder> dataHolder)
    {
        isFlushingData = true;

        String lastFileName = DataHolder.getLastFilename(dbname);
        long lastFileSize = FileUtils.getFileSize(SPUtils.getDbPath(dbname, lastFileName));

        DataHolder temp = new DataHolder(SPUtils.getDbPath(dbname, lastFileName));
        temp.beforeAppend();


        logger.debug("-------------- Start flushing: {}",lastFileName );

        long fileSize = dataReader.length();
        long readOffset = 0;

        while (readOffset < fileSize)
        {
            byte[] bytes = DataInput.load(dataReader, readOffset);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(bytes, true);

            if ((lastFileSize + dataDefinition.getSize()) >= DatabaseDescriptor.config.max_dataholder_size)
            {
                temp.afterAppend();
                lastFileName = SPUtils.getDbPath(dbname, DataHolder.getNextFilename(dbname));
                temp = new DataHolder(lastFileName);
                dataHolder.add(temp);
                temp.beforeAppend();
            }

            temp.append(dataDefinition);
            indexer.delete(dataDefinition.getKey32());
            readOffset += (bytes.length + 4);
        }

        logger.debug("-------------- End flushing");

        dataWriter.truncate(0);
        indexer.clear();
        isFlushingData = false;
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

        if (offset!=null)
        {
            byte[] bytes = DataInput.load(dataReader, offset);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(bytes, true);
            return dataDefinition;
        }
        return null;
    }

    public boolean isEmpty()
    {
        return getSize() > 0 ? false : true;
    }

    public long getSize()
    {
        return dataReader.length();
    }

    public void clear()
    {
        dataWriter.truncate(0);
    }
}
