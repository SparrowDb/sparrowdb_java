package org.sparrow.db.compaction;

import ch.qos.logback.core.util.FileUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.DataDefinition;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.io.DataInput;
import org.sparrow.io.IDataReader;
import org.sparrow.io.StorageReader;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.sparrow.util.FileUtils;
import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public LinkedHashSet<File> getDataHoldersFromDatabase(String dbname)
    {
        File dbdir = new File(DatabaseDescriptor.getDataFilePath(dbname));
        return Arrays.stream(FileUtils.listFiles(dbdir))
                .filter(x -> x.getName().startsWith("data"))
                .sorted(File::compareTo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
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

    public void getDataHolderDirty(String dbname, File dataHolderFile, Set<String> dirtyKey)
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


    public void processDatabase(String dbname)
    {
        try
        {
            FileUtils.createDirectory(DatabaseDescriptor.getDataFilePath("temp"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        getDataHoldersFromDatabase(dbname)
                .stream()
                .forEach(x -> {
                    File newFile = new File(DatabaseDescriptor.getDataFilePath("temp", x.getName() + ".tmp"));
                    x.renameTo(newFile);
                });
        SparrowDatabase.instance.dropDatabase(dbname);
        SparrowDatabase.instance.createDatabase(dbname);


        Set<String> dirtyKeys = new LinkedHashSet<>();
        getDataHoldersFromDatabase("temp")
                .stream()
                .forEach(x -> {
                    getDataHolderDirty(dbname, x, dirtyKeys);
                });

        getDataHoldersFromDatabase("temp")
                .stream()
                .forEach(x -> {
                        try
                        {
                            iterateDataHolder(x, f -> {
                                if (!dirtyKeys.contains(f.getKey()))
                                {
                                    SparrowDatabase.instance.getDatabase(dbname).insertData(f);
                                }
                            });
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                });

        FileUtils.delete(DatabaseDescriptor.getDataFilePath("temp"));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        logger.debug("Starting DataHolder compact");

        Set<String> databases = SparrowDatabase.instance.getDatabases();

        databases.forEach(this::processDatabase);
    }
}
