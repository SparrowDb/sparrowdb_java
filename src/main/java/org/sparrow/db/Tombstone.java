package org.sparrow.db;

/**
 * Created by mauricio on 12/03/16.
 */
final class Tombstone extends DataDefinition
{

    public Tombstone(DataDefinition dataDefinition)
    {
        super(
                dataDefinition.getKey(),
                dataDefinition.getKey32(),
                0,
                dataDefinition.getOffset(),
                0,
                dataDefinition.getExtension(),
                DataState.REMOVED,
                new byte[0]
        );
    }
}
