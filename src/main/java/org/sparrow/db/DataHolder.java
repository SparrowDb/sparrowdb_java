package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.serializer.IndexSeralizer;
import org.sparrow.util.BloomFilter;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;
import org.xerial.snappy.Snappy;

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

    @Override
    protected void deleteData(DataDefinition dataDefinition)
    {

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


    public static class DataHolderFileManager {

        private static boolean isValidDataHolder(String filename)
        {
            if (!FileUtils.fileExists(filename.replace("data-holder", "index")))
            {
                logger.warn("Index file does not exists for {}", filename);
                return false;
            }
            if (!FileUtils.fileExists(filename.replace("data-holder", "bloomfilter")))
            {
                logger.warn("Bloom filter file does not exists for {}", filename);
                return false;
            }
            return true;
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
                    if (isValidDataHolder(filename))
                    {
                        DataHolder dataHolder = DataHolder.open(filename);
                        collection.add(dataHolder);
                    }
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
                if (file.exists())
                {
                    int next = filecount+1;
                    File nextFile = new File(SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", next)));
                    if (!nextFile.exists() && file.exists())
                    {
                        return file.getName();
                    }
                }
                else
                {
                    return file.getName();
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
}
