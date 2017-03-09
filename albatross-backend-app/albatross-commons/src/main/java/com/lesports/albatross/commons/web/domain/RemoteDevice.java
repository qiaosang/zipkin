package com.lesports.albatross.commons.web.domain;

import java.io.Serializable;

import com.lesports.albatross.commons.type.OSType;
import com.lesports.albatross.commons.type.Version;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoteDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private OSType systemType;
    private Version systemVersion;
    private String deviceModel;
    private String localizedModel;
    private String appName;
    private Version appVersion;
    private String appBuildNumber;
    private String vendorUUID;
    private String channelName; //如应用宝,360市场
}
