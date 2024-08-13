package com.cyyaw.admin.controller;

import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TRoleService;
import com.cyyaw.user.table.entity.TRole;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/admin/{eCode}/role")
@RestController
public class TRoleController {
    @Autowired
    private TRoleService tRoleService;

    /**
     * 添加或修改
     */
    @PostMapping("/saveTRole")
    public BaseResult saveTRole(@RequestBody TRole saveObj, @TokenData LoginInfo loginInfo) {
        saveObj.setEnterpriseCode(loginInfo.getEnterpriseCode());
        TRole obj = tRoleService.saveTree(saveObj);
        return BaseResult.ok(obj);
    }
}
