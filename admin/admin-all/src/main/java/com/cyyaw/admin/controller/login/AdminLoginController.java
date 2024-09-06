package com.cyyaw.admin.controller.login;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.service.LoginService;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.entity.AdminAuthToken;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WebException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "后台登录模块")
@RequestMapping("/admin/{eCode}/login")
@RestController
public class AdminLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TPowerService tPowerService;

    @ApiOperation(value = "用户名密码登录", notes = "用户名密码登录")
    @PostMapping(value = "/login")
    public BaseResult login(@RequestBody LoginRequest loginRequest, @PathVariable String eCode) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)) {
            WebException.parameterFail();
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


    @GetMapping("/checkCode")
    public void checkCode() {

    }

    @ApiOperation(value = "APP用户微信登录", notes = "APP用户手机验证码登录")
    @PostMapping("weixinLogin")
    public BaseResult weixinLogin(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        return BaseResult.ok("sss", "登录成功");
    }

}
