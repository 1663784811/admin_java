package com.cyyaw.signin.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.signin.table.entity.SiSignIn;

public interface SiSignInService extends BaseTableService<SiSignIn, Integer> {

    SiSignIn findByTid(String tid);


}
