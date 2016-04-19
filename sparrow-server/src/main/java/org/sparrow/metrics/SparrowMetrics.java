package org.sparrow.metrics;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * Created by mauricio on 4/16/16.
 */
public class SparrowMetrics
{
    public static final SparrowMetrics instance = new SparrowMetrics();
    private final MetricRegistry metrics = new MetricRegistry();
    private final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();

    private SparrowMetrics()
    {
        reporter.start();
    }

    public MetricRegistry getMetrics()
    {
        return metrics;
    }
}
