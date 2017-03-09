package com.lesports.albatross.commons.web.method;

import org.springframework.core.MethodParameter;

import com.lesports.albatross.commons.web.domain.RemoteUser;
import com.lesports.albatross.commons.web.domain.RemoteUserHeaderModelResolver;


public class RemoteUserHandlerMethodArgumentResolver extends AbstractHeaderModelHandlerMethodArgumentResolver<RemoteUser> {

    public RemoteUserHandlerMethodArgumentResolver(RemoteUserHeaderModelResolver remoteUserHeaderModelResolver) {
        super(remoteUserHeaderModelResolver);
    }

    public RemoteUserHandlerMethodArgumentResolver() {
        super(new RemoteUserHeaderModelResolver());
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return RemoteUser.class.equals(methodParameter.getParameterType());
    }
}
