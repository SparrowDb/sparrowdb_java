package org.sparrow.db;

import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.rpc.DataObject;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by mauricio on 26/12/2015.
 */
public class SparrowDatabase
{
    private static final Logger logger = LoggerFactory.getLogger(SparrowDatabase.class);
    public static final SparrowDatabase instance = new SparrowDatabase();
    private volatile Map<String, Database> databases = new NonBlockingHashMap<>();

    public SparrowDatabase()
    {

    }

    public boolean createDatabase(String dbname)
    {
        if (!databaseExists(dbname))
        {
            try
            {
                Database database = Database.build(dbname);
                databases.put(dbname, database);
                return true;
            } catch (Exception e)
            {
                logger.error("Could not create database {}: {} ", dbname, e.getMessage());
            }
        }
        return false;
    }

    public boolean databaseExists(String dbname)
    {
        return databases.containsKey(dbname);
    }

    public boolean dropDatabase(String dbname)
    {
        Database database = databases.get(dbname);
        if (database != null)
        {
            logger.debug("Dropping database {}", dbname);
            database.close();
            databases.remove(dbname);
            FileUtils.delete(new File(SPUtils.getDbPath(dbname)).getAbsolutePath());
            return true;
        }
        return false;
    }

    public Database getDatabase(String dbname)
    {
        return databases.get(dbname);
    }

    public Set<String> getDatabases()
    {
        return databases.keySet();
    }

    public Map<String, Database> getDatabasesHolder()
    {
        return databases;
    }

    public void insert_data(DataObject object)
    {
        getDatabase(object.getDbname()).insertData(object);
    }

    public void delete_data(String dbname, String key)
    {
        getDatabase(dbname).deleteData(key);
    }

    public void loadFromDisk()
    {
        DatabaseDescriptor.filterDatabasesDir(new File(DatabaseDescriptor.getDataFilePath()))
                .forEach(x -> {
                    Database database = Database.open(x.getName());
                    databases.put(x.getName(), database);
                });
    }

    public DataDefinition getObjectByKey(String dbname, String key)
    {
        Database database = getDatabase(dbname);
        return (database!=null) ? database.getDataWithImageByKey32(key) : null ;
    }

    public void loadDatabase(String dbname)
    {
        String path = DatabaseDescriptor.getDataFilePath(dbname);
        Database database = Database.open(path);
        databases.put(path, database);
    }

    public void closeDatabase(String dbname)
    {
        Database database = getDatabase(dbname);
        if (database!=null)
        {
            database.close();
        }
    }
}
