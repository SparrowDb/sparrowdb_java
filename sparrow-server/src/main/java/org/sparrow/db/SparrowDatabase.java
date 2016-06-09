package org.sparrow.db;

import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;
import org.sparrow.config.DatabaseConfig;
import org.sparrow.config.DatabaseDescriptor;

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

    private SparrowDatabase()
    {
    }

    public boolean createDatabase(DatabaseConfig.Descriptor descriptor)
    {
        if (!databaseExists(descriptor.name))
        {
            try
            {
                Database database = Database.build(descriptor);
                databases.put(descriptor.name, database);
                return true;
            } catch (Exception e)
            {
                logger.error("Could not create database {}: {} ", descriptor.name, e.getMessage());
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

            FileUtils.delete(DatabaseDescriptor.getDatabaseConfigByName(dbname).path);

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

    public void loadFromDisk()
    {
        DatabaseDescriptor.database.databases.stream()
                .forEach(x -> {
                    Database database = Database.open(x);
                    databases.put(x.name, database);
                });
    }
}
