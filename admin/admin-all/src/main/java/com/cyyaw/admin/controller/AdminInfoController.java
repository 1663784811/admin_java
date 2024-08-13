package com.cyyaw.admin.controller;


import com.cyyaw.service.AdminUserService;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.service.TPowerService;
import com.cyyaw.user.service.UUserService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.table.entity.TPower;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.user.utils.MenuUtils;
import com.cyyaw.user.utils.entity.MenuEntity;
import com.cyyaw.user.utils.entity.TreeEntity;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "企业-管理员信息")
@RequestMapping("/admin/{eCode}/user")
@RestController
public class AdminInfoController {

    @Autowired
    private TAdminService tAdminService;

    @Autowired
    private UUserService uUserService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TPowerService powerService;


    @ApiOperation(value = "管理员信息", notes = "管理员信息")
    @GetMapping("/userInfo")
    public BaseResult userInfo(@TokenData LoginInfo loginInfo) {
        Integer type = loginInfo.getType();
        if (null != type && type.equals(1)) {
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
    public BaseResult adminMenu(@TokenData LoginInfo loginInfo) {
        //String adminId = loginInfo.getId();
        //List<TreeEntity.Node<TPower>> arr = adminUserService.adminMenu(adminId, 0);
        //        return BaseResult.ok(arr);
        BaseResult<TreeEntity<TPower>> rest = powerService.queryMenu(loginInfo.getEnterpriseCode(), 1);
        return BaseResult.ok(MenuUtils.getMenu(rest.getData()));
    }


}
