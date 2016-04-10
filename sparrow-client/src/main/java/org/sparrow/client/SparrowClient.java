package org.sparrow.client;

import io.airlift.airline.*;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by mauricio on 4/2/16.
 */
@Command(name = "client", description = "SparrowDb client")
public class SparrowClient
{
    private static Logger logger = LoggerFactory.getLogger(SparrowClient.class);

    @Inject
    public HelpOption helpOption;

    @Option(name = {"-r", "--host"}, description = "Server ip address")
    public String host = "127.0.0.1";

    @Option(name = {"-p", "--port"}, description = "Server port number")
    public String port = "9090";

    private static CommandProcessor processor = new CommandProcessor();

    public static void main(String... args)
    {
        System.out.println("SparrowDb SPQL - v1.0.0");
        SparrowClient client = SingleCommand.singleCommand(SparrowClient.class).parse(args);

        if (client.helpOption.showHelpIfRequested())
        {
            return;
        }

        client.start();
    }

    public void start()
    {
        try
        {
            processor.connect(host, port);
        }
        catch (TTransportException e)
        {
            logger.error("Could not connect to {}:{} caused: {}", host, port, e.getCause());
        }

        this.run();
    }

    public void run()
    {
        while(processor.isActive())
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String line;

            try
            {
                System.out.print(">>");
                line = buffer.readLine().trim();
                processor.process(line);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
