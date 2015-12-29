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
import org.sparrow.util.MurmurHash;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public Database(String dbname)
    {
        this.dbname = dbname;
        String path = DatabaseDescriptor.getDataFilePath() + dbname;
        storageWriter = StorageWriter.open(new java.io.File(path +  File.separator + "Data" + FILENAME_EXTENSION));
        storageReader = StorageReader.open(new java.io.File(path +  File.separator + "Data" + FILENAME_EXTENSION));
        indexSummary = new IndexSummary(new java.io.File(path +  File.separator + "Index" + FILENAME_EXTENSION));
        loadDataFromDisk();
    }

    public void loadDataFromDisk()
    {
        indexSummary.loadDataFromDisk();
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

    public String insert_data(DataObject object)
    {
        int hash32key = MurmurHash.hash32(ByteBuffer.wrap(object.getKey().getBytes()), 0, object.getKey().length(), 0);

        DataDefinition dataDefinition = new DataDefinition();
        dataDefinition.setKey32(hash32key);
        dataDefinition.setKey64(MurmurHash.hash2_64(ByteBuffer.wrap(object.getKey().getBytes()), 0, object.getKey().length(), 0));
        dataDefinition.setOffset(storageWriter.currentPosition());
        dataDefinition.setSize(object.bufferForData().capacity());
        dataDefinition.setCrc32(0);
        dataDefinition.setExtension(DataDefinition.Extension.PNG);
        dataDefinition.setState(DataDefinition.DataState.ACTIVE);
        dataDefinition.setBuffer(object.bufferForData().array());

        ByteBuffer buffer = DataDefinitionSerializer.instance.serialize(dataDefinition);
        try
        {
            storageWriter.write(buffer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        long unixtime = System.currentTimeMillis() / 1000L;
        indexSummary.addIndex(new Index(hash32key, dataDefinition.getOffset(), unixtime));

        return String.valueOf(hash32key);
    }

    public DataDefinition getDataByKey32(int key32)
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
        DataDefinition dataDefinition = getDataByKey32(key32);

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

    public SpqlResult query_data_where_timestamp(long value)
    {
        SpqlResult result = new SpqlResult();
        Map data = indexSummary.filterByTimestamp(value);

        for(Object entry : data.entrySet())
        {
            Map.Entry<Integer, Index> el = (Map.Entry<Integer, Index>)entry;
            DataObject dobj = new DataObject();
            DataDefinition dataDefinition = getDataByKey32(el.getValue().getKey());
            dobj.setDbname(dbname);
            dobj.setSize(dataDefinition.getSize());
            dobj.setTimestamp(el.getValue().getTimestamp());
            dobj.setKey(String.valueOf(el.getKey()));
            result.addToRows(dobj);
        }
        result.count = data.size();
        return result;
    }

    public SpqlResult query_data_where_key(int value)
    {
        SpqlResult result = new SpqlResult();
        Map data = indexSummary.filterByKey(value);

        for(Object entry : data.entrySet())
        {
            Map.Entry<Integer, Index> el = (Map.Entry<Integer, Index>)entry;
            DataObject dobj = new DataObject();
            DataDefinition dataDefinition = getDataByKey32(el.getValue().getKey());
            dobj.setDbname(dbname);
            dobj.setSize(dataDefinition.getSize());
            dobj.setTimestamp(el.getValue().getTimestamp());
            dobj.setKey(String.valueOf(el.getKey()));
            result.addToRows(dobj);
        }
        result.count = data.size();
        return result;
    }

    public SpqlResult query_data_all()
    {
        SpqlResult result = new SpqlResult();
        ConcurrentHashMap<Integer, Index> index = indexSummary.getAll();

        for(Map.Entry<Integer, Index> idx : index.entrySet())
        {
            DataObject dobj = new DataObject();
            DataDefinition dataDefinition = getDataByKey32(idx.getValue().getKey());
            dobj.setDbname(dbname);
            dobj.setSize(dataDefinition.getSize());
            dobj.setTimestamp(idx.getValue().getTimestamp());
            dobj.setKey(String.valueOf(idx.getKey()));
            result.addToRows(dobj);
        }
        result.count = index.size();
        return result;
    }
}