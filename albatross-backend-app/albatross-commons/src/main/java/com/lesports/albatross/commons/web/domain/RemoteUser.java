package com.lesports.albatross.commons.web.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoteUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;//登录用户id
    private String accessToken; //预留，不知道和ssoToken啥关系 --Kipp
    private String userType; //ADMIN; MOBILE
}
