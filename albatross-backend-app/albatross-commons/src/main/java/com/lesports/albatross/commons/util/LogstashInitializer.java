package com.lesports.albatross.commons.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class LogstashInitializer {

    private final static String DEFAULT_ENV_PROPERTIES = "env.properties";
    private final static String PROPERTY_NANE_LOGSTASH_SERVER = "logstash.server";
    private final static String LOGSTASH_SERVER_ENV = "LOGSTASH_SERVER";
    /**
     * 采用静态方法
     */
    private static Properties props = new Properties();

    static {
        Resource resource = new ClassPathResource(DEFAULT_ENV_PROPERTIES);
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private LogstashInitializer() {
    }

    private static String getKeyValue(String key) {
        return props.getProperty(key);
    }


    public static void loadConfiguration() {
        if (System.getenv(LOGSTASH_SERVER_ENV) == null) {
            System.setProperty(LOGSTASH_SERVER_ENV, LogstashInitializer.getKeyValue(PROPERTY_NANE_LOGSTASH_SERVER));
        }
    }

    public static String getLogstashServer() {
        String env = System.getenv(LOGSTASH_SERVER_ENV);
        if (env == null) {
            return System.getProperty(LOGSTASH_SERVER_ENV);
        } else {
            return env;
        }
    }
}
