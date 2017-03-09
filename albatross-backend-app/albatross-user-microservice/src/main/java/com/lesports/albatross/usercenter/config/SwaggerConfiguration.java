package com.lesports.albatross.usercenter.config;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "swagger")
@Data
class SwaggerSettings {
    private String contextPath;
    private String title;
    private String licenseUrl;
    private String license;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
    private String termsUrl;
    private String description;
    private String home;
}

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerSettings.class)
public class SwaggerConfiguration {
    /**
     * API Info as it appears on the swagger-ui page
     */
    @Resource
    private SwaggerSettings swaggerSettings;

    /**
     * API Info as it appears on the swagger-ui page
     */
    private ApiInfo apiInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerSettings.getTitle())
                .license(swaggerSettings.getLicense())
                .contact(new Contact(swaggerSettings.getContactName(), swaggerSettings.getContactUrl(), swaggerSettings.getContactEmail()))
                .licenseUrl(swaggerSettings.getLicenseUrl())
                .termsOfServiceUrl(swaggerSettings.getTermsUrl())
                .description(swaggerSettings.getDescription())
                .build();
        return apiInfo;
    }

    @Bean
    public Docket api() {
        Set<String> produces = new LinkedHashSet<>();
        produces.add(MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .consumes(produces)
                .produces(produces)
                .directModelSubstitute(DateTime.class, Date.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lesports.albatross.usercenter.controller"))
                .build();
    }
}

