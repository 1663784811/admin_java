package com.cyyaw.root.controller;


import com.cyyaw.enterprise.service.EEnterpriseService;
import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.service.LoginService;
import com.cyyaw.user.service.TRoleService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.utils.entity.LoginRequest;
import com.cyyaw.util.entity.EnterpriseRegisterRequest;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "企业")
@RequestMapping("/root/enterprise")
@RestController
public class EnterpriseController {

    @Autowired
    private EEnterpriseService eEnterpriseService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TRoleService tRoleService;


    @ApiOperation(value = "企业注册", notes = "企业注册")
    @PostMapping(value = "/register")
    public BaseResult register(@RequestBody EnterpriseRegisterRequest enterpriseRegisterRequest) {
        log.info("------------企业注册----------{}", enterpriseRegisterRequest);
        EEnterprise eEnterprise = enterpriseRegisterRequest.getEEnterprise();
        LoginRequest loginRequest = enterpriseRegisterRequest.getAdmin();
        // 判断数据
        // 判断账号是否被注册
        //

        // 第一步：保存企业信息
        EEnterprise e = eEnterpriseService.registerEnterprise(eEnterprise);
        // 第二步：保存负责人信息
        String eCode = e.getCode();
        TAdmin admin = loginService.adminRegister(loginRequest, eCode);
        // 第三步:分配权限
        tRoleService.initRole(eCode, admin.getTid());
        Map<String, Object> msg = new HashMap<>();
        msg.put("admin", admin);
        msg.put("enterprise", e);
        // 初始化企业角色,初始化企业菜单, 给消息队列发消息
        return BaseResult.ok(msg, "注册成功");
    }



}
