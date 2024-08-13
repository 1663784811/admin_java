package com.cyyaw.user.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录信息
 */
@Data
public class LoginInfo {

    @ApiModelProperty(value = "id", example = "id")
    private String id;

    @ApiModelProperty(value = "登录类型:{0:用户,1:管理员}", example = "0")
    private Integer type;

    @ApiModelProperty(value = "登录账号", example = "userName")
    private String account;

    @ApiModelProperty(value = "用户名", example = "userName")
    private String userName;

    @ApiModelProperty(value = "角色", example = "roleList")
    private String role;

    @ApiModelProperty(value = "权限", example = "powerList")
    private String power;

    @ApiModelProperty(value = "企业Code", example = "enterpriseCode")
    private String enterpriseCode;

    @ApiModelProperty(value = "APP_ID", example = "appId")
    private String appId;

}
