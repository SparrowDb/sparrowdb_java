package org.sparrow.backup;

import org.sparrow.common.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by mauricio on 6/10/16.
 */
public class SparrowBackup
{
    public static final SparrowBackup instance = new SparrowBackup();

    private SparrowBackup()
    {

    }

    public void backupFolder(String sourcePath, String destPath) throws Exception
    {
        List<File> files = FileUtils.listFiles(new File(sourcePath));

        FileOutputStream fos = new FileOutputStream(destPath);
        ZipOutputStream zos = new ZipOutputStream(fos);
        byte[] buffer = new byte[1024];

        for (File sourceFile : files)
        {
            FileInputStream fis = new FileInputStream(sourceFile);
            zos.putNextEntry(new ZipEntry(sourceFile.getName()));

            int len;
            while ((len = fis.read(buffer)) > 0)
            {
                zos.write(buffer, 0, len);
            }

            zos.closeEntry();
            fis.close();
        }

        zos.close();
    }
}
