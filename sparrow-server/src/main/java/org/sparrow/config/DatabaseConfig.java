package org.sparrow.config;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauricio on 4/25/16.
 */
@JacksonXmlRootElement(localName = "databases")
public class DatabaseConfig
{
    @JacksonXmlProperty(localName = "database")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Descriptor> databases = new ArrayList<>();

    public static class Descriptor
    {
        public String name;
        public Long max_datalog_size;
        public Long max_cache_size;
        public Double bloomfilter_fpp;
        public String dataholder_cron_compaction;
        public String path;
    }
}
