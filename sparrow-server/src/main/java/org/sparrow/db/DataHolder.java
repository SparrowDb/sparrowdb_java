package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.io.*;
import org.sparrow.common.serializer.IndexSeralizer;
import org.sparrow.common.util.BloomFilter;
import org.sparrow.common.util.SPUtils;
import org.sparrow.config.DatabaseDescriptor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Created by mauricio on 07/01/2016.
 */
public final class DataHolder extends DataFile
{
    private static Logger logger = LoggerFactory.getLogger(DataHolder.class);
    private BloomFilter bf;
    private final String indexFile;
    private final String bloomFilterFile;

    private DataHolder(String filename)
    {
        super();
        this.filename = filename;
        dataHolderProxy = new DataHolderProxy(filename);
        this.indexFile = filename.replace("data-holder", "index");
        this.bloomFilterFile = filename.replace("data-holder", "bloomfilter");
    }

    public static DataHolder open(String filename)
    {
        DataHolder dataHolder = new DataHolder(filename);
        dataHolder.loadIndexFile();
        dataHolder.loadBloomFilter();
        return dataHolder;
    }

    public static DataHolder create(String filename, IndexSummary indexer)
    {
        DataHolder dh = new DataHolder(filename);
        indexer.getIndexList().forEach((key, value) -> {
            dh.writeIndex(key, value);
            dh.indexer.put(key, value);
        });
        dh.writeBloomFilter();
        return dh;
    }

    public boolean isKeyInFile(String key)
    {
        String hashed = String.valueOf(SPUtils.hash32(key));
        return bf.contains(hashed);
    }

    private void writeIndex(int key, long offset)
    {
        IDataWriter indexWriter = StorageWriter.open(indexFile);
        try
        {
            byte[] serializedData = IndexSeralizer.instance.serialize(key, offset);
            DataOutput.save(indexWriter, serializedData);
        }
        catch (IOException e)
        {
            logger.error("Could not append data to Index file {}: {} ", indexFile, e.getMessage());
        }
        finally
        {
            if (indexWriter != null)
            {
                indexWriter.close();
            }
        }
    }

    private void writeBloomFilter()
    {
        bf = new BloomFilter(indexer.size(), DatabaseDescriptor.config.bloomfilter_fpp);
        for (Map.Entry<Integer, Long> idx : indexer.getIndexList().entrySet()) {
            bf.add(String.valueOf(idx.getKey()));
        }

        byte[] bytes = BloomFilter.BloomFilterSerializer.serializer(bf);
        IDataWriter bfWriter = StorageWriter.open(bloomFilterFile);

        try
        {
            if (bfWriter != null)
            {
                bfWriter.write(bytes);
            }
        }
        catch (IOException e)
        {
            logger.error("Could not write bloomfilter {}", bloomFilterFile);
        }
        finally
        {
            if (bfWriter != null)
            {
                bfWriter.close();
            }
        }
    }

    public void loadBloomFilter()
    {
        IDataReader dataReader = StorageReader.open(bloomFilterFile);
        ByteBuffer buffer = ByteBuffer.allocate((int)dataReader.length());
        try
        {
            dataReader.readChunck(0, buffer);
            bf = BloomFilter.BloomFilterSerializer.deserialize(buffer.array());
        }
        catch (IOException e)
        {
            logger.error("Could not read bloomfilter {}", bloomFilterFile);
        }
        finally
        {
            if (dataReader != null)
            {
                dataReader.close();
            }
        }
    }

    private void loadIndexFile()
    {
        IDataReader indexReader = StorageReader.open(indexFile);
        long fileSize = indexReader.length();
        long indexSize = 0;

        while (indexSize < fileSize)
        {
            byte[] bytes = DataInput.load(indexReader, indexSize);
            try
            {
                Map.Entry<Integer, Long> entry = IndexSeralizer.instance.deserialize(bytes);
                indexer.put(entry.getKey(), entry.getValue());
                indexSize += (bytes.length + 4);
            } catch (IOException e)
            {
                logger.error("Could not load data from Index file {}: {} ", indexFile, e.getMessage());
            }
        }
        if (indexReader != null)
        {
            indexReader.close();
        }
    }
}
