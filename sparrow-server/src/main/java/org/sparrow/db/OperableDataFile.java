package org.sparrow.db;

import java.io.IOException;

/**
 * Created by mauricio on 4/7/16.
 */
interface OperableDataFile
{
    void open(String filename);
    void append(DataDefinition dataDefinition) throws IOException;
    DataDefinition getData(long offset);
    void iterateDataHolder(DataDefinitionIterator dataDefinitionIterator);
    void truncate();
    boolean isEmpty();
    long getSize();
    void close();
}
