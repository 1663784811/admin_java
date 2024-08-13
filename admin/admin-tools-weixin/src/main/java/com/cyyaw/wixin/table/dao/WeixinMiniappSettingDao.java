package com.cyyaw.wixin.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.wixin.table.entity.WeixinMiniappSetting;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeixinMiniappSettingDao extends BaseDao<WeixinMiniappSetting, Integer> {


    @Query("select m from WeixinMiniappSetting m where m.status = ?1 ")
    List<WeixinMiniappSetting> findListByStatus(Integer status);

}
