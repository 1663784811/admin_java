package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.TAdmin;

public interface TAdminService extends BaseTableService<TAdmin, Integer> {


    TAdmin findByAccount(String enterpriseCode, String account);

}
