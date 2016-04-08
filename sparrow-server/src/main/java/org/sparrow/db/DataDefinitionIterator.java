package org.sparrow.db;

/**
 * Created by mauricio on 4/6/16.
 */
@FunctionalInterface
interface DataDefinitionIterator
{
    void get(DataDefinition dataDefinition, long bytesRead);
}
