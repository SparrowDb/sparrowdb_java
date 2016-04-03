package org.sparrow.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.sparrow.rpc.DataObject;
import org.sparrow.rpc.SparrowTransport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mauricio on 4/2/16.
 */
public class CommandProcessor
{
    private String host;
    private int port;
    private boolean active;

    private TTransport transport;
    private TProtocol protocol;
    private SparrowTransport.Client client;
    private HashMap<String, Command> commands = new HashMap<>();

    @FunctionalInterface
    public interface Command {
        void execute(String[] args);
    }

    public CommandProcessor()
    {
        commands.put("^show databases\\s*;$", (x) -> showDatabases());
        commands.put("^create database ([A-Za-z0-9]{3,20})\\s*;$", (x) -> createDatabase(x[0]));
        commands.put("^drop database ([A-Za-z0-9]{3,20})\\s*;$", (x) -> dropDatabase(x[0]));
        commands.put("^delete data ([A-Za-z0-9]{3,20}).([A-Za-z0-9]{3,20})\\s*;$", (x) -> deleteData(x[0], x[1]));
        commands.put("^insert into ([A-Za-z0-9]{3,20})\\s*\\(\\s*(.{1,}.{4}\\s*),\\s*([A-Za-z0-9]{3,20}\\s*)\\)\\s*;$", (x) -> insertData(x[1], x[0], x[2]));
        commands.put("^exit\\s*$", (x) -> close());
    }

    public boolean isActive()
    {
        return active;
    }

    public void connect(String host, String port) throws TTransportException
    {
        this.host = host;
        this.port = Integer.valueOf(port);
        transport = new TSocket(this.host, this.port);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        client = new SparrowTransport.Client(protocol);
        active = true;
    }

    public void process(String line)
    {
        for (Map.Entry<String, Command> cmd : commands.entrySet())
        {
            Matcher matcher = Pattern.compile(cmd.getKey()).matcher(line);
            if (matcher.matches())
            {
                List<String> params = new ArrayList<String>();
                int size = matcher.groupCount();
                for (int idx = 1; idx <= size; idx++)
                {
                    params.add(matcher.group(idx));
                }
                cmd.getValue().execute(params.toArray(new String[params.size()]));
                break;
            }
        }
    }

    public void showDatabases()
    {
        try
        {
            client.show_databases().forEach(System.out::println);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    public void createDatabase(String dbname)
    {
        try
        {
            client.create_database(dbname);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    public void dropDatabase(String dbname)
    {
        try
        {
            client.drop_database(dbname);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteData(String dbname, String key)
    {
        try
        {
            client.delete_data(dbname, key);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    public void insertData(String path, String dbname, String key)
    {
        try
        {
            byte[] bytes = Files.readAllBytes(new File(path).toPath());
            DataObject dataObject = new DataObject();
            dataObject.setDbname(dbname);
            dataObject.setKey(key);
            dataObject.setData(bytes);
            client.insert_data(dataObject);
            bytes = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        active = false;
        transport.close();
    }
}
