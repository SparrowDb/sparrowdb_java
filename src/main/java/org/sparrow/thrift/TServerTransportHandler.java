package org.sparrow.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.spql.SpqlParser;

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
    public String clear_database(String dbname) throws TException
    {
        return null;
    }

    @Override
    public String insert_data(DataObject object) throws TException
    {
        return SparrowDatabase.instance.insert_data(object);
    }

    @Override
    public String bulk_insert_data(List<DataObject> objects) throws TException
    {
        return null;
    }

    @Override
    public String delete_data(DataObject object) throws TException
    {
        return null;
    }

    @Override
    public String bulk_delete_data(List<DataObject> objects) throws TException
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
