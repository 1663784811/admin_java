package com.cyyaw.service;

import com.cyyaw.util.entity.AddMyCar;
import com.cyyaw.util.tools.BaseResult;

public interface CartService {


    /**
     * 获取我的购物车列表
     */
    BaseResult myCartList(String userId);

    /**
     * 查询我的门店购物车
     */
    BaseResult myStoreCartList(String storeId, String userId);

    /**
     * 更新我的购物车
     */
    BaseResult updateMyCar(String userId, AddMyCar addMyCar);


    void delCartGoods(String cartTid, String userId);


}
