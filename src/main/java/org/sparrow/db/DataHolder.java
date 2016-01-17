package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.io.*;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.serializer.IndexSeralizer;
import org.sparrow.util.SPUtils;

import java.io.File;
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
    private String filename;
    private String indexFile;
    private IDataWriter dataWriter;
    private IDataReader dataReader;

    public DataHolder(String filename)
    {
        this.filename = filename;
        this.indexFile = filename.replace("data-holder", "index");
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
        indexer.put(dataDefinition.getKey32(), dataDefinition.getOffset());
        writeIndex(dataDefinition.getKey32(), dataDefinition.getOffset());
    }

    public void afterAppend()
    {
        dataWriter.close();
    }

    public boolean isKeyInFile(String key)
    {
        return false;
    }

    public void writeIndex(int key, long offset)
    {
        IDataWriter indexWriter = StorageWriter.open(indexFile);
        byte[] serializedData = IndexSeralizer.instance.serialize(key, offset);
        DataOutput.save(indexWriter, serializedData);
        indexWriter.close();
    }

    public void loadIndexFile()
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
        this.dataReader = StorageReader.open(this.filename);
        Long offset = indexer.get(key32);

        if (offset!=null)
        {
            byte[] bytes = DataInput.load(dataReader, offset);
            return DataDefinitionSerializer.instance.deserialize(bytes, true);
        }
        this.dataReader.close();
        return null;
    }

    public LinkedHashSet<DataDefinition> fetchAll()
    {
        return indexer.getIndexList().entrySet().stream().map(
                idx -> get(idx.getKey())).collect(
                Collectors.toCollection(() -> new LinkedHashSet<>()));
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
                DataHolder dataHolder = new DataHolder(filename);
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
