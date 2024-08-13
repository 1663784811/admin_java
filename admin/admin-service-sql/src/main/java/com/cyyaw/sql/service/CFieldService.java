package com.cyyaw.sql.service;

import com.cyyaw.sql.table.entity.CField;
import java.util.List;

public interface CFieldService {

    List<CField> findByCPageComponentsId(String cPageComponentsTid);

}
