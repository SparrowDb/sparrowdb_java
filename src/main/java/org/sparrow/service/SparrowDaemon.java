package org.sparrow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.net.NettyHttpServer;
import org.sparrow.thrift.ThriftServer;

/**
 * Created by mauricio on 24/12/2015.
 */
public class SparrowDaemon
{
    private static Logger logger = LoggerFactory.getLogger(ThriftServer.class);
    private static ThriftServer thriftServer;
    private static NettyHttpServer nettyHttpServer;

    public static void setup()
    {
        logger.info("Loading configuration file...");
        DatabaseDescriptor.loadConfiguration();
        DatabaseDescriptor.checkDataDirectory();

        logger.info("Loading databases...");
        SparrowDatabase.instance.loadFromDisk();

        logger.info("Starting SparrowDb Thrift...");
        thriftServer = new ThriftServer(
                DatabaseDescriptor.config.tcp_host,
                DatabaseDescriptor.config.tcp_port);
        thriftServer.start();

        logger.info("Starting SparrowDb Http Server...");
        nettyHttpServer = new NettyHttpServer(
                DatabaseDescriptor.config.http_host,
                DatabaseDescriptor.config.http_port);
        nettyHttpServer.start();

        logger.info("SparrowDb loaded !");
    }

    public static void stop()
    {
        logger.info("Stoping SparrowDb...");

        if (thriftServer.isRunning())
            thriftServer.stop();
    }

    public static void main(String[] args) throws Exception
    {
        logger.info("Starting SparrowDb...");
        setup();
    }
}