package org.sparrow.client;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.sparrow.protocol.SparrowTransport;
import org.sparrow.spql.SpqlLexer;
import org.sparrow.spql.SpqlParser;

/**
 * Created by mauricio on 4/2/16.
 */
public class CommandProcessor
{
    private String host;
    private int port;
    private boolean active;

    private TTransport transport;
    private TProtocol protocol;
    private SparrowTransport.Client client;
    private SpqlParseHandler spqlParseHandler;

    private SpqlLexer lexer = new SpqlLexer(new ANTLRInputStream());
    private SpqlParser parser = new SpqlParser(new CommonTokenStream(lexer));

    public CommandProcessor()
    {

    }

    public boolean isActive()
    {
        return active;
    }

    public void connect(String host, String port) throws TTransportException
    {
        this.host = host;
        this.port = Integer.valueOf(port);
        transport = new TSocket(this.host, this.port);
        transport.open();
        protocol = new TBinaryProtocol(transport);
        client = new SparrowTransport.Client(protocol);
        active = true;
        spqlParseHandler = new SpqlParseHandler(client);
    }

    public void process(String line)
    {
        lexer = new SpqlLexer(new ANTLRInputStream(line));
        parser = new SpqlParser(new CommonTokenStream(lexer));
        parser.setHandler(spqlParseHandler);

        ParseTree tree = parser.parse(); // begin parsing at init rule
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
    }

    public void close()
    {
        active = false;
        transport.close();
    }
}
