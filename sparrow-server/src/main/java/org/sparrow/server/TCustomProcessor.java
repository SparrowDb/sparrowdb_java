package org.sparrow.server;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.sparrow.protocol.SparrowTransport;

import java.net.InetAddress;

/**
 * Created by mauricio on 4/10/16.
 */
public class TCustomProcessor implements TProcessor
{
    private TProcessor processor;

    public TCustomProcessor()
    {
    }

    @Override
    public boolean process(TProtocol in, TProtocol out) throws TException
    {
        TTransport t = in.getTransport();
        InetAddress address = (t instanceof TSocket) ? ((TSocket)t).getSocket().getInetAddress() : null;
        processor = new SparrowTransport.Processor(new TServerTransportHandler(address));
        return processor.process(in, out);
    }
}
