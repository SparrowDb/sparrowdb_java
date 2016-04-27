package org.sparrow.service;


import io.airlift.airline.Command;
import io.airlift.airline.Option;
import io.airlift.airline.SingleCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.SigarLib;
import org.sparrow.config.DatabaseDescriptor;
import org.sparrow.config.SparrowFile;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.db.compaction.DataHolderCompact;
import org.sparrow.db.compaction.ScheduleManager;
import org.sparrow.net.NettyHttpServer;
import org.sparrow.plugin.FilterManager;
import org.sparrow.server.ThriftServer;

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
    public static StartUpParams startUpParams = new StartUpParams();

    @Command(name = "sparrow")
    public static class StartUpParams {
        @Option(name = {"-c", "--conf"}, description = "Configuration path")
        public String configurationPath = "conf";
    }

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
        ScheduleManager.instance.addJob(
                "dataHolderCompaction",
                DatabaseDescriptor.getDataHolderCronCompaction(),
                DataHolderCompact.class);

        ScheduleManager.instance.startScheduler();
    }

    public static void checkDirectories()
    {
        DatabaseDescriptor.checkAndCreateDirectory(DatabaseDescriptor.getDataFilePath());
        DatabaseDescriptor.checkAndCreateDirectory(DatabaseDescriptor.config.plugin_directory);
    }

    public static void loadConfiguration()
    {
        DatabaseDescriptor.loadConfiguration(SparrowFile.SPARROW);
        DatabaseDescriptor.loadConfiguration(SparrowFile.DATABASE);
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

        startUpParams = SingleCommand.singleCommand(StartUpParams.class).parse(args);
        logSystemInfo();

        if (SigarLib.instance.initialized())
        {
            SigarLib.instance.checkSystemResource();
        }

        writePidFile();

        logger.info("Loading configuration file...");
        loadConfiguration();
        checkDirectories();

        logger.info("Node name: {}", DatabaseDescriptor.config.node_name);

        logger.info("Loading databases...");
        SparrowDatabase.instance.loadFromDisk();

        setupServerListener();

        logger.info("Setting up compaction job...");
        setupCompactionJobs();

        // Load Sparrow Filters from plugin folder
        FilterManager.instance.loadFilters();

        logger.info("SparrowDb loaded !");
    }
}