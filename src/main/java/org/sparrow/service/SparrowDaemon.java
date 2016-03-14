package org.sparrow.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.db.compaction.CompactionManager;
import org.sparrow.db.compaction.DataHolderCompact;
import org.sparrow.net.NettyHttpServer;
import org.sparrow.thrift.ThriftServer;
import org.sparrow.util.SigarLib;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private static void setupServerListener()
    {
        logger.info("Starting SparrowDb Thrift...");
        thriftServer = new ThriftServer(
                DatabaseDescriptor.config.tcp_host,
                DatabaseDescriptor.config.tcp_port);

        logger.info("Starting SparrowDb Http Server...");
        nettyHttpServer = new NettyHttpServer(
                DatabaseDescriptor.config.http_host,
                DatabaseDescriptor.config.http_port);
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

    private static void logSystemInfo()
    {
        if (logger.isInfoEnabled())
        {
            try
            {
                logger.info("Hostname: {}", InetAddress.getLocalHost().getHostName());
            }
            catch (UnknownHostException e1)
            {
                logger.info("Could not resolve local host");
            }

            logger.info("JVM vendor/version: {}/{}", System.getProperty("java.vm.name"), System.getProperty("java.version"));
            logger.info("Heap size: {}/{}", Runtime.getRuntime().totalMemory(), Runtime.getRuntime().maxMemory());

            for(MemoryPoolMXBean pool: ManagementFactory.getMemoryPoolMXBeans())
                logger.info("{} {}: {}", pool.getName(), pool.getType(), pool.getPeakUsage());

            logger.info("Classpath: {}", System.getProperty("java.class.path"));

            logger.info("JVM Arguments: {}", ManagementFactory.getRuntimeMXBean().getInputArguments());
        }
    }

    public static void setupCompactionJobs()
    {
        /**
         * Data holder compaction job. Rewrite data holders of each database
         * without DataDefinition with "deleted" flag.
         */
        CompactionManager.instance.addJob(
                "dataHolderCompaction",
                DatabaseDescriptor.getDataHolderCronCompaction(),
                DataHolderCompact.class);

        CompactionManager.instance.startScheduler();
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
        logSystemInfo();

        if (SigarLib.instance.initialized())
        {
            SigarLib.instance.checkSystemResource();
        }

        writePidFile();

        logger.info("Loading configuration file...");
        DatabaseDescriptor.loadConfiguration();
        DatabaseDescriptor.checkDataDirectory();

        logger.info("Node name: {}", DatabaseDescriptor.config.node_name);

        logger.info("Loading databases...");
        SparrowDatabase.instance.loadFromDisk();

        setupServerListener();
        startServers();

        logger.info("Setting up compaction job...");
        setupCompactionJobs();

        logger.info("SparrowDb loaded !");
    }
}