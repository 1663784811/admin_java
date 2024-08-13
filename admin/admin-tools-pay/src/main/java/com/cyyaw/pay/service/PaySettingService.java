package com.cyyaw.pay.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.pay.table.entity.PaySetting;

public interface PaySettingService extends BaseTableService<PaySetting, Integer> {


    PaySetting save(PaySetting paySetting, String eCode);


}
