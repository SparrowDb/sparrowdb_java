package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.util.FileUtils;
import org.sparrow.util.SPUtils;

import java.io.File;
import java.util.Set;

/**
 * Created by mauricio on 4/7/16.
 */
public class DataHolderFileManager
{
    private static Logger logger = LoggerFactory.getLogger(DataHolderFileManager.class);

    private static boolean isValidDataHolder(String filename)
    {
        if (!FileUtils.fileExists(filename.replace("data-holder", "index")))
        {
            logger.warn("Index file does not exists for {}", filename);
            return false;
        }
        if (!FileUtils.fileExists(filename.replace("data-holder", "bloomfilter")))
        {
            logger.warn("Bloom filter file does not exists for {}", filename);
            return false;
        }
        return true;
    }

    public static void loadDataHolders(Set<DataHolder> collection, String dbname)
    {
        int filecount = 0;
        String filename;
        File file;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            file = new File(filename);
            if (file.exists())
            {
                if (isValidDataHolder(filename))
                {
                    collection.add(DataHolder.open(filename));
                }
            } else
            {
                break;
            }
            filecount++;
        }
    }

    public static String getLastFilename(String dbname)
    {
        int filecount = 0;
        String filename;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            File file = new File(filename);
            if (file.exists())
            {
                int next = filecount + 1;
                File nextFile = new File(SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", next)));
                if (!nextFile.exists() && file.exists())
                {
                    return file.getName();
                }
            } else
            {
                return file.getName();
            }
            filecount++;
        }
    }

    public static String getNextFilename(String dbname)
    {
        int filecount = 0;
        String filename;

        while (true)
        {
            filename = SPUtils.getDbPath(dbname, String.format("data-holder-%d.spw", filecount));
            File file = new File(filename);
            if (!file.exists())
            {
                return file.getName();
            }
            filecount++;
        }
    }
}
