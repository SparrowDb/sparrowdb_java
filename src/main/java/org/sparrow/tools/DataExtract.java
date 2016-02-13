package org.sparrow.tools;

import org.sparrow.db.DataDefinition;
import org.sparrow.io.DataInput;
import org.sparrow.io.IDataReader;
import org.sparrow.io.StorageReader;
import org.sparrow.serializer.DataDefinitionSerializer;

import java.io.IOException;

/**
 * Created by mauricio on 27/12/2015.
 */
public class DataExtract
{
    public static void main(String[] args) throws IOException
    {
        IDataReader dataReader  = StorageReader.open("data\\teste1\\data-holder-1.spw");

        long fileSize = dataReader.length();
        long currentSize = 0;

        while (currentSize < fileSize)
        {
            byte[] bytes = DataInput.load(dataReader, currentSize);
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(bytes, true);
            System.out.println(DataDefinitionSerializer.instance.toString(dataDefinition));
            currentSize += (bytes.length + 4);
        }
        dataReader.close();
    }
}