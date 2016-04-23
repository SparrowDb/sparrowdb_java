package org.sparrow.service;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.protocol.DataObject;
import org.sparrow.server.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by mauricio on 18/01/2016.
 */
public class PublisherService
{
    private static Logger logger = LoggerFactory.getLogger(PublisherService.class);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private volatile BlockingDeque<Executor> queue = new LinkedBlockingDeque<>();
    private volatile Set<Client> subscribers = new HashSet<>();
    private volatile boolean active;

    public enum Type
    {
        INSERT, DELETE, QUERY, CREATE, DROP
    }

    public PublisherService()
    {

    }

    public void addSubscriber(String host, int port)
    {
        subscribers.add(new Client(host, port));
    }

    public <T> void publish(Type type, T a)
    {
        queue.add(new Executor(type, a));
    }

    public void consume()
    {
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        try
        {
            Executor ex = queue.take();
            for (Client c : subscribers)
            {
                ex.attachClient(c);
                callables.add(ex);
            }
            executorService.invokeAll(callables);
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            executorService.shutdown();
            callables.clear();
        }
    }

    private class Executor<T> implements Callable
    {
        private Type type;
        private T args;
        private Client client;

        public Executor(Type type, T args)
        {
            this.type = type;
            this.args = args;
        }

        public void attachClient(Client client)
        {
            this.client = client;
        }

        @Override
        public String call() throws Exception
        {
            String response = null;
            try
            {
                switch (type)
                {
                    //case CREATE: response = client.getExecutor().create_database((String)args); break;
                    //case INSERT: response = client.getExecutor().insert_data((DataObject)args); break;
                }
            } catch (Exception e)
            {
                logger.warn("Could not process command on {}:{}", client.getHost(), client.getPort());
            }
            return response;
        }
    }

    public boolean isActive()
    {
        return active;
    }
}
