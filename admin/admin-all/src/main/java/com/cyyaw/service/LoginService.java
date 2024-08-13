package com.cyyaw.service;


import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.user.utils.entity.AdminAuthToken;
import com.cyyaw.user.utils.entity.AuthToken;
import com.cyyaw.user.utils.entity.LoginRequest;

import java.util.List;

/**
 * 获取登录信息接口
 */
public interface LoginService {

    // ========================================= 1. 管理员

    // ========================================= 2. 企业
    /**
     * 企业后台登录
     */
    AdminAuthToken loginEnterUserNameAndPassword(String enterpriseCode, String userName, String password);

    // ========================================= 3. APP管理
    /**
     * 应用后台登录
     */
    AdminAuthToken appAdminLogin(String appId, String userName, String password);

    // ========================================= 3. APP用户

    /**
     * 获取用户信息
     *
     * @param account
     * @return
     */
    TAdmin getLoginInfo(String account, String enterpriseCode);

    /**
     * 获取角色列表
     *
     * @param tid
     * @return
     */
    List<TRole> getRolesByTAdminTid(String tid);


    /**
     * 获取权限列表
     *
     * @param tid
     * @return
     */
    List<TPower> getTPowerByTAdminTid(String tid);

    /**
     * 注册
     *
     * @param tAdmin
     * @return
     */
    TAdmin register(TAdmin tAdmin);

    /**
     * 修改密码
     *
     * @param password
     * @param newpassword
     * @return
     */
    TAdmin updatePassword(String password, String newpassword);

    /**
     * 修改个人信息
     *
     * @param tAdmin
     * @return
     */
    TAdmin updateUserInfo(TAdmin tAdmin);

    /**
     * 微信登录
     */
    AuthToken weixinLogin(String openid, String unionid);


    /**
     * 注册
     *
     * @param registerInfo
     * @return
     */
    TAdmin adminRegister(LoginRequest registerInfo, String eCode);




}
