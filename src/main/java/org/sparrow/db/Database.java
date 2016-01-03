package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.IDataReader;
import org.sparrow.io.IDataWriter;
import org.sparrow.io.StorageReader;
import org.sparrow.io.StorageWriter;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.thrift.DataObject;
import org.sparrow.thrift.SpqlResult;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mauricio on 25/12/2015.
 */
public class Database
{
    private static Logger logger = LoggerFactory.getLogger(Database.class);
    private static final String FILENAME_EXTENSION = ".spw";
    private IDataWriter storageWriter;
    private IDataReader storageReader;
    private IndexSummary indexSummary;
    private String dbname;
    private Lock lock_ = new ReentrantLock();

    public Database(String dbname)
    {
        this.dbname = dbname;
        storageWriter = StorageWriter.open(SPUtils.getDbPath(dbname, "Data", FILENAME_EXTENSION));
        storageReader = StorageReader.open(SPUtils.getDbPath(dbname, "Data", FILENAME_EXTENSION));
        indexSummary = new IndexSummary(SPUtils.getDbPath(dbname, "Index", FILENAME_EXTENSION));
        indexSummary.loadIndexFromDisk();
    }

    public static Database build(String dbname)
    {
        try
        {
            FileUtils.createDirectory(DatabaseDescriptor.getDataFilePath() + dbname);
            return new Database(dbname);
        } catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public void close()
    {
        storageWriter.close();
        storageReader.close();
        indexSummary.close();
    }

    public String insert_data(DataObject object)
    {
        lock_.lock();
        try
        {
            int hash32key = SPUtils.hash32(object.getKey());
            DataDefinition dataDefinition = new DataDefinition();
            dataDefinition.setKey32(hash32key);
            dataDefinition.setKey64(SPUtils.hash64(object.getKey()));
            dataDefinition.setOffset(storageWriter.currentPosition());
            dataDefinition.setSize(object.bufferForData().capacity());
            dataDefinition.setCrc32(0);
            dataDefinition.setExtension(DataDefinition.Extension.PNG);
            dataDefinition.setState(DataDefinition.DataState.ACTIVE);
            dataDefinition.setBuffer(object.bufferForData().array());

            ByteBuffer buffer = DataDefinitionSerializer.instance.serialize(dataDefinition);
            storageWriter.write(buffer);
            long unixtime = System.currentTimeMillis() / 1000L;
            indexSummary.addIndex(new Index(hash32key, dataDefinition.getOffset(), unixtime));

            return String.valueOf(hash32key);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            lock_.unlock();
        }
        return "Could not insert image";
    }

    public DataDefinition getRawDataByKey32(int key32)
    {
        ByteBuffer dataDefinitionBuffer = ByteBuffer.allocate(DataDefinitionSerializer.DEFAULT_SIZE);
        Index index = indexSummary.get(key32);
        DataDefinition dataDefinition = null;

        if (index != null)
        {
            try
            {
                storageReader.readChunck(index.getOffset(), dataDefinitionBuffer);
                dataDefinitionBuffer.flip();
                dataDefinition = DataDefinitionSerializer.instance.deserialize(dataDefinitionBuffer);
                dataDefinitionBuffer.clear();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return dataDefinition;
    }

    public DataDefinition getDataWithImageByKey32(int key32)
    {
        DataDefinition dataDefinition = getRawDataByKey32(key32);

        if (dataDefinition != null)
        {
            try
            {
                if (dataDefinition.getSize() > 0)
                {
                    ByteBuffer dataBuff = ByteBuffer.allocate(dataDefinition.getSize());
                    storageReader.readChunck(dataDefinition.getOffset() + DataDefinitionSerializer.DEFAULT_SIZE, dataBuff);
                    dataDefinition.setBuffer(dataBuff.array());
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return dataDefinition;
    }

    public SpqlResult mapToSpqlResult(Map data)
    {
        SpqlResult result = new SpqlResult();
        for(Object entry : data.entrySet())
        {
            Map.Entry<Integer, Index> idx = (Map.Entry<Integer, Index>) entry;
            DataDefinition dataDefinition = getRawDataByKey32(idx.getValue().getKey());
            DataObject dataObject = new DataObject();
            dataObject.setDbname(dbname);
            dataObject.setSize(dataDefinition.getSize());
            dataObject.setTimestamp(idx.getValue().getTimestamp());
            dataObject.setKey(String.valueOf(dataDefinition.getKey32()));
            result.addToRows(dataObject);
        }
        result.count = data.size();
        return  result;
    }

    public SpqlResult query_data_where_timestamp(long value)
    {
        Map data = indexSummary.filterByTimestamp(value);
        return mapToSpqlResult(data);
    }

    public SpqlResult query_data_where_key(int value)
    {
        Map data = indexSummary.filterByKey(value);
        return mapToSpqlResult(data);
    }

    public SpqlResult query_data_all()
    {
        ConcurrentHashMap<Integer, Index> index = indexSummary.getAll();
        return mapToSpqlResult(index);
    }
}