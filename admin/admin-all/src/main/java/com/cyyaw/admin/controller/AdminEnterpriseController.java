package com.cyyaw.admin.controller;


import com.cyyaw.enterprise.service.EEnterpriseService;
import com.cyyaw.enterprise.table.dao.EEnterpriseDao;
import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "企业-企业管理")
@RequestMapping("/admin/{eCode}/enterprise")
public class AdminEnterpriseController {

    @Autowired
    private EEnterpriseService eEnterpriseService;

    @Autowired
    private EEnterpriseDao eEnterpriseDao;


    @GetMapping("/enterpriseInfo")
    public BaseResult enterpriseInfo(@PathVariable String eCode) {
        EEnterprise eEnterprise = eEnterpriseService.findByCode(eCode);
        return BaseResult.ok(eEnterprise);
    }


}
