package org.sparrow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 04/10/2015.
 */
public class FileUtils
{
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final double KB = 1024d;
    private static final double MB = 1024*1024d;
    private static final double GB = 1024*1024*1024d;
    private static final double TB = 1024*1024*1024*1024d;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static File createTempFile(String prefix, String suffix, File directory) throws Exception
    {
        return File.createTempFile(prefix, suffix, directory);
    }

    public static File createTempFile(String prefix, String suffix) throws Exception
    {
        return createTempFile(prefix, suffix, new File(System.getProperty("java.io.tmpdir")));
    }

    public static String getCanonicalPath(String filename) throws Exception
    {
        return new File(filename).getCanonicalPath();
    }

    public static String getCanonicalPath(File file) throws Exception
    {
        return file.getCanonicalPath();
    }

    public static void createDirectory(String directory) throws Exception
    {
        createDirectory(new File(directory));
    }

    public static void createDirectory(File directory) throws Exception
    {
        if (!directory.exists())
        {
            if (!directory.mkdirs())
                throw new Exception("Failed to mkdirs " + directory);
        }
    }

    public static boolean delete(String file)
    {
        File f = new File(file);
        return f.delete();
    }

    public static void delete(File... files)
    {
        for (File file : files)
        {
            file.delete();
        }
    }

    public static String stringifyFileSize(double value)
    {
        double d;
        if ( value >= TB )
        {
            d = value / TB;
            String val = decimalFormat.format(d);
            return val + " TB";
        }
        else if ( value >= GB )
        {
            d = value / GB;
            String val = decimalFormat.format(d);
            return val + " GB";
        }
        else if ( value >= MB )
        {
            d = value / MB;
            String val = decimalFormat.format(d);
            return val + " MB";
        }
        else if ( value >= KB )
        {
            d = value / KB;
            String val = decimalFormat.format(d);
            return val + " KB";
        }
        else
        {
            String val = decimalFormat.format(value);
            return val + " bytes";
        }
    }

    public static File[] listSubdirectories(File directory)
    {
        File[] directories = directory.listFiles();
        List<File> resultDirectories = new ArrayList<>();

        assert directories != null;
        for (File file : directories)
        {
            if (file.isDirectory())
                resultDirectories.add(file);
        }

        return resultDirectories.toArray(new File[resultDirectories.size()]);
    }

    public static long folderSize(File directory)
    {
        long length = 0;
        for (File file : directory.listFiles())
        {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }
}
