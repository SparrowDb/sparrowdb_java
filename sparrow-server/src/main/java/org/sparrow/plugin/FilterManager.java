package org.sparrow.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrow.common.util.FileUtils;
import org.sparrow.config.DatabaseDescriptor;

import java.io.File;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * Created by mauricio on 4/20/16.
 */
public class FilterManager
{
    private static Logger logger = LoggerFactory.getLogger(FilterManager.class);

    public static final FilterManager instance = new FilterManager();
    private HashMap<String, IFilter> filters = new HashMap<>();

    private URLClassLoader getClassLoader(File jarFile) throws MalformedURLException
    {
        return new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, System.class.getClassLoader());
    }

    private IFilter filterProxy(Class clazz, Object o)
    {
        return (IFilter) Proxy.newProxyInstance(IFilter.class.getClassLoader(),
                new Class[]{IFilter.class},
                (proxy, method, args) -> clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(o, args)
        );
    }

    private void loadFilter(String filter)
    {
        String[] params = filter.trim().split("=");
        URLClassLoader urlClassLoader;
        Class classToLoad;

        try
        {
            String path = FileUtils.joinPath(DatabaseDescriptor.config.plugin_directory, params[0]);
            urlClassLoader = getClassLoader(new File(path));
            classToLoad = urlClassLoader.loadClass(params[1]);
            filters.put(params[2], filterProxy(classToLoad, classToLoad.getConstructor().newInstance()));
        }
        catch (Exception e)
        {
            logger.error("Could not load filter {} in jar {}", params[1], params[1]);
        }
    }

    public IFilter getFilter(String name)
    {
        return filters.get(name);
    }

    public void loadFilters()
    {
        DatabaseDescriptor.config.filters.stream().forEach(this::loadFilter);
    }
}