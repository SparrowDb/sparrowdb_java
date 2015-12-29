package org.sparrow.tools;

import org.sparrow.db.Index;
import org.sparrow.serializer.IndexSerializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * Created by mauricio on 27/12/2015.
 */
public class IndexExtract
{
    public static void main(String[] args) throws IOException
    {
        ByteBuffer in = ByteBuffer.allocate(IndexSerializer.DEFAULT_SIZE);
        FileChannel fc2 = FileChannel.open(new java.io.File("data\\teste1\\Index.spw").toPath(), StandardOpenOption.READ);
        fc2.position(0);

        while (fc2.read(in) > 0)
        {
            in.flip();
            Index index = IndexSerializer.instance.deserialize(in);
            System.out.println(IndexSerializer.instance.toString(index));
            in.clear();
        }
        fc2.close();
    }
}
