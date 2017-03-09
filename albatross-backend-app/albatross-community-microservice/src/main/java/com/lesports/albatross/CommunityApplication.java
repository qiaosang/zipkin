package com.lesports.albatross;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.lesports.albatross.commons.trace.TracingAspect;

/**
 * Created by litzuhsien on 4/26/16.
 */

@SpringCloudApplication
@EnableFeignClients
public class CommunityApplication {

    @Value("${logging.aspect.logging-throwable:true}")
    private boolean loggingThrowable;

    public static void main(String[] args) {      
        SpringApplication application = new SpringApplication(CommunityApplication.class);
        application.run(args);
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "logging.aspect",
            name = {"enabled"}
    )
    public TracingAspect loggingAspect() {
        return new TracingAspect(loggingThrowable);
    }
}
