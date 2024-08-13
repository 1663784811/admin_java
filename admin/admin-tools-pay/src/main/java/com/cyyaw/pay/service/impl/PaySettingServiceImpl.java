package com.cyyaw.pay.service.impl;

import com.cyyaw.config.exception.WebException;
import com.cyyaw.jpa.BaseDao;
import com.cyyaw.jpa.BaseService;
import com.cyyaw.pay.service.PaySettingService;
import com.cyyaw.pay.table.dao.PaySettingDao;
import com.cyyaw.pay.table.entity.PaySetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class PaySettingServiceImpl extends BaseService<PaySetting, Integer> implements PaySettingService {

    @Autowired
    private PaySettingDao paySettingDao;

    @Override
    public BaseDao getBaseDao() {
        return paySettingDao;
    }

    public PaySetting save(PaySetting paySetting, String eCode) {
        String appId = paySetting.getAppId();
        Integer payType = paySetting.getPayType();
        // 判断APPID 是否正确

        // 是否已经存在  appid + type
        List<PaySetting> paySettingList = paySettingDao.findByAppIdAndPayType(appId, payType);
        if (paySettingList.size() > 0) {
            if (paySettingList.size() > 1) {
                WebException.fail("数据库数据错误:存在多条相同数据");
            } else {
                PaySetting setting = paySettingList.get(0);
                paySetting.setId(setting.getId());
                BeanUtils.copyProperties(paySetting, setting);
                return save(setting);
            }
        } else {
            return save(paySetting);
        }
        return null;
    }
}

