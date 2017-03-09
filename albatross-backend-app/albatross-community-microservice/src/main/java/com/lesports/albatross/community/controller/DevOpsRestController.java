package com.lesports.albatross.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lesports.albatross.commons.web.versioning.ApiVersion;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author KILLER
 * @version 2015年9月24日 下午4:31:04 class instructions
 */
@Api(description = "开发维护人员专用", tags = "开发维护")
@RestController
@RequestMapping("devops")
@ApiVersion("1")
@Slf4j
@SuppressWarnings("unchecked")
public class DevOpsRestController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:2001";

    @RequestMapping(value = "/test-zipkin", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String testZipkin() throws Exception {
        Thread.sleep(200L);
        String s = this.restTemplate.getForObject(url + "/v1/addresses/1", String.class);
        return s;
    }

}
