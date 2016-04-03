package org.sparrow.spql;

import org.sparrow.db.DataDefinition;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.rpc.DataObject;
import org.sparrow.rpc.SpqlResult;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mauricio on 3/21/16.
 */
public class SpqlProcessor {
    public static SpqlResult mapToSpqlResult(String dbname, Set<DataDefinition> data)
    {
        SpqlResult result = new SpqlResult();
        for(DataDefinition dataDefinition : data)
        {
            DataObject dataObject = new DataObject();
            dataObject.setDbname(dbname);
            dataObject.setSize(dataDefinition.getSize());
            dataObject.setTimestamp(dataDefinition.getTimestamp());
            dataObject.setKey(dataDefinition.getKey());
            dataObject.setState(dataDefinition.getState().ordinal());
            result.addToRows(dataObject);
        }
        result.count = data.size();
        return  result;
    }

    public static SpqlResult queryDataWhereKey(String dbname, String value)
    {
        DataDefinition dataDefinition = SparrowDatabase.instance.getObjectByKey(dbname, value);
        SpqlResult result = new SpqlResult();
        if (dataDefinition!=null)
        {
            result = SpqlProcessor.mapToSpqlResult(dbname, new HashSet<DataDefinition>(){{
                add(dataDefinition);
            }});
        }
        return result;
    }

    public static SpqlResult queryDataAll(String dbname)
    {
        Set result = new LinkedHashSet<>();
        SparrowDatabase.instance.getDatabase(dbname).getDataHolders().forEach(x -> result.addAll(x.fetchAll()));
        return mapToSpqlResult(dbname, result);
    }
}