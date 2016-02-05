package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.serializer.IndexSeralizer;
import org.sparrow.util.BloomFilter;
import org.sparrow.util.SPUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 07/01/2016.
 */
public class DataHolder
{
    private static Logger logger = LoggerFactory.getLogger(DataHolder.class);
    private IndexSummary indexer = new IndexSummary();
    private BloomFilter bf = null;
    private String filename;
    private String indexFile;
    private String bloomFilterFile;
    private IDataWriter dataWriter;

    private DataHolder(String filename)
    {
        this.filename = filename;
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

    public static DataHolder create(String filename)
    {
        return new DataHolder(filename);
    }


    public void beforeAppend()
    {
        dataWriter = StorageWriter.open(filename);
    }

    public void append(DataDefinition dataDefinition)
    {
        dataDefinition.setOffset(dataWriter.length());
        logger.debug("Writing data: {}", DataDefinitionSerializer.instance.toString(dataDefinition));
        byte[] serializedData = DataDefinitionSerializer.instance.serialize(dataDefinition);
        DataOutput.save(dataWriter, serializedData);
        writeIndex(dataDefinition.getKey32(), dataDefinition.getOffset());
    }

    public void afterAppend()
    {
        dataWriter.close();
        loadIndexFile();
        writeBloomFilter();
    }

    public boolean isKeyInFile(String key)
    {
        String hashed = String.valueOf(SPUtils.hash32(key));
        return bf.contains(hashed);
    }

    private void writeIndex(int key, long offset)
    {
        IDataWriter indexWriter = StorageWriter.open(indexFile);
        byte[] serializedData = IndexSeralizer.instance.serialize(key, offset);
        DataOutput.save(indexWriter, serializedData);
        if (indexWriter != null)
        {
            indexWriter.close();
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
            bfWriter.write(bytes);
        }
        catch (IOException e)
        {
            logger.error("Could not write bloomfilter {}", bloomFilterFile);
        }
        bfWriter.close();
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
        dataReader.close();
    }

    private void loadIndexFile()
    {
        IDataReader indexReader = StorageReader.open(indexFile);
        long fileSize = indexReader.length();
        long indexSize = 0;

        while (indexSize < fileSize)
        {
            byte[] bytes = DataInput.load(indexReader, indexSize);
            Map.Entry<Integer, Long> entry = IndexSeralizer.instance.deserialize(bytes);
            indexer.put(entry.getKey(), entry.getValue());
            indexSize += (bytes.length + 4);
        }

        indexReader.close();
    }

    public DataDefinition get(String key)
    {
        return get(SPUtils.hash32(key));
    }

    public DataDefinition get(int key32)
    {
        IDataReader dataReader = StorageReader.open(this.filename);
        Long offset = indexer.get(key32);

        if (offset!=null)
        {
            byte[] bytes = DataInput.load(dataReader, offset);
            return DataDefinitionSerializer.instance.deserialize(bytes, true);
        }
        dataReader.close();
        return null;
    }

    public LinkedHashSet<DataDefinition> fetchAll()
    {
        return indexer.getIndexList().entrySet().stream().map(
                idx -> get(idx.getKey())).collect(
                Collectors.toCollection(LinkedHashSet::new));
    }

    public String getFilename()
    {
        return filename;
    }

    public static void loadDataHolders(Set<DataHolder> collection, String dbname)
    {
        int filecount = 0;
        String filename;
        File file;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            file = new File(filename);
            if (file.exists())
            {
                DataHolder dataHolder = DataHolder.open(filename);
                collection.add(dataHolder);
            } else {
                break;
            }
            filecount++;
        }
    }

    public static String getLastFilename(String dbname)
    {
        int filecount = 0;
        String filename;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            File file = new File(filename);
            if (!file.exists())
            {
                return file.getName();
            }
            else
            {
                int next = filecount+1;
                File nextFile = new File(SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", next)));
                if (!nextFile.exists() && file.exists())
                {
                    return file.getName();
                }
            }
            filecount++;
        }
    }

    public static String getNextFilename(String dbname)
    {
        int filecount = 0;
        String filename;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            File file = new File(filename);
            if (!file.exists())
            {
                return file.getName();
            }
            filecount++;
        }
    }
}