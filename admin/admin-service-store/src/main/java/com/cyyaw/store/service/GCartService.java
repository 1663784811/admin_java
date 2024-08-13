package com.cyyaw.store.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.store.table.goods.entity.GCart;

public interface GCartService extends BaseTableService<GCart, Integer> {


    /**
     *
     */
    GCart findBySkuIdAndUid(String skuId, String userId);


}
