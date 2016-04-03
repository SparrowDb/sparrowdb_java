package org.sparrow.spql;

import org.slf4j.LoggerFactory;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.rpc.SpqlResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mauricio on 25/12/2015.
 */
public class SpqlParser
{
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SpqlParser.class);

    public static SpqlResult parseAndProcess(String query)
    {
        String SPQL_QUERY_SELECT = "^select\\s*from\\s*([A-Za-z0-9]{3,20})\\s*(.*)?;$";
        String SPQL_QUERY_WHERE = "^\\s*where\\s*([A-Za-z0-9]{3,20})\\s*\\=\\s*([A-Za-z0-9]{3,20})\\s*(.*)?$";
        String SPQL_QUERY_START_UNTIL = "^\\s*start\\s*([0-9]{1,})\\s*until\\s*([0-9)]{1,})$";

        Matcher query_select_matcher = Pattern.compile(SPQL_QUERY_SELECT).matcher(query);
        Matcher query_start_until_matcher;
        Matcher query_where_matcher;

        if (query_select_matcher.matches())
        {
            logger.debug("Processing query: {} ", query);

            query_where_matcher = Pattern.compile(SPQL_QUERY_WHERE).matcher(query_select_matcher.group(2));

            String dbname = query_select_matcher.group(1);
            if (!SparrowDatabase.instance.databaseExists(dbname))
                return null;

            if (query_where_matcher.matches())
            {
                if (query_where_matcher.group(1).equals("key"))
                {
                    return SpqlProcessor.queryDataWhereKey(dbname, query_where_matcher.group(2));
                }
            }
            else
            {
                query_start_until_matcher = Pattern.compile(SPQL_QUERY_START_UNTIL).matcher(query_select_matcher.group(2));
                if (query_start_until_matcher.matches())
                {
                    return null;
                }
                else
                {
                    return SpqlProcessor.queryDataAll(dbname);
                }
            }
        }
        else
        {
            logger.debug("Invalid query: {}", query);
        }

        return null;
    }
}
