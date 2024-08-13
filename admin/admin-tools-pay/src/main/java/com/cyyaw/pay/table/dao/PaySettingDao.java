package com.cyyaw.pay.table.dao;


import com.cyyaw.jpa.BaseDao;
import com.cyyaw.pay.table.entity.PaySetting;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaySettingDao extends BaseDao<PaySetting, Integer> {


    @Query("select m from PaySetting m where m.appId = ?1 and m.payType = ?2")
    List<PaySetting> findByAppIdAndPayType(String appId, Integer payType);



}
