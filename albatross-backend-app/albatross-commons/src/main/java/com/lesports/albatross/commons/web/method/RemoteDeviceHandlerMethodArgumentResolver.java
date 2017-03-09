package com.lesports.albatross.commons.web.method;

import org.springframework.core.MethodParameter;

import com.lesports.albatross.commons.web.domain.RemoteDevice;
import com.lesports.albatross.commons.web.domain.RemoteDeviceHeaderModelResolver;

public class RemoteDeviceHandlerMethodArgumentResolver extends AbstractHeaderModelHandlerMethodArgumentResolver<RemoteDevice> {

    public RemoteDeviceHandlerMethodArgumentResolver(RemoteDeviceHeaderModelResolver remoteDeviceHeaderModelResolver) {
        super(remoteDeviceHeaderModelResolver);
    }

    public RemoteDeviceHandlerMethodArgumentResolver() {
        super(new RemoteDeviceHeaderModelResolver());
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return RemoteDevice.class.equals(methodParameter.getParameterType());
    }
}
