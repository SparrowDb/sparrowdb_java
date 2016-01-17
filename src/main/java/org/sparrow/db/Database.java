package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.thrift.DataObject;
import org.sparrow.thrift.SpqlResult;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mauricio on 25/12/2015.
 */
public class Database
{
    private static Logger logger = LoggerFactory.getLogger(Database.class);
    private static final String FILENAME_EXTENSION = ".spw";
    private Set<DataHolder> dataHolder;
    private DataLog dataLog;
    private String dbname;
    private Lock lock_ = new ReentrantLock();

    private Database(String dbname)
    {
        this.dbname = dbname;
        dataLog = new DataLog(dbname, SPUtils.getDbPath(dbname, "datalog", FILENAME_EXTENSION));
        dataHolder = new LinkedHashSet<>();
    }

    public static Database build(String dbname)
    {
        try
        {
            FileUtils.createDirectory(DatabaseDescriptor.getDataFilePath() + dbname);
            return new Database(dbname);
        } catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public static Database open(String dbname)
    {
        Database database = new Database(dbname);

        if (!database.dataLog.isEmpty())
        {
            logger.debug("Loading datalog {} with size: {}", dbname, database.dataLog.getSize());
            database.dataLog.load();
            database.dataLog.flush(database.dataHolder);
        }

        DataHolder.loadDataHolders(database.dataHolder, dbname);
        for(DataHolder dataHolder : database.dataHolder)
        {
            dataHolder.loadIndexFile();
        }

        return database;
    }

    public void close()
    {
    }

    public String insert_data(DataObject object)
    {
        lock_.lock();
        try
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
            dataDefinition.setSize(object.bufferForData().capacity());
            dataDefinition.setCrc32(0);
            dataDefinition.setExtension(DataDefinition.Extension.PNG);
            dataDefinition.setState(DataDefinition.DataState.ACTIVE);
            dataDefinition.setBuffer(object.bufferForData().array());

            if (dataLog.needFlush(dataDefinition.getSize()))
            {
                dataLog.flush(dataHolder);
            }
            dataLog.append(dataDefinition);

            return String.valueOf(hash32key);
        }
        finally
        {
            lock_.unlock();
        }
    }

    public DataDefinition getDataWithImageByKey32(String dataKey)
    {
        DataDefinition dataDefinition = null;

        dataDefinition = dataLog.get(dataKey);

        if (dataDefinition == null)
        {
            for (DataHolder dh : dataHolder)
            {
                dataDefinition = dh.get(dataKey);
                if (dataDefinition != null)
                    return  dataDefinition;
            }
        }

        return dataDefinition;
    }

    public SpqlResult mapToSpqlResult(Set<DataDefinition> data)
    {
        SpqlResult result = new SpqlResult();
        for(DataDefinition dataDefinition : data)
        {
            DataObject dataObject = new DataObject();
            dataObject.setDbname(dbname);
            dataObject.setSize(dataDefinition.getSize());
            dataObject.setTimestamp(0);
            dataObject.setKey(dataDefinition.getKey());
            dataObject.setState(dataDefinition.getState().ordinal());
            result.addToRows(dataObject);
        }
        result.count = data.size();
        return  result;
    }

    public SpqlResult query_data_where_timestamp(long value)
    {
        return  null;
    }

    public SpqlResult query_data_where_key(int value)
    {
        return  null;
    }

    public SpqlResult query_data_all()
    {
        Set result = new LinkedHashSet<>();
        dataHolder.forEach(x -> result.addAll(x.fetchAll()));
        return mapToSpqlResult(result);
    }
}