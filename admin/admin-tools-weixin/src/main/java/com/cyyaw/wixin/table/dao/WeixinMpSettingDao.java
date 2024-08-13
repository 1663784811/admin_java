package com.cyyaw.wixin.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.wixin.table.entity.WeixinMpSetting;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeixinMpSettingDao extends BaseDao<WeixinMpSetting, Integer> {


    @Query("select m from WeixinMpSetting m where m.status = ?1")
    List<WeixinMpSetting> findListByStatus(Integer status);


}
