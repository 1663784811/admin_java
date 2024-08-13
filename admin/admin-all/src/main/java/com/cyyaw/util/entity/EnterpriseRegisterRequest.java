package com.cyyaw.util.entity;

import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY,getterVisibility= JsonAutoDetect.Visibility.NONE)
public class EnterpriseRegisterRequest {


    @ApiModelProperty(value = "企业信息", example = "企业信息")
    private EEnterprise eEnterprise;

    @ApiModelProperty(value = "用户信息", example = "用户信息")
    private LoginRequest admin;


}
