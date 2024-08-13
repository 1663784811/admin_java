package com.cyyaw.admin.controller;

import com.cyyaw.enterprise.service.EApplicationService;
import com.cyyaw.enterprise.table.entity.EApplication;
import com.cyyaw.user.config.TokenData;
import com.cyyaw.user.service.TAdminService;
import com.cyyaw.user.table.entity.TAdmin;
import com.cyyaw.user.utils.LoginInfo;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.WhyStringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@Api(tags = "应用中心")
@RequestMapping("/admin/{eCode}/app")
public class AdminAppController {

    @Autowired
    private EApplicationService applicationService;


    @ApiOperation(value = "开通APP", notes = "开通APP")
    @PostMapping("/openApp")
    public BaseResult openApp(EApplication application, @TokenData LoginInfo loginInfo) {
        String enterpriseCode = loginInfo.getEnterpriseCode();
        application.setEnterpriseCode(enterpriseCode);
        application.setUrl("");


        EApplication app = applicationService.openApp(application);
        return BaseResult.ok(app);
    }

}
