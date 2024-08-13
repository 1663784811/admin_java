package com.cyyaw.sql.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.sql.table.entity.CPage;

public interface CPageService extends BaseTableService<CPage, Integer> {

    CPage findByTid(String tid);

    CPage findByPageCode(String pageCod);

    /**
     * 复制新页面
     */
    void copyCPage(CPage cPage);

}
