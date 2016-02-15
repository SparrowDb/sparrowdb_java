package org.sparrow.config;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mauricio on 24/12/2015.
 */
public class Config
{
    public String node_name = "localnode";
    public Integer http_port = 8081;
    public String http_host = "0.0.0.0";
    public Integer tcp_port = 8082;
    public String tcp_host = "0.0.0.0";
    public Long cache_capacity = 31457280L;
    public String data_file_directory = "data";
    public Long max_datalog_size = 1073741824L;
    public List<String> nodes = new LinkedList<String>();
    public Double bloomfilter_fpp = 0.001;
}
