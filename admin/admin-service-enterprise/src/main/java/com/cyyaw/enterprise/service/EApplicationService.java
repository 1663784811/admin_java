package com.cyyaw.enterprise.service;

import com.cyyaw.enterprise.table.entity.EApplication;
import com.cyyaw.jpa.BaseTableService;

public interface EApplicationService extends BaseTableService<EApplication, Integer> {


    EApplication findByCode(String appId);

    EApplication openApp(EApplication application);

}
