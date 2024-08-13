package com.cyyaw.admin.controller;


import com.cyyaw.service.CpCompanyService;
import com.cyyaw.spider.table.company.entity.CpCompany;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "企业-管理员信息")
@RequestMapping("/admin/{eCode}/company")
@RestController
public class CompanyController {

    @Autowired
    private CpCompanyService cpCompanyService;

    @ApiOperation(value = "保存公司")
    @PostMapping("/saveCompany")
    public BaseResult saveCompany(@RequestBody CpCompany cpCompany) {
        return BaseResult.ok(cpCompanyService.save(cpCompany));
    }


}
