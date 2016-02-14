package org.sparrow.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.net.NettyHttpServer;
import org.sparrow.thrift.ThriftServer;
import org.sparrow.util.SigarLib;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by mauricio on 24/12/2015.
 */
public class SparrowDaemon
{
    private static Logger logger = LoggerFactory.getLogger(SparrowDaemon.class);
    private static ThriftServer thriftServer;
    private static NettyHttpServer nettyHttpServer;

    private static void setup()
    {
        logger.info("Loading configuration file...");
        DatabaseDescriptor.loadConfiguration();
        DatabaseDescriptor.checkDataDirectory();

        logger.info("Node name: {}", DatabaseDescriptor.config.node_name);

        logger.info("Loading databases...");
        SparrowDatabase.instance.loadFromDisk();

        logger.info("Starting SparrowDb Thrift...");
        thriftServer = new ThriftServer(
                DatabaseDescriptor.config.tcp_host,
                DatabaseDescriptor.config.tcp_port);

        logger.info("Starting SparrowDb Http Server...");
        nettyHttpServer = new NettyHttpServer(
                DatabaseDescriptor.config.http_host,
                DatabaseDescriptor.config.http_port);

        logger.info("SparrowDb loaded !");
    }

    private static void startServers()
    {
        thriftServer.start();
        nettyHttpServer.start();
    }

    private static void writePidFile()
    {
        long pid = SigarLib.instance.getPid();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("sparrow.pid"))) {
            writer.write(String.valueOf(pid));
        }
        catch (IOException e)
        {
            logger.warn("Could not write pid file");
        }
    }

    public static void stop()
    {
        logger.info("Stoping SparrowDb...");

        if (thriftServer.isRunning())
            thriftServer.stop();

        if (nettyHttpServer.isRunning())
            nettyHttpServer.stop();
    }

    public static void main(String[] args) throws Exception
    {
        logger.info("Starting SparrowDb...");

        SigarLib.instance.checkSystemResource();
        writePidFile();
        setup();
        startServers();
    }
}