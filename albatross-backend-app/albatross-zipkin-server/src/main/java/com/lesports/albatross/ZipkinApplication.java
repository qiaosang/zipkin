package com.lesports.albatross;

import lombok.extern.slf4j.Slf4j; 

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by zhanghu on 17/2/23.
 */

@SpringCloudApplication
@EnableZipkinStreamServer
@Slf4j
public class ZipkinApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZipkinApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext ctx = application.run(args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.info("Spring Boot profile is:" + profile);
        }
    }


}
