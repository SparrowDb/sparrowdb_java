package org.sparrow.spql;

import org.sparrow.db.Database;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.thrift.SpqlResult;
import org.sparrow.util.MurmurHash;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mauricio on 25/12/2015.
 */
public class SpqlParser
{
    public static SpqlResult parseAndProcess(String sql)
    {
        String CPQL_QUERY_SELECT = "^select\\s*from\\s*([A-Za-z0-9]{3,20})\\s*(.*)?;$";
        String CPQL_QUERY_WHERE = "^\\s*where\\s*([A-Za-z0-9]{3,20})\\s*\\=\\s*([A-Za-z0-9]{3,20})\\s*(.*)?$";
        String CPQL_QUERY_START_UNTIL = "^\\s*start\\s*([0-9]{1,})\\s*until\\s*([0-9)]{1,})$";

        Matcher query_select_matcher = Pattern.compile(CPQL_QUERY_SELECT).matcher(sql);
        Matcher query_start_until_matcher;
        Matcher query_where_matcher;

        if (query_select_matcher.matches())
        {
            query_where_matcher = Pattern.compile(CPQL_QUERY_WHERE).matcher(query_select_matcher.group(2));

            Database database = SparrowDatabase.instance.getDatabase(query_select_matcher.group(1));

            if (database == null)
                return null;

            if (query_where_matcher.matches())
            {
                if (query_where_matcher.group(1).equals("key"))
                {
                    int hash32key = MurmurHash.hash32(ByteBuffer.wrap(query_where_matcher.group(2).getBytes()), 0, query_where_matcher.group(2).length(), 0);
                    return database.query_data_where_key(hash32key);
                }
                else if (query_where_matcher.group(1).equals("timestamp"))
                {
                    return database.query_data_where_timestamp(Integer.parseInt(query_where_matcher.group(2)));
                }
            }
            else
            {
                query_start_until_matcher = Pattern.compile(CPQL_QUERY_START_UNTIL).matcher(query_select_matcher.group(2));
                if (query_start_until_matcher.matches())
                {
                    System.out.println("busca todos com start e until");
                }
                else
                {
                    return database.query_data_all();
                }
            }
        }
        else
        {
            System.out.println("Query invalida");
        }

        return null;
    }
}
