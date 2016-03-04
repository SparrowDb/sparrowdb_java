package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.io.DataInput;
import org.sparrow.io.IDataReader;
import org.sparrow.io.IDataWriter;
import org.sparrow.io.StorageReader;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.util.SPUtils;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 03/03/16.
 */
abstract class DataFile
{
    private static Logger logger = LoggerFactory.getLogger(DataFile.class);

    protected String filename;
    protected IDataWriter dataWriter;
    protected IDataReader dataReader;
    protected IndexSummary indexer = new IndexSummary();

    protected abstract void deleteData(DataDefinition dataDefinition);

    public DataDefinition get(String key)
    {
        return get(SPUtils.hash32(key));
    }

    public DataDefinition get(int key32)
    {
        DataDefinition dataDefinition = null;
        Long offset = indexer.get(key32);

        if (offset == null)
        {
            return null;
        }

        IDataReader dataReader = StorageReader.open(this.filename);
        byte[] dataCompressedBytes = DataInput.load(dataReader, offset);

        if (dataCompressedBytes != null)
        {
            try
            {
                byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
                dataDefinition = DataDefinitionSerializer.instance.deserialize(uncompressed, true);
            }
            catch (IOException e)
            {
                logger.error("Could not get data from DataHolder {}: {} ", filename, e.getMessage());
            }
        }

        if (dataReader != null)
        {
            dataReader.close();
        }

        return dataDefinition;
    }

    public LinkedHashSet<DataDefinition> fetchAll()
    {
        return indexer.getIndexList().entrySet().stream().map(idx -> get(idx.getKey())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String getFilename()
    {
        return filename;
    }

    public boolean isEmpty()
    {
        return getSize() <= 0;
    }

    public long getSize()
    {
        return dataReader == null ? 0 : dataReader.length();
    }

    public void clear()
    {
        if (dataWriter != null)
        {
            dataWriter.truncate(0);
        }
    }

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
