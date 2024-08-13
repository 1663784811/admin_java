package com.cyyaw.admin.controller.login;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.enterprise.service.EEnterpriseService;
import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.service.LoginService;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.service.TRoleService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.AdminAuthToken;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.util.entity.EnterpriseRegisterRequest;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "后台登录模块")
@RequestMapping("/admin/{eCode}/login")
@RestController
public class AdminLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TPowerService tPowerService;

    @Autowired
    private EEnterpriseService eEnterpriseService;


    @Autowired
    private TRoleService tRoleService;


    @ApiOperation(value = "后台登录", notes = "后台登录")
    @PostMapping(value = "/login")
    public BaseResult login(@RequestBody LoginRequest loginRequest, @PathVariable String eCode) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            return BaseResult.fail("参数错误...");
        }
        AdminAuthToken authToken = loginService.loginEnterUserNameAndPassword(eCode, userName, password);
        TAdmin tAdmin = authToken.getTAdmin();
        tAdmin.setPassword(null);
        // 查权限
        List<TPower> tPowerList = tPowerService.findAdminPower(tAdmin.getTid());
        authToken.setAuthList(tPowerList);
        return BaseResult.ok(authToken, "登录成功");
    }

    @ApiOperation(value = "管理员注册", notes = "管理员注册")
    @PostMapping(value = "/register")
    public BaseResult register(@RequestBody LoginRequest registerInfo, @PathVariable String eCode) {
        TAdmin tAdmin = loginService.adminRegister(registerInfo, eCode);
        tAdmin.setPassword(null);
        return BaseResult.ok(tAdmin, "注册成功");
    }


    @ApiOperation(value = "企业注册", notes = "企业注册")
    @PostMapping(value = "/enterpriseRegister")
    public BaseResult enterpriseRegister(@RequestBody EnterpriseRegisterRequest enterpriseRegisterRequest, @PathVariable String eCode) {
        log.info("------------企业注册----------{}", enterpriseRegisterRequest);
        EEnterprise eEnterprise = enterpriseRegisterRequest.getEEnterprise();
        LoginRequest loginRequest = enterpriseRegisterRequest.getAdmin();
        // 第一步：保存企业信息
        EEnterprise e = eEnterpriseService.registerEnterprise(eEnterprise);
        // 第二步：保存负责人信息
        String tid = e.getCode();
        TAdmin admin = loginService.adminRegister(loginRequest, eCode);
        admin.setPassword(null);
        // 第三步:分配权限
        tRoleService.initRole(tid, admin.getTid());
        Map<String, Object> msg = new HashMap<>();
        msg.put("admin", admin);
        msg.put("enterprise", e);

        // 初始化企业角色,初始化企业菜单, 给消息队列发消息


        return BaseResult.ok(msg, "注册成功");
    }


    @GetMapping("/checkCode")
    public void checkCode() {

    }

    @ApiOperation(value = "APP用户微信登录", notes = "APP用户手机验证码登录")
    @PostMapping("weixinLogin")
    public BaseResult weixinLogin(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        return BaseResult.ok("sss", "登录成功");
    }

}
