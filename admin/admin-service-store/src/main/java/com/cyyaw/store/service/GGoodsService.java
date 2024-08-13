package com.cyyaw.store.service;

import com.cyyaw.jpa.BaseTableService;
import com.cyyaw.store.table.goods.entity.GGoods;
import com.cyyaw.util.tools.BaseResult;

public interface GGoodsService extends BaseTableService<GGoods, Integer> {


    GGoods findByTid(String tid);

    BaseResult saveGGoods(GGoods saveObj);


}
