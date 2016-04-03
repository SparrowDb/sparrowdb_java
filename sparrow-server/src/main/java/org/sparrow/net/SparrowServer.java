package org.sparrow.net;

/**
 * Created by mauricio on 24/12/2015.
 */
public abstract class SparrowServer extends Thread
{
    @Override
    public void run()
    {
        doStart();
    }
    public abstract void doStart();
    public abstract void doStop();
}