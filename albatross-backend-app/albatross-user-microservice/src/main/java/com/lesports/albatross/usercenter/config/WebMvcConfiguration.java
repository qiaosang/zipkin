package com.lesports.albatross.usercenter.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.Filter;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.lesports.albatross.commons.type.GenderType;
import com.lesports.albatross.commons.type.UserAddressType;
import com.lesports.albatross.commons.type.json.TypeDeserializers;
import com.lesports.albatross.commons.type.json.TypeSerializers;
import com.lesports.albatross.commons.web.method.RemoteDeviceHandlerMethodArgumentResolver;
import com.lesports.albatross.commons.web.method.RemoteUserHandlerMethodArgumentResolver;
import com.lesports.albatross.commons.web.versioning.ApiVersionRequestMappingHandlerMapping;

/**
 * Created by litzuhsien on 4/29/16.
 */

@Configuration
public class WebMvcConfiguration extends DelegatingWebMvcConfiguration {

    private final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }



    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        TimeZone gmt8TimeZone = TimeZone.getTimeZone("GMT+8");
        bean.setIndentOutput(true);
        bean.setSimpleDateFormat(DATETIME_FORMAT);
        bean.setTimeZone(gmt8TimeZone);
        bean.afterPropertiesSet();

        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        ObjectMapper objectMapper = bean.getObject();
        objectMapper.setTimeZone(gmt8TimeZone);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        objectMapper.setDateFormat(df);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(jacksonJodaModule());
        objectMapper.registerModule(enumTypeModule());
        return objectMapper;
    }

    public SimpleModule enumTypeModule() {
        SimpleModule enumTypeModule = new SimpleModule("EnumTypeModule", PackageVersion.VERSION);
        enumTypeModule.addDeserializer(GenderType.class, new TypeDeserializers.GenderTypeDeserializer());
        enumTypeModule.addDeserializer(UserAddressType.class, new TypeDeserializers.UserAddressTypeDeserializer());

        enumTypeModule.addSerializer(GenderType.class, new TypeSerializers.GenderTypeSerializer());
        enumTypeModule.addSerializer(UserAddressType.class, new TypeSerializers.UserAddressTypeSerializer());
        return enumTypeModule;
    }

    @Bean
    @Primary
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();
        DateTimeFormatterFactory formatterFactory = new DateTimeFormatterFactory();
        formatterFactory.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        formatterFactory.setPattern(DATETIME_FORMAT);
        JacksonJodaDateFormat jodaDateFormat = new JacksonJodaDateFormat(formatterFactory.createDateTimeFormatter());
        module.addDeserializer(DateTime.class, DateTimeDeserializer.forType(DateTime.class));
        module.addSerializer(DateTime.class, new DateTimeSerializer(jodaDateFormat));
        return module;
    }

    @Bean
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper());
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
        configurer.setUseSuffixPatternMatch(true);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        RemoteDeviceHandlerMethodArgumentResolver systemTypeHandlerMethodArgumentResolver = new RemoteDeviceHandlerMethodArgumentResolver();
        argumentResolvers.add(systemTypeHandlerMethodArgumentResolver);
        RemoteUserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver = new RemoteUserHandlerMethodArgumentResolver();
        argumentResolvers.add(userHandlerMethodArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .setCachePeriod(3600);

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(3600);
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html").setContextRelative(true);
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        ApiVersionRequestMappingHandlerMapping handlerMapping = new ApiVersionRequestMappingHandlerMapping("v");
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());

        PathMatchConfigurer configurer = getPathMatchConfigurer();
        if (configurer.isUseSuffixPatternMatch() != null) {
            handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
        }
        if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
            handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
        }
        if (configurer.isUseTrailingSlashMatch() != null) {
            handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
        }
        if (configurer.getPathMatcher() != null) {
            handlerMapping.setPathMatcher(configurer.getPathMatcher());
        }
        if (configurer.getUrlPathHelper() != null) {
            handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
        }
        return handlerMapping;
    }
}
