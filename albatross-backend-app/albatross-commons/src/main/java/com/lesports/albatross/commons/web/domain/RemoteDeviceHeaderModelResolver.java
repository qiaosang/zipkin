package com.lesports.albatross.commons.web.domain;

import com.lesports.albatross.commons.type.OSType;
import com.lesports.albatross.commons.type.Version;
import com.lesports.albatross.commons.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Gang Li on 29/11/2016.
 * Copyright © 2016 LeSports Inc. All rights reserved.
 */
public class RemoteDeviceHeaderModelResolver implements HeaderModelResolver<RemoteDevice> {

    private static final String REMOTE_DEVICE_HEADER_NAME = "X-Remote-Client-Info";

    @Override
    public Optional<RemoteDevice> resolve(HttpServletRequest servletRequest) {
        String clientInfo = servletRequest.getHeader(REMOTE_DEVICE_HEADER_NAME);
        if (StringUtils.isEmpty(clientInfo)) return Optional.empty();
        return Optional.ofNullable(parseRemoteDeviceHeader(clientInfo));
    }

    @Override
    public Optional<RemoteDevice> resolve(WebRequest webRequest) {
        String[] clientInfo = webRequest.getHeaderValues(REMOTE_DEVICE_HEADER_NAME);
        if (clientInfo == null || clientInfo.length < 1) return Optional.empty();
        String infoString = clientInfo[0];
        return Optional.ofNullable(parseRemoteDeviceHeader(infoString));
    }

    private RemoteDevice parseRemoteDeviceHeader(String headerValue) {

        String[] clientAttributes = headerValue.split(";");
        if (clientAttributes.length > 0) {
            RemoteDevice device = new RemoteDevice();
            Arrays.stream(clientAttributes).forEach(attr -> {
                String[] data = attr.trim().split("=");
//                assert data.length == 2;
                if (data.length == 2) {
                    String key = data[0];
                    String value = data[1];
                    if (key.equalsIgnoreCase("OSVersion")) {
                        device.setSystemVersion(Version.of(value));
                    } else if (key.equalsIgnoreCase("AppVersion")) {
                        device.setAppVersion(Version.of(value));
                    } else if (key.equalsIgnoreCase("AppBuildNumber")) {
                        device.setAppBuildNumber(value);
                    } else if (key.equalsIgnoreCase("Application")) {
                        device.setAppName(value);
                    } else if (key.equalsIgnoreCase("DeviceModel")) {
                        device.setDeviceModel(value);
                    } else if (key.equalsIgnoreCase("OS")) {
                        if (value.equalsIgnoreCase("Android"))
                            device.setSystemType(OSType.ANDROID);
                        else if (value.equalsIgnoreCase("iPhone OS") || value.equalsIgnoreCase("iOS")) {
                            device.setSystemType(OSType.IOS);
                        } else {
                            device.setSystemType(OSType.UNDEFINED);
                        }
                    } else if (key.equalsIgnoreCase("LocalizedModel")) {
                        device.setLocalizedModel(value);
                    } else if (key.equalsIgnoreCase("VendorUUID")) {
                        device.setVendorUUID(value);
                    } else if (key.equalsIgnoreCase("ChannelName")) {
                        device.setChannelName(value);
                    }
                }
            });
            return device;
        }
        return null;
    }
}
