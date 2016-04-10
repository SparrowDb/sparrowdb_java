package org.sparrow.common;

/**
 * Created by mauricio on 4/6/16.
 */
@FunctionalInterface
public interface DataDefinitionIterator
{
    void get(DataDefinition dataDefinition, long bytesRead);
}
