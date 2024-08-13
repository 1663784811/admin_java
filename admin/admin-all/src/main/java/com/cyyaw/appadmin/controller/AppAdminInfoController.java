package com.cyyaw.appadmin.controller;


import com.cyyaw.service.AdminUserService;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "app-管理员信息")
@RequestMapping("/appAdmin/{appId}/user")
@RestController
public class AppAdminInfoController {

    @Autowired
    private TAdminService tAdminService;

    @Autowired
    private UUserService uUserService;

    @Autowired
    private AdminUserService adminUserService;


    @ApiOperation(value = "管理员信息", notes = "管理员信息")
    @GetMapping("/userInfo")
    public BaseResult userInfo(@TokenData LoginInfo loginInfo) {
        Integer type = loginInfo.getType();
        if (null != type && type.equals(2)) {
            String enterpriseCode = loginInfo.getEnterpriseCode();
            String account = loginInfo.getAccount();
            TAdmin admin = tAdminService.findByAccount(enterpriseCode, account);
            return BaseResult.ok(admin);
        } else {
            return BaseResult.fail("登录方式错误");
        }
    }


    /**
     * 获取用户菜单
     *
     * @return
     */
    @GetMapping(value = "/menu")
    public BaseResult adminMenu() {
        String adminId = "admin";
        List<TreeEntity.Node<TPower>> arr = adminUserService.adminMenu(adminId, 2);
        return BaseResult.ok(arr);
    }


}
