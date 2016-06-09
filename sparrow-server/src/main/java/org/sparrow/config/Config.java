package org.sparrow.config;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mauricio on 24/12/2015.
 */
public class Config
{
    public String node_name;
    public Integer http_port;
    public String http_host;
    public Integer tcp_port;
    public String tcp_host;
    public Long max_cache_size;
    public String data_file_directory;
    public String plugin_directory;
    public Long max_datalog_size;
    public Double bloomfilter_fpp;
    public String dataholder_cron_compaction;
    public List<String> filters;

    public Config()
    {
        node_name = "localnode";
        http_port = 8081;
        http_host = "0.0.0.0";
        tcp_port = 8082;
        tcp_host = "0.0.0.0";
        max_cache_size = 80L;
        data_file_directory = "data";
        plugin_directory = "plugins";
        max_datalog_size = 1073741824L;
        bloomfilter_fpp = 0.001;
        dataholder_cron_compaction = "0 0 1 ? * TUE,FRI *";
        filters = new LinkedList<String>();
    }
}
