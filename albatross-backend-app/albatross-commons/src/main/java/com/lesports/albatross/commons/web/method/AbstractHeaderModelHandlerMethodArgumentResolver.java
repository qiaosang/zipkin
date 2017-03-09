package com.lesports.albatross.commons.web.method;

import com.lesports.albatross.commons.web.domain.HeaderModelResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.Serializable;

/**
 * Created by Gang Li on 29/11/2016.
 * Copyright © 2016 RealMedis LLC. All rights reserved.
 */
public abstract class AbstractHeaderModelHandlerMethodArgumentResolver<M extends Serializable> implements HandlerMethodArgumentResolver {

    private final HeaderModelResolver<M> headerModelResolver;

    public abstract boolean supportsParameter(MethodParameter methodParameter);

    public AbstractHeaderModelHandlerMethodArgumentResolver(HeaderModelResolver<M> headerModelResolver) {
        this.headerModelResolver = headerModelResolver;
    }

    @Override
    public M resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return this.headerModelResolver.resolve(nativeWebRequest).orElse(null);
    }
}
