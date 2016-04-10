package org.sparrow.common;

/**
 * Created by mauricio on 12/03/16.
 */
public final class Tombstone extends DataDefinition
{

    public Tombstone(DataDefinition dataDefinition)
    {
        super(
                dataDefinition.getKey(),
                dataDefinition.getKey32(),
                0,
                dataDefinition.getOffset(),
                dataDefinition.getExtension(),
                DataState.REMOVED,
                new byte[0]
        );
    }
}
