package com.cyyaw.sql.service;


import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.sql.table.entity.CPageComponents;

import java.util.List;

public interface CPageComponentsService extends BaseTableService<CPageComponents, Integer> {



    List<CPageComponents> findByPageId(String pageId);





    CPageComponents findById(Integer id);


}
