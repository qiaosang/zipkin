package com.lesports.albatross.community.config;

import lombok.Data;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by Gang Li on 09/10/2016. Copyright © 2016 LeSports Inc. All rights reserved.
 */

@Data
@ConfigurationProperties(prefix = "realmedis.jetty")
class JettyProperties {

    private final ThreadPool threadPool = new ThreadPool();
    private final Connector connector = new Connector();
    private final Http http = new Http();

    @Data
    public static class ThreadPool {
        private Integer maxThreads = 300;
        private Integer minThreads = 20;
        private Integer idleTimeout = 60000;
    }

    @Data
    public static class Http {
        private boolean sendServerVersion = false;
    }

    /**
     * Currently, it want be used --litzuhisen
     */
    @Data
    public static class Connector {
        private Integer acceptors = 1;
        private Integer maxIdleTime = -1;

        private Integer lowResourcesMaxIdleTime = -1;
        private Integer lowResourcesConnections = 0;

        private Integer acceptQueueSize = 50;
        private Long socketLingerTime;
        private boolean resolveNames = false;
    }
}

@Configuration
@EnableConfigurationProperties(JettyProperties.class)
public class JettyConfiguration {

    @Resource
    private JettyProperties jettyProperties;

    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory() {
        JettyEmbeddedServletContainerFactory jettyContainer = new JettyEmbeddedServletContainerFactory();
        jettyContainer.addServerCustomizers(server -> {
            // Tweak the connection pool used by Jetty to handle incoming HTTP connections
            server.setStopAtShutdown(true);
            server.setStopTimeout(5000);
            server.setDumpAfterStart(false);
            server.setDumpBeforeStop(true);

            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(jettyProperties.getThreadPool().getMaxThreads());
            threadPool.setMinThreads(jettyProperties.getThreadPool().getMinThreads());
            threadPool.setIdleTimeout(jettyProperties.getThreadPool().getIdleTimeout());

            HttpConfiguration httpConfiguration = new HttpConfiguration();
            httpConfiguration.setSendServerVersion(jettyProperties.getHttp().isSendServerVersion());

            HttpConnectionFactory connectionFactory = new HttpConnectionFactory(httpConfiguration);
            final ServerConnector connector = new ServerConnector(server, connectionFactory);

            server.addConnector(connector);
        });
        
        
        return jettyContainer;
    }

}
