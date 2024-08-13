package com.cyyaw.wixin.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.wixin.table.entity.WeixinCpSetting;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeixinCpSettingDao extends BaseDao<WeixinCpSetting, Integer> {


    @Query("select m from WeixinCpSetting m where m.status = ?1")
    List<WeixinCpSetting> findListByStatus(Integer status);
}
