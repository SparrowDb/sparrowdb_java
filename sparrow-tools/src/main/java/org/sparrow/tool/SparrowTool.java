package org.sparrow.tool;

import io.airlift.airline.Arguments;
import io.airlift.airline.Cli;
import io.airlift.airline.Command;
import io.airlift.airline.Help;
import org.sparrow.common.DataDefinitionIterator;
import org.sparrow.common.io.DataInput;
import org.sparrow.common.io.IDataReader;
import org.sparrow.common.io.StorageReader;
import org.sparrow.common.serializer.DataDefinitionSerializer;
import org.xerial.snappy.Snappy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mauricio on 4/3/16.
 */
public class SparrowTool
{
    public static void main(String[] args)
    {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("sparrowtool")
                .withDescription("the stupid content tracker")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, ExtractCsv.class);

        Cli<Runnable> gitParser = builder.build();

        gitParser.parse(args).run();
    }

    @Command(name = "csv", description = "Export sparrow data file to csv")
    public static class ExtractCsv implements Runnable
    {
        @Arguments(description = "CSV params")
        public List<String> patterns;

        public void iterateDataHolder(File dataHolder, DataDefinitionIterator dataDefinitionProcess) throws IOException
        {
            IDataReader dataReader  = StorageReader.open(dataHolder);

            long fileSize = dataReader.length();
            long currentSize = 0;

            while (currentSize < fileSize)
            {
                byte[] dataCompressedBytes = DataInput.load(dataReader, currentSize);
                byte[] uncompressed = Snappy.uncompress(dataCompressedBytes);
                currentSize += (dataCompressedBytes.length + 4);
                dataDefinitionProcess.get(DataDefinitionSerializer.instance.deserialize(uncompressed, true), currentSize);
            }
            dataReader.close();
        }

        @Override
        public void run()
        {
            String headerLine = String.format("%s,%s,%s,%s,%s,%s",
                    "key",
                    "size",
                    "extension",
                    "timestamp",
                    "state",
                    System.getProperty("line.separator")
            );

            try
            {
                if (patterns.size() == 1)
                {
                    System.out.print(headerLine);
                    iterateDataHolder(new File(patterns.get(0)), (x, c) -> {
                        String line = String.format("%s,%s,%s,%s,%s",
                                x.getKey(),
                                x.getSize(),
                                x.getExtension(),
                                x.getTimestamp(),
                                x.getState());
                        System.out.println(line);
                    });
                }
                else
                {
                    File out = new File(patterns.get(1));
                    PrintWriter printWriter;

                    if (!out.exists()) out.createNewFile();
                    printWriter = new PrintWriter(new FileOutputStream(out, true));
                    printWriter.write(headerLine);

                    iterateDataHolder(new File(patterns.get(0)), (x, c) -> {
                        String line = String.format("%s,%s,%s,%s,%s,%s",
                                x.getKey(),
                                x.getSize(),
                                x.getExtension(),
                                x.getTimestamp(),
                                x.getState(),
                                System.getProperty("line.separator"));
                        printWriter.write(line);
                    });

                    printWriter.flush();
                    printWriter.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}