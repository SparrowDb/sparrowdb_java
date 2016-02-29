package org.sparrow.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mauricio on 24/12/2015.
 */
public class CacheFactory
{
    private static Logger logger = LoggerFactory.getLogger(CacheProvider.class);

    public static ICache newCache(final long capacity)
    {
        logger.info("Creating cache provider with capacity: {}", capacity);
        return new CacheProvider<>(capacity);
    }
}
