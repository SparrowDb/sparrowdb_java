package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by mauricio on 4/6/16.
 */
public class DataHolderProxy implements OperableDataFile
{
    private static Logger logger = LoggerFactory.getLogger(DataHolderProxy.class);
    private IDataWriter dataWriter;
    private IDataReader dataReader;
    private String filename;

    public DataHolderProxy()
    {
    }

    public DataHolderProxy(String filename)
    {
        this.filename = filename;
        open(this.filename);
    }

    @Override
    public void open(String filename) {
        this.filename = filename;
        dataWriter = StorageWriter.open(filename);
        dataReader = StorageReader.open(filename);
    }

    @Override
    public void append(DataDefinition dataDefinition) throws IOException {
        try
        {
            dataWriter = StorageWriter.open(filename);
            dataDefinition.setOffset(dataWriter.length());
            byte[] serializedData = DataDefinitionSerializer.instance.serialize(dataDefinition);
            byte[] compressed = Snappy.compress(serializedData);
            DataOutput.save(dataWriter, compressed);
        }
        finally
        {
            dataWriter.close();
        }
    }

    @Override
    public DataDefinition getData(long offset)
    {
        dataReader = StorageReader.open(filename);
        byte[] dataCompressedBytes = DataInput.load(dataReader, offset);
        DataDefinition dataDefinition = null;

        try
        {
            if (dataCompressedBytes != null)
            {
                byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
                dataDefinition = DataDefinitionSerializer.instance.deserialize(uncompressed, true);
            }
        }
        catch (IOException e)
        {
            logger.error("Could not get data from DataHolder {}: {} ", filename, e.getMessage());
        }
        finally
        {
            dataReader.close();
        }

        return dataDefinition;
    }

    @Override
    public void iterateDataHolder(DataDefinitionIterator dataDefinitionIterator)
    {
        dataReader = StorageReader.open(filename);
        long fileSize = dataReader.length();
        long currentSize = 0;

        try
        {
            while (currentSize < fileSize)
            {
                try
                {
                    byte[] dataCompressedBytes = DataInput.load(dataReader, currentSize);
                    byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
                    currentSize += (dataCompressedBytes.length + 4);
                    dataDefinitionIterator.get(DataDefinitionSerializer.instance.deserialize(uncompressed, true), currentSize);
                }
                catch (IOException e)
                {
                    logger.error("Could not get data from DataHolder {}: {} ", filename, e.getMessage());
                }
            }
        }
        finally
        {
            dataReader.close();
        }
    }

    @Override
    public void truncate()
    {
        dataWriter.truncate(0);
    }

    @Override
    public boolean isEmpty()
    {
        return getSize() <= 0;
    }

    @Override
    public long getSize()
    {
        return dataReader == null ? 0 : dataReader.length();
    }

    @Override
    public void close()
    {
        if (dataWriter != null)
        {
            dataWriter.close();
        }

        if (dataReader != null)
        {
            dataReader.close();
        }
    }
}
