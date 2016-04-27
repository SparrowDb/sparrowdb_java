package org.sparrow.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauricio on 4/7/16.
 */
public class DataFileManager
{
    private static Logger logger = LoggerFactory.getLogger(DataFileManager.class);

    public static List<File> getDataHolders(String path)
    {
        return FileUtils.listFiles(new File(path))
                .stream()
                .filter(x -> x.getName().startsWith("data-holder-"))
                .collect(Collectors.toList());
    }

    public static boolean isValidDataHolder(String filename)
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

    public static String getNextDataHolderName(String path)
    {
        List<File> dataHolders = DataFileManager.getDataHolders(path);
        return String.format("data-holder-%d.spw", dataHolders.size());
    }
}
