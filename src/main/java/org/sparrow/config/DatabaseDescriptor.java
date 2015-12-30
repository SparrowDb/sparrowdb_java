package org.sparrow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.util.FileUtils;

import java.io.File;

/**
 * Created by mauricio on 24/12/2015.
 */
public class DatabaseDescriptor
{
    private static Logger logger = LoggerFactory.getLogger(DatabaseDescriptor.class);
    private static final String DEFAULT_SERVER_CONFIG_FILE = "sparrow.yml";
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

    public static void checkDataDirectory()
    {
        File dataDir = new File(getDataFilePath());
        if(!dataDir.exists())
        {
            try
            {
                FileUtils.createDirectory(getDataFilePath());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static String getDataFilePath()
    {
        return config.data_file_directory + File.separator;
    }
}
