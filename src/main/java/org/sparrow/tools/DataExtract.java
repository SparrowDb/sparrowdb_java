package org.sparrow.tools;

import org.sparrow.db.DataDefinition;
import org.sparrow.serializer.DataDefinitionSerializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by mauricio on 27/12/2015.
 */
public class DataExtract
{
    public static void main(String[] args) throws IOException
    {
        ByteBuffer in = ByteBuffer.allocate(DataDefinitionSerializer.DEFAULT_SIZE);
        FileChannel fc2 = FileChannel.open(new java.io.File("data\\teste1\\Data.spw").toPath(), StandardOpenOption.READ);
        fc2.position(0);

        while(fc2.read(in)>0)
        {
            in.flip();
            DataDefinition dataDefinition = DataDefinitionSerializer.instance.deserialize(in);
            if (dataDefinition.getSize() > 0)
            {
                ByteBuffer aaa = ByteBuffer.allocate(dataDefinition.getSize());
                fc2.read(aaa);
                dataDefinition.setBuffer(aaa.array());
                byte[] byteArray = dataDefinition.getBuffer();
                Files.write(new java.io.File(dataDefinition.getKey32() + ".png").toPath(), byteArray);
                System.out.println(DataDefinitionSerializer.instance.toString(dataDefinition));
            }

            in.clear();
        }
        fc2.close();
    }
}
