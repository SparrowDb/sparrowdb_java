package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.io.*;
import org.sparrow.common.serializer.IndexSeralizer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mauricio on 4/15/16.
 */
public class IndexManager
{
    private static Logger logger = LoggerFactory.getLogger(IndexManager.class);

    static void loadIndex(DataFile dataFile)
    {
        IDataReader indexReader = StorageReader.open(dataFile.getIndexFileName());
        long fileSize = indexReader.length();
        long indexSize = 0;

        while (indexSize < fileSize)
        {
            byte[] bytes = DataInput.load(indexReader, indexSize);
            try
            {
                Map.Entry<Integer, Long> entry = IndexSeralizer.instance.deserialize(bytes);
                dataFile.getIndexer().put(entry.getKey(), entry.getValue());
                indexSize += (bytes.length + 4);
            }
            catch (IOException e)
            {
                logger.error("Could not load data from Index file {}: {} ", dataFile.getIndexFileName(), e.getMessage());
            }
        }
        if (indexReader != null)
        {
            indexReader.close();
        }
    }

    private static void appendIndex(IDataWriter dataWriter, int key, long offset) throws IOException
    {
        byte[] serializedData = IndexSeralizer.instance.serialize(key, offset);
        DataOutput.save(dataWriter, serializedData);
    }

    static void writeIndexFile(DataFile dataFile)
    {
        IDataWriter indexWriter = StorageWriter.open(dataFile.getIndexFileName());

        for (Map.Entry<Integer, Long> entry : dataFile.getIndexer().getIndexList().entrySet())
        {
            try
            {
                appendIndex(indexWriter, entry.getKey(), entry.getValue());
            }
            catch (IOException e)
            {
                logger.error("Could not append data to Index file {}: {} ", dataFile.getIndexFileName(), e.getMessage());
            }
        }

        if (indexWriter != null)
        {
            indexWriter.close();
        }
    }
}
