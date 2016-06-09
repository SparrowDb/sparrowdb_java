package org.sparrow.server;

import com.google.common.base.Strings;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;
import org.sparrow.config.DatabaseConfig;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.config.SparrowFile;
import org.sparrow.db.Database;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.plugin.FilterManager;
import org.sparrow.plugin.IFilter;
import org.sparrow.protocol.DataObject;
import org.sparrow.protocol.SparrowTransport;
import org.sparrow.protocol.SpqlResult;
import org.sparrow.spql.SpqlProcessor;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by mauricio on 24/12/2015.
 */
public class TServerTransportHandler implements SparrowTransport.Iface
{
    private static Logger logger = LoggerFactory.getLogger(TServerTransportHandler.class);
    private InetAddress address;

    public TServerTransportHandler()
    {
    }

    public TServerTransportHandler(InetAddress address)
    {
        this.address = address;
    }

    @Override
    public String authenticate(String username, String password) throws TException
    {
        return null;
    }

    @Override
    public String logout() throws TException
    {
        return null;
    }

    @Override
    public List<String> show_databases() throws TException
    {
        List<String> list = new ArrayList<>(SparrowDatabase.instance.getDatabases());
        Collections.sort(list, (str1, str2) -> str1.toUpperCase(Locale.getDefault()).compareTo(str2.toUpperCase(Locale.getDefault())));
        return list;
    }

    @Override
    public String create_database(String dbname, Map<String, String> params) throws TException
    {
        DatabaseConfig.Descriptor descriptor = new DatabaseConfig.Descriptor();
        descriptor.name = dbname;
        descriptor.path = FileUtils.joinPath(DatabaseDescriptor.config.data_file_directory, dbname);
        descriptor.max_datalog_size = DatabaseDescriptor.config.max_datalog_size;
        descriptor.max_cache_size = DatabaseDescriptor.config.max_cache_size;
        descriptor.bloomfilter_fpp = DatabaseDescriptor.config.bloomfilter_fpp;
        descriptor.dataholder_cron_compaction = DatabaseDescriptor.config.dataholder_cron_compaction;

        DatabaseDescriptor.database.databases.add(descriptor);
        DatabaseDescriptor.saveConfigurationFile(SparrowFile.DATABASE);

        boolean result = SparrowDatabase.instance.createDatabase(descriptor);
        return result ? "Database " + dbname + " created" : "Could not create database " + dbname;
    }

    @Override
    public String drop_database(String dbname) throws TException
    {
        boolean result = SparrowDatabase.instance.dropDatabase(dbname);

        DatabaseDescriptor.database.databases.remove(DatabaseDescriptor.getDatabaseConfigByName(dbname));
        DatabaseDescriptor.saveConfigurationFile(SparrowFile.DATABASE);

        return result ? "Database " + dbname + " dropped" : "Could not drop database " + dbname;
    }

    @Override
    public String insert_data(DataObject object, List<String> params) throws TException
    {
        if (object.getSize() > DatabaseDescriptor.config.max_datalog_size)
        {
            return "Data size is greater than max_datalog_size: " + DatabaseDescriptor.config.max_datalog_size;
        }


        if (!SparrowDatabase.instance.databaseExists(object.getDbname()))
        {
            return "Database " + object.getDbname() + " does not exists";
        }

        if (params.size() > 3)
        {
            IFilter filter = FilterManager.instance.getFilter(params.get(3));

            if (filter == null)
            {
                return "Invalid filter: " + params.get(3);
            }
            else
            {
                byte[] buff = null;
                try
                {
                    buff = filter.process(object.getData());
                    object.setData(buff);
                }
                catch (Exception e)
                {
                    logger.error("Filter error (Database: {}, Key: {}, Filter: {}) : {}",
                            object.getDbname(),
                            object.getKey(),
                            params.get(3),
                            e.getMessage());
                    return String.format("Filter error (Database: {}, Key: {}, Filter: {}) ",
                            object.getKey(),
                            object.getDbname(),
                            params.get(3));
                }
                finally
                {
                    buff = null;
                }
            }
        }


        Database database  = SparrowDatabase.instance.getDatabase(object.getDbname());
        database.insertData(object);
        return String.format("Data %s inserted in %s", object.getKey(), object.getDbname());
    }

    @Override
    public String delete_data(String dbname, String keyName, String keyValue) throws TException
    {
        Database database  = SparrowDatabase.instance.getDatabase(dbname);
        if (database != null)
        {
            database.deleteData(keyValue);
            return "";
        }
        return "Data deleted";
    }

    @Override
    public SpqlResult spql_query(String dbname, String keyName, String keyValue) throws TException
    {
        if (SparrowDatabase.instance.databaseExists(dbname)) {
                return (Strings.isNullOrEmpty(keyName) && Strings.isNullOrEmpty(keyValue)) ? SpqlProcessor.queryDataAll(dbname) : SpqlProcessor.queryDataWhereKey(dbname, keyValue);
        } else {
            return null;
        }

    }
}
