package com.cyyaw.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.cyyaw.service.CpCompanyService;
import com.cyyaw.service.CpRecruitService;
import com.cyyaw.spider.table.company.entity.CpCompany;
import com.cyyaw.spider.table.company.entity.CpRecruit;
import com.cyyaw.util.tools.BaseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "公司-招聘职位")
@RestController
@RequestMapping("/admin/{eCode}/company/recruit")
public class CpRecruitController {

    @Autowired
    private CpRecruitService cpRecruitService;


    @Autowired
    private CpCompanyService cpCompanyService;

    @PostMapping("/saveCpRecruit")
    public BaseResult saveCpRecruit(@RequestBody CpCompany cpCompany) {
        String name = cpCompany.getName();
        if (StrUtil.isNotBlank(name)) {
            CpCompany company = new CpCompany();
            company.setName(name);
            List<CpCompany> cpCompanyList = cpCompanyService.findByExample(company);
            if (!cpCompanyList.isEmpty()) {
                company = cpCompanyList.get(0);
            } else {
                company = cpCompanyService.save(cpCompany);
            }
            String tid = company.getTid();

            List<CpRecruit> recruitList = cpCompany.getRecruitList();
            if(null != recruitList && !recruitList.isEmpty()){
                for (int i = 0; i < recruitList.size(); i++) {
                    CpRecruit cpRecruit = recruitList.get(i);
                    cpRecruit.setCpId(tid);
                    cpRecruitService.save(cpRecruit);
                }
            }
        }
        return BaseResult.ok();
    }


}
