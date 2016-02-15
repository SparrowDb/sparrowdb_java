package org.sparrow.util;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mauricio on 14/02/2016.
 */
public class SigarLib
{
    private static Logger logger = LoggerFactory.getLogger(SigarLib.class);
    public static final SigarLib instance = new SigarLib();
    private boolean initialized = false;
    private long MIN_HEAP_SPACE = (long) 2 * 1024 * 1024 * 1024; // 2GB
    private Sigar sigar;

    private SigarLib()
    {
        logger.info("Initializing SIGAR library");
        try
        {
            sigar = new Sigar();
            initialized = true;
        }
        catch (UnsatisfiedLinkError linkError)
        {
            logger.info("Could not initialize SIGAR library {} ", linkError.getMessage());
        }
    }

    public boolean initialized()
    {
        return initialized;
    }

    public long getPid()
    {
        return initialized ? sigar.getPid() : -1;
    }

    private boolean hasAcceptableHeapSpace()
    {
        try
        {
            long heapSpace = sigar.getResourceLimit().getVirtualMemoryMax();
            return heapSpace >= MIN_HEAP_SPACE;
        }
        catch (SigarException sigarException)
        {
            logger.warn("Could not determine if heap space is correctly configured. Error message: {}", sigarException);
            return false;
        }
    }

    public void checkSystemResource()
    {
        if (initialized)
        {
            boolean goodHeapSpace = hasAcceptableHeapSpace();
            if (!goodHeapSpace)
            {
                logger.warn("Address heap space adequate ? {}", goodHeapSpace);
            }
            else
            {
                logger.info("Checked OS settings and found them configured for optimal performance.");
            }
        }
        else
        {
            logger.info("Sigar could not be initialized, test for checking degraded mode omitted.");
        }
    }
}
