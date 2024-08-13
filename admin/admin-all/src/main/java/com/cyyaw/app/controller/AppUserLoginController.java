package com.cyyaw.app.controller;


import com.cyyaw.service.LoginUserService;
import com.cyyaw.user.table.entity.UUser;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.user.utils.entity.UserAuthToken;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "APP-用户登录")
@RequestMapping("/app/{appId}/login")
@RestController
public class AppUserLoginController {


    @Autowired
    private LoginUserService loginUserService;


    @ApiOperation(value = "退出登录", notes = "退出登录")
    @GetMapping(value = "/logout")
    public BaseResult logout() {
        return BaseResult.ok("退出登录成功");
    }


    @ApiOperation(value = "APP用户登录:用户名密码", notes = "APP用户登录:用户名密码")
    @PostMapping("login")
    public BaseResult login(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        UserAuthToken authToken = loginUserService.loginUserNameAndPassword(appId, userName, password);
        UUser uUser = authToken.getUUser();
        uUser.setPassword(null);
        return BaseResult.ok(authToken, "登录成功");
    }


    @ApiOperation(value = "APP用户注册", notes = "APP用户注册")
    @PostMapping(value = "/register")
    public BaseResult register(@RequestBody LoginRequest registerInfo, @PathVariable String appId) {
        UUser user = loginUserService.userRegister(appId, registerInfo);
        user.setPassword(null);
        return BaseResult.ok(user, "注册成功");
    }

    @ApiOperation(value = "APP用户:手机验证码登录或注册", notes = "APP用户:手机验证码登录或注册")
    @PostMapping("phoneLogin")
    public BaseResult phoneLogin(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        String code = loginRequest.getCode();
        String phone = loginRequest.getPhone();
        UserAuthToken authToken = loginUserService.phoneLogin(appId, code, phone);
        UUser uUser = authToken.getUUser();
        uUser.setPassword(null);
        return BaseResult.ok(authToken, "登录成功");
    }


    @ApiOperation(value = "获取手机验证码", notes = "获取手机验证码")
    @PostMapping("checkCode")
    public BaseResult checkCode(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        String phone = loginRequest.getPhone();
        loginUserService.checkCode(appId, phone);
        return BaseResult.ok();
    }


    // TODO 未完成
    @PostMapping("weixinLogin")
    @ApiOperation(value = "APP用户微信登录或注册", notes = "APP用户微信登录或注册")
    public BaseResult weixinLogin(@RequestBody LoginRequest loginRequest, @PathVariable String appId) {
        return BaseResult.ok( "未完成");
    }

}
