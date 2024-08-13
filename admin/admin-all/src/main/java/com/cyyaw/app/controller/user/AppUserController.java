package com.cyyaw.app.controller.user;


import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "app-用户")
@RequestMapping("/app/{appId}/user")
@RestController
public class AppUserController {

    @Autowired
    private TAdminService tAdminService;

    @Autowired
    private UUserService uUserService;


    @ApiOperation(value = "app用户信息", notes = "app用户信息")
    @GetMapping("/userInfo")
    public BaseResult userInfo(@TokenData LoginInfo loginInfo) {
        return BaseResult.ok(uUserService.findByTid(loginInfo.getId()));
    }


}
