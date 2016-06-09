package org.sparrow.spql;

import org.sparrow.common.DataDefinition;
import org.sparrow.db.Database;
import org.sparrow.db.SparrowDatabase;
import org.sparrow.protocol.DataObject;
import org.sparrow.protocol.SpqlResult;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
            dataObject.setExtension(dataDefinition.getExtension());
            dataObject.setState(dataDefinition.getState().ordinal());
            result.addToRows(dataObject);
        }
        result.count = data.size();
        return  result;
    }

    public static Predicate<DataDefinition> filterByActive()
    {
        return x -> x.getState() == DataDefinition.DataState.ACTIVE;
    }

    public static Predicate<DataDefinition> filterByKey(String key)
    {
        return x -> x.getKey().equals(key);
    }

    public static SpqlResult queryDataByFilter(String dbname, Predicate<DataDefinition> predicate)
    {
        Set result = new LinkedHashSet<>();

        result.addAll(SparrowDatabase.instance.getDatabase(dbname)
                .getDataLog()
                .fetchAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList()));

        SparrowDatabase.instance.getDatabase(dbname)
                .getDataHolders()
                .stream()
                .forEach(x ->
                        result.addAll(x.fetchAll().stream().filter(predicate).collect(Collectors.toList()))
                );

        return mapToSpqlResult(dbname, result);
    }

    public static SpqlResult queryDataAll(String dbname)
    {
        return queryDataByFilter(dbname, filterByActive());
    }

    public static SpqlResult queryDataWhereKey(String dbname, String value)
    {
        Database database  = SparrowDatabase.instance.getDatabase(dbname);
        DataDefinition dataDefinition = database.getDataWithImageByKey32(value);
        SpqlResult result = new SpqlResult();
        if (dataDefinition!=null)
        {
            result = SpqlProcessor.mapToSpqlResult(dbname, new HashSet<DataDefinition>(){{
                add(dataDefinition);
            }});
        }
        return result;
    }

    public static long queryCount(String dbname)
    {
        return SparrowDatabase.instance.getDatabase(dbname).countData();
    }
}
