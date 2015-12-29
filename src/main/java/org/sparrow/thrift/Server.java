package org.sparrow.thrift;

/**
 * Created by mauricio on 26/12/2015.
 */
public interface Server
{
    void start();
    void stop();
    boolean isRunning();
}