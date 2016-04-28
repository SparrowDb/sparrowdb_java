package spql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.spql.SpqlLexer;
import org.sparrow.spql.SpqlParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mauricio on 4/27/16.
 */
public class SpqlLanguageTest
{
    private static Logger logger = LoggerFactory.getLogger(SpqlLanguageTest.class);
    private SpqlLexer lexer = new SpqlLexer(new ANTLRInputStream());
    private SpqlParser parser = new SpqlParser(new CommonTokenStream(lexer));

    public void executeCommand(String command)
    {
        lexer = new SpqlLexer(new ANTLRInputStream(command));
        parser = new SpqlParser(new CommonTokenStream(lexer));
        parser.setHandler(new ParseHandlerTest());
        parser.parse();
    }

    @Test
    public void createDatabaseTest()
    {
        String command = "create database database1";
        executeCommand(command);
    }

    @Test
    public void createDatabaseArgsTest()
    {
        String command = "create database database2 { key1 : value1, key2 : value2 }";
        executeCommand(command);
    }

    public class ParseHandlerTest implements SpqlParser.ISpqlParseHandler
    {

        @Override
        public void createDatabase(String dbName, HashMap<String, String> tokens)
        {
            logger.info("createDatabase: {}", dbName);
            for (HashMap.Entry<String, String> e : tokens.entrySet())
            {
                logger.info("Entry: {} = {}", e.getKey(), e.getValue());
            }
        }

        @Override
        public void deleteStatement(String dbName, String keyName, String valueName)
        {

        }

        @Override
        public void dropDatabase(String dbName)
        {

        }

        @Override
        public void insertStatement(String dbName, ArrayList<String> tokens)
        {

        }

        @Override
        public void selectStatement(String dbName, String keyName, String valueName)
        {

        }

        @Override
        public void showDatabases()
        {

        }
    }
}
