package org.sparrow.server;

import com.google.common.base.Strings;
import org.apache.thrift.TException;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.SparrowDatabase;
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
        if (object.getSize() < DatabaseDescriptor.config.max_datalog_size)
        {
            if (SparrowDatabase.instance.databaseExists(object.getDbname()))
            {
                SparrowDatabase.instance.insert_data(object);
                return "";
            }
        }
        return "Could not insert data";
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
