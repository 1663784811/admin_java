package com.cyyaw.enterprise.service;

import com.cyyaw.enterprise.table.entity.EEnterprise;
import com.cyyaw.jpa.BaseTableService;

public interface EEnterpriseService extends BaseTableService<EEnterprise, Integer> {

    /**
     * 企业注册
     */
    EEnterprise registerEnterprise(EEnterprise enterprise);



    EEnterprise findByCode(String code);



}
