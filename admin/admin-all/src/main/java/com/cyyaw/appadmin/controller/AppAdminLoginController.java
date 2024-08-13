package com.cyyaw.appadmin.controller;


import com.cyyaw.service.LoginService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.utils.entity.AdminAuthToken;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "APP管理员登录模块")
@RequestMapping("/appAdmin/{appId}")
@RestController
public class AppAdminLoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation(value = "app管理员登录", notes = "app管理员登录")
    @PostMapping(value = "/login")
    public BaseResult login(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        AdminAuthToken authToken = loginService.appAdminLogin(appId, userName, password);
        TAdmin tAdmin = authToken.getTAdmin();
        tAdmin.setPassword(null);
        return BaseResult.ok(authToken, "登录成功");
    }


    @ApiOperation(value = "门店管理员", notes = "门店管理员")
    @PostMapping(value = "/store/{storeId}/login")
    public BaseResult adminLogin(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        AdminAuthToken authToken = loginService.appAdminLogin(appId, userName, password);
        TAdmin tAdmin = authToken.getTAdmin();
        tAdmin.setPassword(null);
        return BaseResult.ok(authToken, "登录成功");
    }


}
