package org.sparrow.db;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.cache.CacheManager;
import org.sparrow.common.DataDefinition;
import org.sparrow.common.Tombstone;
import org.sparrow.common.util.FileUtils;
import org.sparrow.common.util.SPUtils;
import org.sparrow.config.DatabaseConfig;
import org.sparrow.protocol.DataObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by mauricio on 25/12/2015.
 */
public class Database
{
    private static Logger logger = LoggerFactory.getLogger(Database.class);
    private volatile Set<DataHolder> dataHolders;
    private volatile DataLog dataLog;
    private final DatabaseConfig.Descriptor descriptor;
    private volatile CacheManager<String, DataDefinition> cacheManager;

    private Database(DatabaseConfig.Descriptor descriptor)
    {
        this.descriptor = descriptor;
        cacheManager = new CacheManager<>(descriptor.name, descriptor.max_cache_size);
        dataHolders = Sets.newConcurrentHashSet();
        dataLog = new DataLog(dataHolders, descriptor);
    }

    public static synchronized Database build(DatabaseConfig.Descriptor descriptor)
    {
        Database database = null;
        try
        {
            FileUtils.createDirectory(descriptor.path);
            database = new Database(descriptor);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return database;
    }

    public static synchronized Database open(DatabaseConfig.Descriptor descriptor)
    {
        Database database = new Database(descriptor);

        if (!database.dataLog.isEmpty())
        {
            logger.debug("Loading datalog {} with size: {}", descriptor.name, database.dataLog.getSize());
            database.dataLog.load();
        }

        DataFileManager.getDataHolders(descriptor.path)
                .stream()
                .forEach(x -> {
                    if (DataFileManager.isValidDataHolder(x.getAbsolutePath()))
                    {
                        database.dataHolders.add(DataHolder.open(x.getName(), descriptor));
                    }
                });

        return database;
    }


    public synchronized void close()
    {
        cacheManager.clear();
        dataHolders.clear();
        dataLog.close();
    }

    public void insertData(DataObject object)
    {
        int hash32key = SPUtils.hash32(object.getKey());
        DataDefinition dataDefinition = new DataDefinition();
        dataDefinition.setKey(object.getKey());
        dataDefinition.setKey32(hash32key);

        /*
         *  As append only data file, the offset of new data is the
         *   the size of data file. It is updated when the data is
         *   written to the file.
        */
        dataDefinition.setOffset(0);

        // Get current time int UTC
        dataDefinition.setTimestamp(java.time.Instant.now().getEpochSecond());
        dataDefinition.setSize(object.bufferForData().capacity());
        dataDefinition.setExtension(object.getExtension().toLowerCase());
        dataDefinition.setState(DataDefinition.DataState.ACTIVE);
        dataDefinition.setBuffer(object.bufferForData().array());
        insertData(dataDefinition);

        // Put in cache
        cacheManager.put(dataDefinition.getKey(), dataDefinition);
    }

    public void insertData(DataDefinition dataDefinition)
    {
        dataLog.add(dataDefinition);
    }

    public DataDefinition getDataWithImageByKey32(String dataKey)
    {
        DataDefinition dataDefinition = cacheManager.get(dataKey);

        if (dataDefinition == null)
        {
            dataDefinition = dataLog.get(dataKey);
        }

        if (dataDefinition == null)
        {
            Iterator<DataHolder> iterDataHolder = dataHolders.stream()
                    .filter(x -> x.isKeyInFile(dataKey))
                    .iterator();

            while (iterDataHolder.hasNext())
            {
                dataDefinition = iterDataHolder.next().get(dataKey);
            }
        }

        if (dataDefinition != null)
        {
            cacheManager.put(dataKey, dataDefinition);
        }

        return dataDefinition;
    }

    public boolean deleteData(String dataKey)
    {
        DataDefinition dataDefinition = getDataWithImageByKey32(dataKey);

        if (dataDefinition == null)
        {
            return false;
        }
        else
        {
            Tombstone tombstone = new Tombstone(dataDefinition);
            dataLog.add(tombstone);
            cacheManager.put(dataKey, tombstone);
        }

        return true;
    }

    public Set<DataHolder> getDataHolders()
    {
        return dataHolders;
    }

    public DataLog getDataLog()
    {
        return dataLog;
    }

    public long countData()
    {
        long count = 0;

        count += dataLog.count();

        for (DataHolder dh : dataHolders)
        {
            count += dh.count();
        }

        return count;
    }

    public DatabaseConfig.Descriptor getDescriptor()
    {
        return descriptor;
    }
}