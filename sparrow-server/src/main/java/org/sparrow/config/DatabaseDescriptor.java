package org.sparrow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 24/12/2015.
 */
public class DatabaseDescriptor
{
    private static Logger logger = LoggerFactory.getLogger(DatabaseDescriptor.class);
    private static final String DEFAULT_SERVER_CONFIG_FILE = "sparrow.yml";
    public static final String DEFAULT_PLUGIN_DIR = "plugin";
    public static Config config;

    static {
        config = new Config();
    }

    public static void loadConfiguration()
    {
        try
        {
            File cfgFile = new File(DEFAULT_SERVER_CONFIG_FILE);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(cfgFile, Config.class);
        }
        catch (Exception e)
        {
            logger.error("Error trying to load configuration file " + DEFAULT_SERVER_CONFIG_FILE);
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

    public static void checkDataDirectory()
    {
        checkAndCreateDirectory(getDataFilePath());
    }

    public static void checkPluginDirectory()
    {
        checkAndCreateDirectory(DEFAULT_PLUGIN_DIR);
    }

    public static String getDataFilePath()
    {
        return config.data_file_directory + System.getProperty("file.separator");
    }

    public static String getDataFilePath(String ... paths)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(config.data_file_directory);
        sb.append(System.getProperty("file.separator"));
        for (int i = 0; i < paths.length; i++)
        {
            sb.append(paths[i]);

            if (i != paths.length)
                sb.append(System.getProperty("file.separator"));
        }
        return sb.toString();
    }

    public static String getDataHolderCronCompaction()
    {
        return config.dataholder_cron_compaction;
    }

    public static List<File> filterDatabasesDir(File dataDir)
    {
        return Arrays.stream(FileUtils.listSubdirectories(dataDir))
                .filter(x->!x.getName().equals(DEFAULT_PLUGIN_DIR))
                .collect(Collectors.toList());
    }
}
