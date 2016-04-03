package org.sparrow.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.rpc.SparrowTransport;

/**
 * Created by mauricio on 17/01/2016.
 */
public class Client
{
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    private TTransport transport;
    private TProtocol protocol;
    private SparrowTransport.Client client;
    private String host;
    private int port;

    public Client(String host, int port)
    {
        this.host = host;
        this.port = port;
        configure();
    }

    public void configure()
    {
        try
        {
            transport = new TSocket(host, port);
            transport.open();
            protocol = new TBinaryProtocol(transport);
            client = new SparrowTransport.Client(protocol);
        }
        catch (TTransportException e)
        {
           logger.warn("Could not configure client socket: {}", e.getMessage());
        }
    }

    public SparrowTransport.Client getExecutor()
    {
        return client;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public void close()
    {
        if (client != null)
            transport.close();
    }
}
