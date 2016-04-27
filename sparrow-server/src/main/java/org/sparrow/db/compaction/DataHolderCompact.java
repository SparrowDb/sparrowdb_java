package org.sparrow.db.compaction;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.DataDefinition;
import org.sparrow.common.io.DataInput;
import org.sparrow.common.io.IDataReader;
import org.sparrow.common.io.StorageReader;
import org.sparrow.common.serializer.DataDefinitionSerializer;
import org.sparrow.common.util.FileUtils;
import org.sparrow.config.DatabaseConfig;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.DataFileManager;
import org.sparrow.db.SparrowDatabase;
import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mauricio on 12/03/16.
 */
public class DataHolderCompact implements Job
{
    private static Logger logger = LoggerFactory.getLogger(DataHolderCompact.class);

    @FunctionalInterface
    interface DataDefinitionProcess{
        void apply(DataDefinition dataDefinition);
    }

    public void iterateDataHolder(File dataHolder, DataDefinitionProcess dataDefinitionProcess) throws IOException
    {
        IDataReader dataReader  = StorageReader.open(dataHolder);

        long fileSize = dataReader.length();
        long currentSize = 0;

        while (currentSize < fileSize)
        {
            byte[] dataCompressedBytes = DataInput.load(dataReader, currentSize);
            byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
            dataDefinitionProcess.apply(DataDefinitionSerializer.instance.deserialize(uncompressed, true));
            currentSize += (dataCompressedBytes.length + 4);
        }
        dataReader.close();
    }

    public void getDataHolderDirty(File dataHolderFile, Set<String> dirtyKey)
    {
        try
        {
            iterateDataHolder(dataHolderFile, x -> {
                if (x.getState() == DataDefinition.DataState.REMOVED)
                {
                    dirtyKey.add(x.getKey());
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void processDatabase(DatabaseConfig.Descriptor descriptor)
    {
        String tempPath = FileUtils.joinPath(descriptor.path, "temp");

        try
        {
            FileUtils.createDirectory(tempPath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        DataFileManager.getDataHolders(descriptor.path)
                .stream()
                .forEach(x -> {
                    File newFile = new File(FileUtils.joinPath(tempPath, x.getName()));
                    x.renameTo(newFile);
                });


        Set<String> dirtyKeys = new LinkedHashSet<>();
        DataFileManager.getDataHolders(tempPath)
                .stream()
                .forEach(x -> getDataHolderDirty(x, dirtyKeys));


        DataFileManager.getDataHolders(tempPath)
                .stream()
                .forEach(x -> {
                    try
                    {
                        iterateDataHolder(x, f -> {
                            if (!dirtyKeys.contains(f.getKey()))
                            {
                                SparrowDatabase.instance.getDatabase(descriptor.name).insertData(f);
                            }
                        });
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });

        FileUtils.delete(tempPath);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        logger.debug("Starting DataHolder compact");

        List<DatabaseConfig.Descriptor> databases = DatabaseDescriptor.database.databases;

        databases.forEach(this::processDatabase);
    }
}
