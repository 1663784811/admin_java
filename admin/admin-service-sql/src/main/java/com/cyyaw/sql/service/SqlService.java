package com.cyyaw.sql.service;

import cn.hutool.json.JSONObject;
import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.sql.table.entity.CSql;
import com.cyyaw.util.tools.BaseResult;

public interface SqlService extends BaseTableService<CSql, Integer> {

    BaseResult<CSql> sqlList(JSONObject json);

    CSql save(CSql cSql);

    void delSql(Integer[] idArr);

}
