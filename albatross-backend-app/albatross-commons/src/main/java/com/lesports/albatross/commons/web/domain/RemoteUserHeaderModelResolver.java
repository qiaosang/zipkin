package com.lesports.albatross.commons.web.domain;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;

import com.lesports.albatross.commons.util.StringUtils;


public class RemoteUserHeaderModelResolver implements HeaderModelResolver<RemoteUser> {

    private static final String REMOTE_USER_HEADER_NAME = "X-Remote-User-Info";

    @Override
    public Optional<RemoteUser> resolve(HttpServletRequest servletRequest) {
        String headerValue = servletRequest.getHeader(REMOTE_USER_HEADER_NAME);
        if (StringUtils.isEmpty(headerValue)) return Optional.empty();
        return Optional.ofNullable(parseRemoteUserHeader(headerValue));
    }

    @Override
    public Optional<RemoteUser> resolve(WebRequest webRequest) {
        String[] userInfo = webRequest.getHeaderValues(REMOTE_USER_HEADER_NAME);
        if (userInfo == null || userInfo.length < 1) return Optional.empty();
        String headerValue = userInfo[0];
        return Optional.ofNullable(parseRemoteUserHeader(headerValue));
    }

    private RemoteUser parseRemoteUserHeader(String headerValue) {
        String[] userAttributes = headerValue.split(";");
        if (userAttributes != null && userAttributes.length > 0) {
            RemoteUser user = new RemoteUser();
            Arrays.stream(userAttributes).forEach(attr -> {
                String[] data = attr.trim().split("=");
                if (data.length == 2) {
                    String key = data[0];
                    String value = data[1];
                    if (key.equalsIgnoreCase("user_id") && !value.equals("null")) {
                        user.setUserId(value);
                    } else if (key.equalsIgnoreCase("user_type")) {
                        user.setUserType(value.toUpperCase());
                    } else if (key.equalsIgnoreCase("user_token")) {
                        user.setAccessToken(value);
                    }
                }
            });
            if (user.getUserId() == null) {
                return null;
            } else {
                return user;
            }
        }
        return null;
    }
}
