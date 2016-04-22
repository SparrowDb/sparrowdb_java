package org.sparrow.plugin;

/**
 * Created by mauricio on 4/21/16.
 */
public interface IFilter
{
    String getName();
    String getVersion();
    byte[] process(byte[] data) throws Exception;
}
