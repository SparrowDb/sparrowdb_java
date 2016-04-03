package org.sparrow.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.sparrow.thrift.SparrowTransport;

import java.net.InetSocketAddress;

/**
 * Created by mauricio on 26/12/2015.
 */
public class CustomTThreadPoolServer extends TThreadPoolServer
{
    public CustomTThreadPoolServer(Args args)
    {
        super(args);
    }

    public static class Builder
    {
        public static TServer build(InetSocketAddress address, SparrowTransport.Iface handler)
        {
            TServerSocket serverTransport;
            TThreadPoolServer.Args args;
            SparrowTransport.Processor processor;

            try
            {
                processor =  new SparrowTransport.Processor(handler);
                serverTransport = new TServerSocket(address);
                args = new TThreadPoolServer.Args(serverTransport);
                args.processor(processor);
                return new TThreadPoolServer(args);

            } catch (TTransportException e)
            {
                e.printStackTrace();
            }
            return  null;
        }
    }
}
