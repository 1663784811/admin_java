package com.cyyaw.service;

import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.user.utils.entity.UserAuthToken;

import java.util.List;

public interface LoginUserService {


    /**
     * APP登录
     */
    UserAuthToken loginUserNameAndPassword(String appId, String userName, String password);

    /**
     * 用户注册
     */
    UUser userRegister(String appId, LoginRequest registerInfo);


    /**
     * 手机验证码登录或注册
     */
    UserAuthToken phoneLogin(String appId, String code, String phone);


    /**
     * 获取手机验证码
     */
    void checkCode(String appId, String phone);

}
