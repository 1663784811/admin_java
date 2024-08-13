package com.cyyaw.service;

import com.cyyaw.store.table.order.entity.OOrder;
import com.cyyaw.util.entity.CountGoodsRst;
import com.cyyaw.util.entity.SubmitOrder;
import com.cyyaw.util.tools.BaseResult;

public interface OrderService {

    /**
     * 计算商品价格
     */
    CountGoodsRst countGoodsPrice(SubmitOrder submitOrder);


    /**
     * 创建订单
     */
    OOrder createOrder(SubmitOrder submitOrder);


    /**
     * 查找我的订单
     */
    BaseResult findMyOrder(String userId);

    /**
     * 查找订单
     */
    BaseResult orderById(String orderId);

    /**
     * 获取餐桌订单
     */
    OOrder boardOrder(String boardId);

    /**
     * 提交点餐订单
     */
    OOrder createFoodOrder(String boardId, SubmitOrder submitOrder);


}
