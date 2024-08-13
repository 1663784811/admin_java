package com.cyyaw.service.impl;

import cn.hutool.json.JSONUtil;
import com.cyyaw.config.exception.WebException;
import com.cyyaw.enterprise.service.EApplicationService;
import com.cyyaw.enterprise.table.dao.EEnterpriseDao;
import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.service.LoginService;
import com.cyyaw.service.em.LoginType;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.dao.TAdminDao;
import com.cyyaw.user.table.dao.TPowerDao;
import com.cyyaw.user.table.dao.TRoleDao;
import com.cyyaw.user.table.dao.UUserDao;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.user.utils.BCryptUtil;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.user.utils.entity.AdminAuthToken;
import com.cyyaw.user.utils.entity.AuthToken;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.util.tools.JwtTokenUtils;
import com.cyyaw.util.tools.WebErrCodeEnum;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {


    //===============
    @Autowired
    private TAdminService tAdminService;

    //===============


    @Autowired
    private TPowerDao tPowerDao;

    @Autowired
    private UUserDao uUserDao;

    @Autowired
    private UUserService uUserService;

    @Autowired
    private TAdminDao tAdminDao;

    @Autowired
    private TRoleDao tRoleDao;

    @Autowired
    private EEnterpriseDao eEnterpriseDao;


    @Autowired
    private EApplicationService eApplicationService;

    // =================================
    @Override
    public AdminAuthToken loginEnterUserNameAndPassword(String eCode, String userName, String password) {
        // 第一步: 查用户
        TAdmin tAdmin = tAdminDao.findByAccount(eCode, userName);
        if (ObjectUtils.isEmpty(tAdmin)) {
            WebException.fail(WebErrCodeEnum.WEB_LOGINERR, "账号或密码错误");
        }
        String account = tAdmin.getAccount();
        String tid = tAdmin.getTid();
        String passworded = tAdmin.getPassword();
        // 第二步: 对比密码
//        if (!BCryptUtil.matches(password, passworded)) {
//            WebException.fail(WebErrCodeEnum.WEB_LOGINERR, "账号或密码错误");
//        }
        // 第三步: 查角色
        List<TRole> roleList = tRoleDao.findByAdminId(tid);
        StringBuffer sb = new StringBuffer();
        if (null != roleList && roleList.size() > 0) {
            for (TRole role : roleList) {
                if (sb.length() > 0) {
                    sb.append(role.getCode());
                } else {
                    sb.append("," + role.getCode());
                }
            }
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(tAdmin.getTid());
        loginInfo.setAccount(tAdmin.getAccount());
        loginInfo.setRole(sb.toString());
        loginInfo.setEnterpriseCode(eCode);
        loginInfo.setType(LoginType.enterAdmin.getType());
        // 第四步: 生成jwt
        String token = JwtTokenUtils.createToken(account, JSONUtil.toJsonStr(loginInfo));
        AdminAuthToken authToken = new AdminAuthToken();
        authToken.setTAdmin(tAdmin);
        authToken.setJwtToken(token);
        // 第五步: jwt存放到 redis里
        return authToken;
    }

    // =================================
    @Override
    public AdminAuthToken appAdminLogin(String appId, String userName, String password) {
        TAdmin tAdmin = tAdminDao.findByAccountAndAppId(userName, appId);
        if (ObjectUtils.isEmpty(tAdmin)) {
            WebException.fail(WebErrCodeEnum.WEB_LOGINERR, "账号或密码错误");
        }
        // 第二步: 对比密码
//        if (!BCryptUtil.matches(password, passworded)) {
//            WebException.fail(WebErrCodeEnum.WEB_LOGINERR, "密码错误");
//        }
        // 查应用
        // 登录
        String account = tAdmin.getAccount();
        String enterpriseCode = tAdmin.getEnterpriseCode();
        // ==
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(tAdmin.getTid());
        loginInfo.setAccount(tAdmin.getAccount());
        loginInfo.setRole("");
        loginInfo.setEnterpriseCode(enterpriseCode);
        loginInfo.setAppId(appId);
        loginInfo.setType(LoginType.appAdmin.getType());
        String token = JwtTokenUtils.createToken(account, JSONUtil.toJsonStr(loginInfo));
        AdminAuthToken authToken = new AdminAuthToken();
        authToken.setTAdmin(tAdmin);
        authToken.setJwtToken(token);
        // 第五步: jwt存放到 redis里
        return authToken;
    }



    @Override
    public TAdmin getLoginInfo(String account, String enterpriseCode) {
        List<TAdmin> tAdmins = tAdminDao.getLoginInfo(account, enterpriseCode);
        if (tAdmins == null || tAdmins.size() == 0) {
            WebException.fail("账号或密码错误");
        } else if (tAdmins.size() > 1) {
            WebException.fail("账号异常请与管理员联系");
        }
        return tAdmins.get(0);
    }

    @Override
    public List<TRole> getRolesByTAdminTid(String tid) {
        return tRoleDao.getRolesByTAdminTid(tid);
    }

    @Override
    public List<TPower> getTPowerByTAdminTid(String tid) {
        return tPowerDao.getTPowerByTAdminTid(tid);
    }

    /**
     * 管理员注册
     *
     * @param tAdmin
     * @return
     */
    @Override
    public TAdmin register(TAdmin tAdmin) {
//        List<TAdmin> tAdmins = tAdminDao.getLoignInfo(tAdmin.getAccount());
//        if (tAdmins != null && tAdmins.size() > 0) WebException.fail("账号已经被注册");
//        String tid = StringUtilWHY.getUUID();
//        String salt = StringUtilWHY.getRandomString(20);
//        String hash = new SimpleHash(hashAlgorithmName, tAdmin.getPassword(), salt, hashIterations).toString();
//        TAdmin tAdmin1 = new TAdmin();
//        tAdmin1.setAccount(tAdmin.getAccount());
//        tAdmin1.setPassword(hash);
//        tAdmin1.setTid(tid);
//        tAdmin1.setSalt(salt);
//        tAdmin1.setEmail(tAdmin.getEmail());
//        tAdmin1.setNickname(tAdmin.getAccount());
//        tAdmin1.setCanlogintime(new Date());
//        tAdmin1.setCreatetime(new Date());
//        tAdmin1.setStatus(0);
//        return tAdminDao.save(tAdmin1);
        return null;
    }

    /**
     * 修改密码
     *
     * @param password
     * @param newpassword
     * @return
     */
    @Override
    public TAdmin updatePassword(String password, String newpassword) {
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
//        TAdmin oldeTAdmin = (TAdmin) session.getAttribute(ShiroEnum.USERINFO);
//        TAdmin tAdmin = tAdminDao.findByid(oldeTAdmin.getId());
//        String hash = new SimpleHash(hashAlgorithmName, password, tAdmin.getSalt(), hashIterations).toString();
//        if (!tAdmin.getPassword().equals(hash)) WebException.fail("原密码错误");
//        String hash1 = new SimpleHash(hashAlgorithmName, newpassword, tAdmin.getSalt(), hashIterations).toString();
//        tAdmin.setPassword(hash1);
//        return tAdminDao.save(tAdmin);
        return null;
    }

    /**
     * 修改个人信息
     *
     * @param tAdmin
     * @return
     */
    @Override
    public TAdmin updateUserInfo(TAdmin tAdmin) {
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();
//        TAdmin oldeTAdmin = (TAdmin) session.getAttribute(ShiroEnum.USERINFO);
//        TAdmin tAdmin1 = tAdminDao.findByid(oldeTAdmin.getId());
//        WhyBeanUtils.copyPropertiesAccess(tAdmin, tAdmin1, LoginConstants.UpdateColumnArr);
//        TAdmin save = tAdminDao.save(tAdmin1);
//        session.setAttribute(ShiroEnum.USERINFO, save);
//        return save;
        return null;
    }

    @Override
    public AuthToken weixinLogin(String openid, String unionid) {
        // 第一步: 查用户
        List<UUser> uUserList = uUserDao.findByOpenId(openid);
        UUser user = null;
        if (null != uUserList && uUserList.size() > 0) {
            user = uUserList.get(0);
        } else {
            UUser uUser = new UUser();
            uUser.setAccount(WhyStringUtil.getUUID());
            uUser.setPassword(WhyStringUtil.getUUID());
            uUser.setOpenId(openid);
            uUser.setCreateTime(new Date());
            uUser.setDel(0);
            uUser.setTid(WhyStringUtil.getUUID());
            uUser.setBalance(BigDecimal.ZERO);
            uUser.setUnionId(unionid);
            user = uUserService.save(uUser);
        }
        String account = user.getAccount();
        String tid = user.getTid();
        // 第三步: 查角色
        List<TRole> roleList = new ArrayList<>();
        StringBuffer sb = new StringBuffer("user");
        if (null != roleList && roleList.size() > 0) {
            for (TRole role : roleList) {
                if (sb.length() > 0) {
                    sb.append(role.getCode());
                } else {
                    sb.append("," + role.getCode());
                }
            }
        }
        // 第四步: 生成jwt
        String token = JwtTokenUtils.createToken(account, "");
        AuthToken authToken = new AuthToken();
        authToken.setUUser(user);
        authToken.setJwtToken(token);
        return authToken;
    }



    @Override
    public TAdmin adminRegister(LoginRequest registerInfo, String eCode) {
        // 判断企业
        EEnterprise eEnterprise = eEnterpriseDao.findByEnterpriseByCode(eCode);
        if (eEnterprise == null) {
            WebException.fail(WebErrCodeEnum.WEB_REGISTER_ERR, "账号或密码错误");
        }
        // 判断用户是否存在
        String userName = registerInfo.getUserName();
        String password = registerInfo.getPassword();
        TAdmin tAdmin = tAdminDao.findByAccount(eCode, userName);
        if (!ObjectUtils.isEmpty(tAdmin)) {
            WebException.fail(WebErrCodeEnum.WEB_REGISTER_ERR, "账号或密码错误");
        }
        // 添加用户
        String encode = BCryptUtil.encode(password);
        TAdmin t = new TAdmin();
        t.setTid(WhyStringUtil.getUUID());
        t.setAccount(userName);
        t.setPassword(encode);
        t.setEnterpriseCode(eEnterprise.getTid());
        TAdmin save = tAdminService.save(t);
        return save;
    }



}
