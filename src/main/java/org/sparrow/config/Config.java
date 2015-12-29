package org.sparrow.config;

/**
 * Created by mauricio on 24/12/2015.
 */
public class Config
{
    public String node_name = "localnode";
    public Integer instance_type = 0;
    public Integer http_port = 8081;
    public String http_host = "0.0.0.0";
    public Integer tcp_port = 8082;
    public String tcp_host = "0.0.0.0";
    public Integer cache_capacity = 20;
    public String data_file_directory = "data";
    public Integer compressor = 0;
}
