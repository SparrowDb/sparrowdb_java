package org.sparrow.client;

import io.airlift.airline.*;
import org.apache.thrift.transport.TTransportException;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by mauricio on 4/2/16.
 */
@Command(name = "client", description = "network test utility")
public class SparrowClient
{
    @Inject
    public HelpOption helpOption;

    @Option(type = OptionType.GLOBAL, name = {"-h", "--host"}, description = "Node hostname or ip address")
    private static String host = "127.0.0.1";

    @Option(type = OptionType.GLOBAL, name = {"-p", "--port"}, description = "Remote jmx agent port number")
    private static String port = "9090";

    private static CommandProcessor processor = new CommandProcessor();

    public static void main(String... args)
    {
        System.out.println("SparrowDb SPQL - v1.0.0");
        SparrowClient client = SingleCommand.singleCommand(SparrowClient.class).parse(args);

        if (client.helpOption.showHelpIfRequested())
        {
            return;
        }

        try
        {
            processor.connect(host, port);
        }
        catch (TTransportException e)
        {
            e.printStackTrace();
        }

        client.run();
    }

    public void run()
    {
        while(processor.isActive())
        {
            BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
            String line;

            try
            {
                line = buffer.readLine().trim();
                processor.process(line);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
