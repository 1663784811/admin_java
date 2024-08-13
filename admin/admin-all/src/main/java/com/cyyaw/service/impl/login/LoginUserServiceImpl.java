package com.cyyaw.service.impl.login;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.enterprise.service.EApplicationService;
import com.cyyaw.enterprise.table.entity.EApplication;
import com.cyyaw.service.LoginUserService;
import com.cyyaw.service.em.LoginType;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.user.utils.entity.UserAuthToken;
import com.cyyaw.util.tools.JwtTokenUtils;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private EApplicationService eApplicationService;

    @Autowired
    private UUserService uUserService;

    @Autowired
    private LoginCommon loginCommon;

    @Override
    public UserAuthToken loginUserNameAndPassword(String appId, String userName, String password) {
        EApplication app = eApplicationService.findByCode(appId);
        String eCode = app.getCode();
        if (app == null) WebException.fail("APP不存在");
        UUser user = uUserService.findByAppIdAndAccount(appId, userName);
        if (null == user) WebException.fail("找不到用户" + userName);
        // 验证密码
        LoginCommon.checkPassword(appId, password, user.getPassword());
        // 登录成功: 生成Token
        String tid = user.getTid();
        String userAccount = user.getAccount();
        // 设置Token信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(tid);
        loginInfo.setAccount(userAccount);
        loginInfo.setUserName(user.getNickName());
        loginInfo.setAppId(appId);
        loginInfo.setEnterpriseCode(eCode);
        loginInfo.setType(LoginType.appUser.getType());
        String token = JwtTokenUtils.createToken(tid, JSONUtil.toJsonStr(new JSONObject(loginInfo)));
        UserAuthToken userAuthToken = new UserAuthToken();
        userAuthToken.setJwtToken(token);
        userAuthToken.setUUser(user);
        return userAuthToken;
    }

    @Override
    public UUser userRegister(String appId, LoginRequest registerInfo) {
        String userName = registerInfo.getUserName();
        String password = registerInfo.getPassword();
        EApplication app = eApplicationService.findByCode(appId);
        if (app == null) WebException.fail("APP不存在");
        UUser uUser = uUserService.findByAppIdAndAccount(appId, userName);
        if (null != uUser) WebException.fail("用户名已被注册");
        UUser user = new UUser();
        user.setTid(WhyStringUtil.getUUID());
        user.setCreateTime(new Date());
        user.setDel(0);
        user.setNote("用户注册");
        user.setAppId(appId);
        user.setAccount(userName);
        user.setPassword(LoginCommon.pwdEncryption(password));
        user.setTrueName("");
        user.setPhone(registerInfo.getPhone());
        user.setNickName("");
        user.setFace("");
        user.setSex("");
        user.setEmail("");
        user.setIp("");
        user.setSalt("");
        user.setStatus(0);
        user.setType(0);
        user.setAdminId("");
        user.setIntegral(0);
        user.setOpenId("");
        user.setUnionId("");
        return uUserService.save(user);
    }

    @Override
    public UserAuthToken phoneLogin(String appId, String code, String phone) {
        EApplication app = eApplicationService.findByCode(appId);
        String eCode = app.getCode();
        if (app == null) WebException.fail("APP不存在");
        //验证验证码
        loginCommon.verifyPhoneCode(appId, phone, eCode);
        // 查询手机号
        UUser user = uUserService.findByAppIdAndPhone(appId, phone);
        if (null == user) {
            // 注册
            String str = WhyStringUtil.getRandomString(10);
            LoginRequest registerInfo = new LoginRequest();
            registerInfo.setUserName("phone_" + str);
            registerInfo.setPassword(str);
            user = userRegister(appId, registerInfo);
        }
        String tid = user.getTid();
        // 登录
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(tid);
        loginInfo.setAccount(user.getAccount());
        loginInfo.setUserName(user.getNickName());
        loginInfo.setAppId(appId);
        loginInfo.setEnterpriseCode(eCode);
        loginInfo.setType(LoginType.appUser.getType());
        String token = JwtTokenUtils.createToken(tid, JSONUtil.toJsonStr(new JSONObject(loginInfo)));
        UserAuthToken userAuthToken = new UserAuthToken();
        userAuthToken.setJwtToken(token);
        userAuthToken.setUUser(user);
        return userAuthToken;
    }

    @Override
    public void checkCode(String appId, String phone) {
        loginCommon.getPhoneCode(appId, phone);
    }


}
