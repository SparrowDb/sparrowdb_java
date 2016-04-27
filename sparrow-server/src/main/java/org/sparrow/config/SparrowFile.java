package org.sparrow.config;

/**
 * Created by mauricio on 4/26/16.
 */
public enum SparrowFile
{
    SPARROW("sparrow.xml"),
    DATABASE("database.xml");

    private final String filename;

    SparrowFile(final String filename)
    {
        this.filename = filename;
    }

    @Override
    public String toString()
    {
        return filename;
    }
}
