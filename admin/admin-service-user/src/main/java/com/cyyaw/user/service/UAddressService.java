package com.cyyaw.user.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.user.table.entity.UAddress;
import com.cyyaw.util.tools.BaseResult;

public interface UAddressService extends BaseTableService<UAddress, Integer> {

    UAddress defaultAddress(String userId, String addressId);

    Object saveAddress(UAddress uAddress);

}
