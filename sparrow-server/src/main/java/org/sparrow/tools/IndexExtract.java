package org.sparrow.tools;

import org.sparrow.common.io.DataInput;
import org.sparrow.common.io.IDataReader;
import org.sparrow.common.io.StorageReader;
import org.sparrow.common.serializer.IndexSeralizer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mauricio on 27/12/2015.
 */
public class IndexExtract
{
    public static void main(String[] args) throws IOException
    {
        IDataReader dataReader  = StorageReader.open("data/teste1/index-0.spw");

        long fileSize = dataReader.length();
        long currentSize = 0;

        while (currentSize < fileSize)
        {
            byte[] bytes = DataInput.load(dataReader, currentSize);
            Map.Entry<Integer, Long> entry = IndexSeralizer.instance.deserialize(bytes);
            System.out.println(IndexSeralizer.instance.toString(entry));
            currentSize += (bytes.length + 4);
        }
        dataReader.close();
    }
}
