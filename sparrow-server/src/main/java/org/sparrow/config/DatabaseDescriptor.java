package org.sparrow.config;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;
import org.sparrow.service.SparrowDaemon;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 24/12/2015.
 */
public class DatabaseDescriptor
{
    private static Logger logger = LoggerFactory.getLogger(DatabaseDescriptor.class);
    public static volatile Config config;
    public static volatile DatabaseConfig database;

    static
    {
        config = new Config();
        database = new DatabaseConfig();
    }

    public static void saveConfigurationFile(SparrowFile cFile)
    {
        try
        {
            File cfgFile = new File(FileUtils.joinPath(SparrowDaemon.startUpParams.configurationPath, cFile.toString()));
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object object = config;

            switch (cFile)
            {
                case SPARROW: object = config; break;
                case DATABASE: object = database; break;
            }

            xmlMapper.writeValue(cfgFile, object);
        }
        catch (IOException e)
        {
            logger.error("Error trying to save configuration file " + cFile);
            System.exit(1);
        }
    }

    public static void loadConfiguration(SparrowFile cFile)
    {
        try
        {
            File cfgFile = new File(FileUtils.joinPath(SparrowDaemon.startUpParams.configurationPath, cFile.toString()));
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            switch (cFile)
            {
                case SPARROW: config = xmlMapper.readValue(cfgFile, Config.class); break;
                case DATABASE: database = xmlMapper.readValue(cfgFile, DatabaseConfig.class); break;
            }
        }
        catch (IOException e)
        {
            //logger.error("Error trying to load configuration file " + cFile);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void checkAndCreateDirectory(String path)
    {
        File dataDir = new File(path);
        if(!dataDir.exists())
        {
            try
            {
                FileUtils.createDirectory(path);
            }
            catch (Exception e)
            {
                logger.error("Could not create directory: {}", e.getMessage());
            }
        }
    }

    public static String getDataFilePath()
    {
        return config.data_file_directory + System.getProperty("file.separator");
    }

    public static String getDataHolderCronCompaction()
    {
        return config.dataholder_cron_compaction;
    }

    public static List<File> filterDatabasesDir(File dataDir)
    {
        return Arrays.stream(FileUtils.listSubdirectories(dataDir))
                .filter(x->!x.getName().equals(config.plugin_directory))
                .collect(Collectors.toList());
    }

    public static DatabaseConfig.Descriptor getDatabaseConfigByName(String dbname)
    {
        return database.databases.stream()
                .filter(x -> x.name.equals(dbname))
                .findFirst()
                .get();
    }
}
