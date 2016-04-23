package org.sparrow.server;

import com.google.common.base.Strings;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.plugin.FilterManager;
import org.sparrow.plugin.IFilter;
import org.sparrow.protocol.DataObject;
import org.sparrow.protocol.SparrowTransport;
import org.sparrow.protocol.SpqlResult;
import org.sparrow.spql.SpqlProcessor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    public String create_database(String dbname) throws TException
    {
        boolean result = SparrowDatabase.instance.createDatabase(dbname);
        return result ? "Database " + dbname + " created" : "Could not create database " + dbname;
    }

    @Override
    public String drop_database(String dbname) throws TException
    {
        boolean result = SparrowDatabase.instance.dropDatabase(dbname);
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

        SparrowDatabase.instance.insert_data(object);

        return String.format("Data %s inserted in %s", object.getKey(), object.getDbname());
    }

    @Override
    public String delete_data(String dbname, String keyName, String keyValue) throws TException
    {
        if (SparrowDatabase.instance.databaseExists(dbname))
        {
            SparrowDatabase.instance.delete_data(dbname, keyValue);
            return "";
        }
        return "Data deleted";
    }

    @Override
    public SpqlResult spql_query(String dbname, String keyName, String keyValue) throws TException
    {
        return (Strings.isNullOrEmpty(keyName) && Strings.isNullOrEmpty(keyValue)) ? SpqlProcessor.queryDataAll(dbname) : SpqlProcessor.queryDataWhereKey(dbname, keyValue);
    }
}
