package com.cyyaw.signin.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.signin.table.entity.SiSignLog;

import java.util.List;

public interface SiSignLogService extends BaseTableService<SiSignLog, Integer> {

    List<SiSignLog> findBySignInId(String signInId);

}
