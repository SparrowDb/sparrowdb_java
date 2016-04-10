package org.sparrow.server;

import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by mauricio on 26/12/2015.
 */
public class ThriftServer implements Server
{
    private static Logger logger = LoggerFactory.getLogger(ThriftServer.class);
    private InetSocketAddress address;
    private volatile ThriftServerWorker server;

    public ThriftServer(String host, int port)
    {
        this.address = new InetSocketAddress(host, port);
    }

    @Override
    public void start()
    {
        if (server == null)
        {
            server = new ThriftServerWorker(address);
            server.start();
        }
    }

    @Override
    public void stop()
    {
        if (server != null)
        {
            server.stopServer();
            try
            {
                server.join();
            }
            catch (InterruptedException e)
            {
                logger.error("Interrupted while waiting thrift server to stop", e);
            }
            server = null;
        }
    }

    @Override
    public boolean isRunning()
    {
        return server != null;
    }

    private static class ThriftServerWorker extends Thread
    {
        private final TServer server;

        public ThriftServerWorker(InetSocketAddress address)
        {
            super();
            server = CustomTThreadPoolServer.Builder.build(address);
        }

        public void run()
        {
            server.serve();
        }

        public void stopServer()
        {
            logger.info("Stop listening to thrift clients");
            server.stop();
        }
    }
}
