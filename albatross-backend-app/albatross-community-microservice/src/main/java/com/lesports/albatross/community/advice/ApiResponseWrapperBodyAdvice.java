package com.lesports.albatross.community.advice;

import com.lesports.albatross.commons.web.domain.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;


@Order(5)
@ControllerAdvice
@Slf4j
public class ApiResponseWrapperBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue mappingJacksonValue, MediaType mediaType, MethodParameter methodParameter, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        final String requestPath = serverHttpRequest.getURI().getPath();
        Object jsonBody = mappingJacksonValue.getValue();
        log.debug("request: {} write response wrapper {}", requestPath, jsonBody);
        if (jsonBody instanceof ResponseWrapper) {
            ResponseWrapper<?> responseWrapper = (ResponseWrapper<?>) jsonBody;
            responseWrapper.setPath(requestPath);
        }
    }
}
