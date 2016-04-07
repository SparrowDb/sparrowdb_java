package org.sparrow.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.sparrow.rpc.DataObject;
import org.sparrow.rpc.SparrowTransport;
import org.sparrow.rpc.SpqlResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
        void execute(String query, String[] args);
    }

    public CommandProcessor()
    {
        commands.put("^show databases\\s*;$", (q, args) -> showDatabases());
        commands.put("^create database ([A-Za-z0-9]{3,20})\\s*;$", (q, args) -> createDatabase(args[0]));
        commands.put("^drop database ([A-Za-z0-9]{3,20})\\s*;$", (q, args) -> dropDatabase(args[0]));
        commands.put("^delete data ([A-Za-z0-9]{3,20}).([A-Za-z0-9]{3,20})\\s*;$", (q, args) -> deleteData(args[0], args[1]));
        commands.put("^insert into ([A-Za-z0-9]{3,20})\\s*\\(\\s*(.{1,}.{4}\\s*),\\s*([A-Za-z0-9]{3,20}\\s*)\\)\\s*;$", (q, args) -> insertData(args[1], args[0], args[2]));

        /*
        * Select all from database
        * - select from database;
        * */
        commands.put("^select\\s*from\\s*([A-Za-z0-9]{3,20});$", (q, args) -> spqlQuery(q));

        /*
        * Select count all from database
        * - select count from database;
        * */
        commands.put("^select\\s*count\\s*from\\s*([A-Za-z0-9]{3,20});$", (q, args) -> spqlQuery(q));

        /*
        * Select from database by key
        * - select from database where key = myKey;
        * */
        commands.put("^select\\s*from\\s*([A-Za-z0-9]{3,20})\\s*where\\s*key\\s*=\\s*([A-Za-z0-9]{3,20});$", (q, args) -> spqlQuery(q));

        commands.put("^exit\\s*$", (q, args) -> close());
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
                cmd.getValue().execute(line, params.toArray(new String[params.size()]));
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

    public void spqlQuery(String query)
    {
        try
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            final String[] line = new String[1];
            SpqlResult result = client.spql_query(query);

            Iterable<List<DataObject>> pageResult = Lists.newArrayList(Iterables.partition(result.getRows(), 25));
            Iterator iter = pageResult.iterator();

            System.out.format("%10s|%8s|%10s|%15s|%7s|%s",
                    "key",
                    "size",
                    "extension",
                    "timestamp",
                    "state",
                    System.getProperty("line.separator"));

            while(iter.hasNext())
            {
                List<DataObject> page = (List<DataObject>) iter.next();

                page.stream().forEach(x -> System.out.format("%10s|%8s|%10s|%15s|%7s|%s",
                        x.getKey(),
                        x.getSize(),
                        x.getExtension(),
                        x.getTimestamp(),
                        x.getState(),
                        System.getProperty("line.separator")));
                try
                {
                    line[0] = buffer.readLine();
                    if (line[0].trim().equals("."))
                    {
                        break;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
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
