package org.sparrow.tools;

import org.sparrow.db.DataDefinition;
import org.sparrow.io.DataInput;
import org.sparrow.io.IDataReader;
import org.sparrow.io.StorageReader;
import org.sparrow.serializer.DataDefinitionSerializer;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by mauricio on 27/12/2015.
 */
public class DataExtract
{
    public static void main(String[] args) throws IOException
    {
        IDataReader dataReader  = StorageReader.open("data/teste1/datalog.spw");

        long fileSize = dataReader.length();
        long currentSize = 0;

        while (currentSize < fileSize)
        {
            byte[] dataCompressedBytes = DataInput.load(dataReader, currentSize);
            byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(uncompressed, true);
            System.out.println(DataDefinitionSerializer.instance.toString(dataDefinition));
            currentSize += (dataCompressedBytes.length + 4);
        }

        System.out.println(currentSize);
        dataReader.close();
    }
}