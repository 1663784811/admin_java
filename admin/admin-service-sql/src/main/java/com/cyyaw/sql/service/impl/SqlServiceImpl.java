package com.cyyaw.sql.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.sql.service.SqlService;
import com.cyyaw.sql.table.dao.CSqlDao;
import com.cyyaw.sql.table.entity.CSql;
import com.cyyaw.util.tools.BaseResult;
import com.cyyaw.util.tools.PageRespone;
import com.cyyaw.util.tools.WhyStringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SqlServiceImpl extends BaseService<CSql, Integer> implements SqlService {

    @Autowired
    private CSqlDao cSqlDao;

    @Override
    public BaseDao getBaseDao() {
        return cSqlDao;
    }

    @Override
    public BaseResult<CSql> sqlList(JSONObject json) {
        PageRespone<CSql> sqlPage = findPage(json);
        return BaseResult.ok(sqlPage);
    }

    @Override
    public CSql save(CSql cSql) {
        Integer id = cSql.getId();
        if (null != id) {
            CSql oldCSql = cSqlDao.findByid(id);
            BeanUtils.copyProperties(cSql, oldCSql);
            return cSqlDao.save(oldCSql);
        } else {
            cSql.setCreateTime(new Date());
            cSql.setDel(0);
            if(StrUtil.isBlank(cSql.getTid())){
                cSql.setTid(WhyStringUtil.getUUID());
            }
            return cSqlDao.save(cSql);
        }
    }

    @Override
    public void delSql(Integer[] idArr) {
        for (int i = 0; i < idArr.length; i++) {
            CSql cSql = cSqlDao.findByid(idArr[i]);
            cSqlDao.delete(cSql);
        }
    }
}
