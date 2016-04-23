package org.sparrow.client;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.thrift.TException;
import org.sparrow.protocol.DataObject;
import org.sparrow.protocol.SparrowTransport;
import org.sparrow.protocol.SpqlResult;
import org.sparrow.spql.SpqlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mauricio on 4/22/16.
 */
public class SpqlParseHandler implements SpqlParser.ISpqlParseHandler
{
    private SparrowTransport.Client client;

    public SpqlParseHandler(SparrowTransport.Client client)
    {
        this.client = client;
    }

    @Override
    public void createDatabase(String dbName)
    {
        try
        {
            client.create_database(dbName);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStatement(String dbName, String keyName, String valueName)
    {
        try
        {
            client.delete_data(dbName, keyName, valueName);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dropDatabase(String dbName)
    {
        try
        {
            client.drop_database(dbName);
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void insertStatement(String dbName, ArrayList<String> tokens)
    {
        File file = new File(tokens.get(0) + "." + tokens.get(1));
        String fileExt = tokens.get(1);

        if (Strings.isNullOrEmpty(fileExt))
        {
            System.out.println("Invalid file extension");
            return;
        }

        if (file.exists())
        {
            byte[] bytes = null;

            try
            {
                bytes = Files.readAllBytes(file.toPath());
                DataObject dataObject = new DataObject();
                dataObject.setDbname(dbName);
                dataObject.setKey(tokens.get(2));
                dataObject.setData(bytes);
                dataObject.setExtension(fileExt);
                String response = client.insert_data(dataObject, tokens);
                System.out.println(response);
                bytes = null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                bytes = null;
            }
        }
        else
        {
            System.out.println("Could not load file: " + file.getAbsolutePath());
        }
    }

    public String getDateTime(long unixtime)
    {
        Date date = new Date((long)unixtime*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    @Override
    public void selectStatement(String dbName, String keyName, String valueName)
    {
        System.out.println(dbName);
        try
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            final String[] line = new String[1];
            SpqlResult result = client.spql_query(dbName, keyName, valueName);

            if (!result.isSetRows())
            {
                System.out.format("%10s%s",
                        "Empty query",
                        System.getProperty("line.separator"));
                return;
            }

            System.out.format("%10s|%8s|%10s|%24s%s",
                    "key",
                    "size",
                    "extension",
                    "timestamp",
                    System.getProperty("line.separator"));


            Iterable<List<DataObject>> pageResult = Lists.newArrayList(Iterables.partition(result.getRows(), 25));
            Iterator iter = pageResult.iterator();

            while(iter.hasNext())
            {
                List<DataObject> page = (List<DataObject>) iter.next();

                page.stream().forEach(x -> System.out.format("%10s|%8s|%10s|%20s%s",
                        x.getKey(),
                        x.getSize(),
                        x.getExtension(),
                        getDateTime(x.getTimestamp())+"+0000",
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

    @Override
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
}
