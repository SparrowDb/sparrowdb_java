package org.sparrow.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.spql.SpqlParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mauricio on 24/12/2015.
 */
public class TServerTransportHandler implements SparrowTransport.Iface
{
    private static final Logger logger = LoggerFactory.getLogger(TServerTransportHandler.class);

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
        Collections.sort(list, (str1, str2) -> str1.toUpperCase().compareTo(str2.toUpperCase()));
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
    public String insert_data(DataObject object) throws TException
    {
        if (SparrowDatabase.instance.databaseExists(object.getDbname()))
        {
            SparrowDatabase.instance.insert_data(object);
            return "";
        }
        return "Could not insert data";
    }

    @Override
    public String delete_data(DataObject object) throws TException
    {
        return null;
    }

    @Override
    public SpqlResult spql_query(String query) throws TException
    {
        SpqlResult result = SpqlParser.parseAndProcess(query);
        if (result == null)
            return new SpqlResult();
        return result;
    }
}
