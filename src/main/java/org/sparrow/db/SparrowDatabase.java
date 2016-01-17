package org.sparrow.db;

import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.thrift.DataObject;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.io.File;
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
                e.printStackTrace();
                return false;
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

    public String insert_data(DataObject object)
    {
        Database database = getDatabase(object.getDbname());
        if (database != null)
        {
            return database.insert_data(object);
        }
        return "Could not insert data " + object.getKey();
    }

    public void loadFromDisk()
    {
        File[] dirs = FileUtils.listSubdirectories(new File(DatabaseDescriptor.getDataFilePath()));
        for(File dir : dirs)
        {
            Database database = Database.open(dir.getName());
            databases.put(dir.getName(), database);
        }
    }

    public DataDefinition getObjectByKey(String dbname, String key)
    {
        Database database = getDatabase(dbname);
        return (database!=null) ? database.getDataWithImageByKey32(key) : null ;
    }
}
